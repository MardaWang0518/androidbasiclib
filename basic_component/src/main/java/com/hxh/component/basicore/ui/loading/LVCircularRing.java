package com.hxh.component.basicore.ui.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hxh.component.basicore.util.Log;

/**
 * Created by hxh on 2017/3/31.
 */
public class LVCircularRing extends View {
    private float mWidth = 0f;
    private float mPadding = 0f;
    private float startAngle = 0f;
    private Paint mPaint;

    public LVCircularRing(Context context) {
        this(context, null);
    }

    public LVCircularRing(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVCircularRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredWidth() > getHeight())
            mWidth = getMeasuredHeight();
        else
            mWidth = getMeasuredWidth();
        mPadding = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.argb(100, 255, 255, 255));
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - mPadding, mPaint);
        mPaint.setColor(Color.WHITE);
        RectF rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        canvas.drawArc(rectF, startAngle, 100
                , false, mPaint);//第四个参数是否显示半径

    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);
    }

    public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 1000);
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
           // valueAnimator.setRepeatCount(1);
            valueAnimator.getListeners().clear();
            valueAnimator.removeAllUpdateListeners();
            //Log.d("----------------结束动画"+);
            myValueanimator = null;
            valueAnimator.cancel();
            valueAnimator.end();


            valueAnimator = null;
        }
    }

    ValueAnimator valueAnimator;
    private MyValueanimator myValueanimator;
    private ValueAnimator startViewAnim(float startF, final float endF, long time) {
        myValueanimator = new MyValueanimator();
        valueAnimator = ValueAnimator.ofFloat(startF, endF);

        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);//

        valueAnimator.addUpdateListener(myValueanimator);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              //  Log.d("----------------动画一直转");
                super.onAnimationEnd(animation);
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }

        return valueAnimator;
    }

    class MyValueanimator implements ValueAnimator.AnimatorUpdateListener
    {

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
           // Log.d("----------------动画一直转2");
            float value = (float) valueAnimator.getAnimatedValue();
            startAngle = 360 * value;

            invalidate();
        }
    }

}
