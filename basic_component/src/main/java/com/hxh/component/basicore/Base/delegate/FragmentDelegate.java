package com.hxh.component.basicore.Base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hxh.component.basicore.R;
import com.hxh.component.basicore.mvp.view.IView;
import com.hxh.component.basicore.mvp.persenter.BasePresenter;
import com.hxh.component.basicore.mvp.persenter.IPresenter;
import com.hxh.component.basicore.util.AutoUtils;
import com.hxh.component.basicore.util.ButterKnifeUtil;

import butterknife.Unbinder;

/**
 * 应该只专注于Fragment，如ToolBar应该交由ToolBarDelegate去做
 */
public class FragmentDelegate<P extends IPresenter>
{
    public FragmentDelegate(IView<P> fragment)
    {
        this.fragment = fragment;
    }

    private IView<P> fragment;
    private FrameLayout frame_main;
    protected P p; //当前P
    private SparseArray<View> mViews; //View的缓存类
    private Context context;
    protected View rootView;//代表当前VIew
    private Unbinder unbinder;//BUtterKnife 的解绑对象

    private boolean isLazyLoad;
    private Bundle savedInstanceState;
    private boolean isFirstVisible=true;



    //region 生命周期
    public View onCrateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        int layoutid = (fragment).getLayoutId();
        if (layoutid <= 0) {
            throw new IllegalStateException("please set LayoutId");
        }
        else
        {
            mViews = new SparseArray<>();
            if (null == rootView) {
                rootView = inflater.inflate(R.layout.fragment_top_bar_base, container, false);
                frame_main = findViewBy(R.id.frame_main);
                inflater.inflate(layoutid, frame_main);//将子布局加载到主布局中
                //绑定UI
                (fragment).bindUI(rootView);
                AutoUtils.auto(rootView);
            } else {
                //当重复加载时候，就从跟布局中删除这个布局
                ViewGroup vp = (ViewGroup) rootView.getParent();
                if (null != vp) {
                    vp.removeView(rootView);
                }
            }
        }
        this.savedInstanceState = savedInstanceState;
        return rootView;
    }

    /**
     * 正常的Fragment需要实现并调用这个方法
     * @param savedInstanceState
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ( fragment).initData(savedInstanceState);
    }

    public void onAttach(Context context) {
        this.context = context;
    }

    public void onVisible() {
        if(null != ((BasePresenter) getP()))
            ((BasePresenter) getP()).newCompositeSubscription();
    }


    public void onInVisible() {
        if(null != ((BasePresenter) getP()))
            ((BasePresenter) getP()).unSubscription();
    }


    public void onDetach() {
        context = null;
    }

    public void onDestroy()
    {

        rootView = null;
        if(null != unbinder)
        {
            unbinder.unbind();
        }

        if(null !=  getP())getP().DetachView();
        p = null;
        context = null;
        if(null != mViews)
        {
            mViews.clear();
            mViews = null;
        }

    }

    /**
     * 懒加载的需要实现并调用这个方法
     */
    public void onSupportVisible() {

        if(isFirstVisible&&isLazyLoad)
        {
            ((IView) fragment).initData(savedInstanceState);
            isFirstVisible = false;
        }
    }

    //endregion



    public P getP()
    {
        if(null == p)
        {
            p = (fragment).newP();
            if(null != p)
            {
                p.AttachView(fragment);
            }
        }

        return p;
    }


    public <V extends View>V findViewBy(int resId)
    {

        View view = mViews.get(resId);
        if(null == view)
        {
            view = rootView.findViewById(resId);
            mViews.put(resId,view);
        }
        return (V) view;
    }

    /**
     * 这里使用Butterknife 进行View注入
     * @param rootview
     */

    public void bindUI(View rootview) {
        unbinder = ButterKnifeUtil.bind(this,rootview);
    }


    /**
     * 如果你是懒加载的fragment，必须显示指定
     * @param islazy
     */
    public void setEnableLazyLoad(boolean islazy)
    {
        this.isLazyLoad = islazy;
    }



    public Context getContext() {
        return getmContext();
    }
    public Context getmContext() {
        return context;
    }

}
