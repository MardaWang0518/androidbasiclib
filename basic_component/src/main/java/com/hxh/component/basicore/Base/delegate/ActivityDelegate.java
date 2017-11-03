package com.hxh.component.basicore.Base.delegate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.R;
import com.hxh.component.basicore.mvp.view.IView;
import com.hxh.component.basicore.mvp.persenter.BasePresenter;
import com.hxh.component.basicore.mvp.persenter.IPresenter;
import com.hxh.component.basicore.util.AutoUtils;
import com.hxh.component.basicore.util.ButterKnifeUtil;
import com.hxh.component.basicore.util.Utils;

import butterknife.Unbinder;

/**
 * Created by hxh on 2017/7/31.
 */
public class ActivityDelegate<P extends IPresenter> {

    public ActivityDelegate(IView<P> activity)
    {
        this.mActivity = activity;
    }

    private IView<P> mActivity;
    private FrameLayout frame_main;
    private View rootView;
    //存储当前的View
    private SparseArray<View> mViews;
    private P p;
    private Unbinder unBinder;



    public View onCreate(Bundle savedInstanceState) {

        mViews = new SparseArray<>();
        rootView = LayoutInflater.from(((AppCompatActivity) mActivity)).inflate(R.layout.activity_top_bar_base,null);
        frame_main = (FrameLayout) rootView.findViewById(R.id.frame_main);
        if(mActivity.getLayoutId() > 0)
        {
            //如果开启toolbar
            LayoutInflater.from(((AppCompatActivity) mActivity)).inflate(mActivity.getLayoutId(), frame_main);
            ((AppCompatActivity) mActivity).setContentView(rootView);
            bindUI(rootView);
            //AppManager.addActivity(this);
            AutoUtils.auto(((AppCompatActivity) mActivity));
            //设置不显示自带的title
        }

        mActivity.initData(savedInstanceState);

        return rootView;
    }





    public void bindUI(View rootview) {
        unBinder = ButterKnifeUtil.bind(((AppCompatActivity) mActivity));
    }

    public P getP()
    {
        if(null == p) {
            p = mActivity.newP();
            if (p != null)
            {
                p.AttachView(mActivity);
            }
        }
        return p;
    }



    public void onResume() {
        if(null != ((BasePresenter) getP()))((BasePresenter) getP()).newCompositeSubscription();
    }


    public void onPause() {
        if(null != ((BasePresenter) getP()))((BasePresenter) getP()).unSubscription();
    }


    public void onDestroy() {
        if (null != unBinder)
        {
            unBinder.unbind();
            unBinder = null;
        }
        if(null !=p)  {
            p.DetachView();
            p = null;
        }
        mViews.clear();
        mViews = null;
    }


    public <V extends View>V findViewBy(int resId)
    {
        View view = mViews.get(resId);
        if(null == view)
        {
            view = ((AppCompatActivity) mActivity).findViewById(resId);
            mViews.put(resId,view);
        }
        return (V) view;
    }

}
