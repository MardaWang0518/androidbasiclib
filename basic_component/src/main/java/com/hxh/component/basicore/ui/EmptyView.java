package com.hxh.component.basicore.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxh.component.basicore.Base.adapter.BaseRecyAdapter;
import com.hxh.component.basicore.R;
import com.hxh.component.basicore.util.Utils;

import java.lang.reflect.Method;

/**
 * Created by hxh on 2017/6/16.
 */
public class EmptyView extends RelativeLayout implements View.OnClickListener {

    private View mView;
    private RelativeLayout view_request;
    private TextView tv_tip;
    private TextView tv_isrefresh;
    private ImageView iv_emptyicon;
    private boolean isEnableRefresh;
    private ReLoadListener reLoadListener;
    SkipListener skipListener;


    public EmptyView(Context context) {
        super(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.layout_emptyview, this, true);
        initView(mView);
    }

    private void initView(View mView) {
        view_request = (RelativeLayout) mView.findViewById(R.id.view_request);
        tv_tip = (TextView) mView.findViewById(R.id.tv_tip);
        tv_isrefresh = (TextView) mView.findViewById(R.id.tv_isrefresh);
        iv_emptyicon = (ImageView) mView.findViewById(R.id.iv_emptyicon);
        view_request.setOnClickListener(this);
    }

    public void setTipMsg(String msg) {
        tv_tip.setText(Utils.Text.isEmpty(msg) ? "什么都没有喔～" : msg);
    }

    public void setEmptyIcon(int resid) {
        iv_emptyicon.setImageResource(resid);
    }

    public void setEmptyIcon(Drawable drawable) {
        iv_emptyicon.setImageDrawable(drawable);
    }

    //region 刷新方法

    /**
     * 是否启动刷新
     *
     * @param sourceClassObj
     * @param methodName
     * @param parmeters
     */
    public void enableClickEventWhenClickBlank(final Object sourceClassObj, final String methodName, final Object... parmeters) {
        isEnableRefresh = true;
        tv_isrefresh.setVisibility(VISIBLE);
        bindClickEventMethodWhenClickBlank(sourceClassObj, methodName, parmeters);
    }

    /**
     * 绑定 点击空白处 时候的 加载方法
     *
     * @param sourceClassObj 传入当前类  ，一般是this
     * @param methodName     传入刷新的方法
     * @param parmeters      传入刷新方法的参数
     */
    private void bindClickEventMethodWhenClickBlank(final Object sourceClassObj, final String methodName, final Object... parmeters) {
        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CallMethod(sourceClassObj, methodName, parmeters);
            }
        });
    }

    private void CallMethod(final Object sourceClassObj, final String methodName, final Object... parmeters) {
        int length = parmeters.length;
        Class<?>[] paramType = new Class<?>[length];
        for (int i = 0; i < length; i++) {
            paramType[i] = parmeters[i].getClass();
        }

        try {
            Method m = sourceClassObj.getClass().getDeclaredMethod(methodName, paramType);
            if (null == m) {
                throw new IllegalStateException("no can't find method:<" + methodName + "> in " + sourceClassObj.getClass().getName());
            }
            m.setAccessible(true);
            m.invoke(sourceClassObj, parmeters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    public void show() {
        mView.setVisibility(VISIBLE);
    }

    public void hide() {
        mView.setVisibility(GONE);
    }

    public void updateTips(String newTips) {
        tv_isrefresh.setVisibility(VISIBLE);
        tv_isrefresh.setText(newTips);
        tv_isrefresh.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv_isrefresh.setTextColor(Utils.Resource.getColor(R.color.google_blue));
        tv_isrefresh.setOnClickListener(this);
    }

    private BaseRecyAdapter adapter;
    private AdaptetObservable observable;

    /**
     * 它可以和你的Adapter进行绑定，从而实现，当你Adapter 无数据时候，自动显示空VIew
     *
     * @param adapter
     */
    public void bindAdapter(BaseRecyAdapter adapter) {
        this.adapter = adapter;
        observable = new AdaptetObservable();
        this.adapter.registerAdapterDataObserver(observable);
    }

    public void bindAdapter(RecyclerView.Adapter adapter) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_isrefresh) {
            if (null != skipListener) {
                skipListener.skip2others();
            }
        } else if (v.getId() == R.id.view_request) {
            if (null != reLoadListener) {
                reLoadListener.reLoad();
            }
        }
    }

    class AdaptetObservable extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            Utils.Toast.toast("变化，变化，变化！");
            if (adapter.getmDatas().size() == 0) {
                show();
            }
        }
    }

    public void onDestore() {
        adapter.unregisterAdapterDataObserver(observable);
    }

    public void setReLoadListener(ReLoadListener reLoadListener) {
        this.reLoadListener = reLoadListener;
    }

    public void setSkipListener(SkipListener skipListener) {
        this.skipListener = skipListener;
    }

    public interface ReLoadListener {
        void reLoad();
    }

    public interface SkipListener {
        void skip2others();
    }

}
