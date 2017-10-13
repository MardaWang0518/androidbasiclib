package com.hxh.component.basicore.ui.imagview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.hxh.component.basicore.util.Log;
import com.hxh.component.basicore.util.Utils;

/**
 * Created by hxh on 2017/6/8.
 */
public class DragImageView extends ImageView {
    public DragImageView(Context context) {
        super(context);
    }

    public DragImageView(Context context, int lastX) {
        super(context);
        this.lastX = lastX;
    }

    public DragImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public DragImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    private int  lastX,lastY;

    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);//屏蔽父控件拦截onTouch事件
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    lastX = x;
                    lastY = y;
                    startDrag();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //计算移动的距离
                    int offX = x - lastX;
                    int offY = y - lastY;
                   //if(x<= Utils.Screen.getWidth() && y<= Utils.Screen.getHeight())
                    //{
                        //调用layout方法来重新放置它的位置
                        layout(getLeft()+offX, getTop()+offY,
                                getRight()+offX    , getBottom()+offY);
                        stopDrag();
                   //}

                    break;
            }
            return true;

    }

    /**
     *   子控件开始进入移动状态，令ViewPager无法拦截对子控件的Touch事件
     */
    private void startDrag(){
        if(moveListener!=null) moveListener.startDrag();

    }
    /**
     *   子控件开始停止移动状态，ViewPager将拦截对子控件的Touch事件
     */
    private void stopDrag(){
        if(moveListener!=null) moveListener.stopDrag();
    }

    private MatrixImageView.OnMovingListener moveListener;
    public void setListener(MatrixImageView.OnMovingListener lis)
    {
        this.moveListener = lis;
    }

}
