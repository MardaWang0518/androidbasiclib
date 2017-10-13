package com.hxh.component.basicore.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxh.component.basicore.Base.delegate.FragmentDelegate;
import com.hxh.component.basicore.Base.view.EmptyFragment;
import com.hxh.component.basicore.mvp.persenter.IPresenter;

/**
 * 对外提供各种操作方法，内部统一交由对应的Delegate实现
 */
public abstract class BaseFragment<P extends IPresenter>
        extends EmptyFragment
        implements IView<P>
{

    private FragmentDelegate<P> mTopBarFragmentDelegate = new FragmentDelegate<>(this);



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = mTopBarFragmentDelegate.onCrateView(inflater,container,savedInstanceState);
        //开启ToolBar的支持

        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTopBarFragmentDelegate.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTopBarFragmentDelegate.onAttach(context);
    }


    protected P getP() {
        return mTopBarFragmentDelegate.getP();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mTopBarFragmentDelegate.onVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        mTopBarFragmentDelegate.onInVisible();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mTopBarFragmentDelegate.onDetach();
    }

    public Context getmContext() {
        return mTopBarFragmentDelegate.getmContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTopBarFragmentDelegate.onDestroy();
    }

    /**
     * 这里使用Butterknife 进行View注入
     *
     * @param rootview
     */
    @Override
    public void bindUI(View rootview) {
        mTopBarFragmentDelegate.bindUI(rootview);
    }

    public <V extends View> V findViewBy(int resId) {
        return mTopBarFragmentDelegate.findViewBy(resId);
    }


}
