package com.hxh.component.basicore.Base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxh.component.basicore.Base.delegate.interfaces.IToolBarRelated;
import com.hxh.component.basicore.Base.delegate.FragmentDelegate;
import com.hxh.component.basicore.Base.delegate.ToolBarDelegate;
import com.hxh.component.basicore.Base.topbar.ActionBarConfig;
import com.hxh.component.basicore.Base.view.EmptyFragment;
import com.hxh.component.basicore.mvp.view.IView;
import com.hxh.component.basicore.mvp.persenter.IPresenter;

/**
 * 对外提供各种操作方法，内部统一交由对应的Delegate实现
 */
public abstract class TopBarBaseFragment<P extends IPresenter>
        extends EmptyFragment
        implements IView<P>,IToolBarRelated
{

    private FragmentDelegate<P> mTopBarFragmentDelegate = new FragmentDelegate<>(this);
    private ToolBarDelegate mToolBarDelegate ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = mTopBarFragmentDelegate.onCrateView(inflater,container,savedInstanceState);
        //开启ToolBar的支持
        mToolBarDelegate = new ToolBarDelegate(setActionBarConfig());
        mToolBarDelegate.init();
        mToolBarDelegate.fetchToView(rootview,this);

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

    protected abstract ActionBarConfig setActionBarConfig();




    //region ToolBar相关
    @Override
    public void setActionBarConfig(ActionBarConfig config) {
        mToolBarDelegate.setActionBarConfig(config);
    }

    @Override
    public void setActionbar_title(TextView tv_title) {
        mToolBarDelegate.setActionbar_title(tv_title);
    }

    @Override
    public ActionBarConfig getActionBarConfig() {
        return mToolBarDelegate.getmActionbarconfig();
    }

    @Override
    public View getToolBarRootView() {
        return mToolBarDelegate.getToolBarRootView();
    }


    @Override
    public LinearLayout getActionbar_rightImageViewButtons() {
        return mToolBarDelegate.getActionbar_rightImageViewButtons();
    }

    @Override
    public ImageView getActionbar_rightview_img() {
        return mToolBarDelegate.getActionbar_rightImageView();
    }

    @Override
    public TextView getActionbar_title() {
        return mToolBarDelegate.getActionbar_title();
    }


    @Override
    public View getActionbar_rightview() {
        return mToolBarDelegate.getActionbar_rightView();
    }
    @Override
    public void setActionBar_Title(String title) {
        mToolBarDelegate.setActionBar_Title(title);
    }

    @Override
    public void setBackViewTitle(String title) {
        mToolBarDelegate.setBackViewTitle(title);
    }

    @Override
    public void setActionbar_rightText(String text) {
        mToolBarDelegate.setActionbar_rightText(text);
    }

    @Override
    public void setActionbar_rightImg(int resId) {
        mToolBarDelegate.setActionbar_rightImg(resId);
    }

    @Override
    public void setActionbar_rightImg(Drawable drawable) {
        mToolBarDelegate.setActionbar_rightImg(drawable);
    }


    //endregion



}
