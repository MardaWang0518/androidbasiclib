package com.hxh.component.basicore.mvp.view;

import android.os.Bundle;
import android.view.View;

import com.hxh.component.basicore.mvp.persenter.IPresenter;

/**
 * VIew的基类，需要一个Presenter
 */
public interface IView<P extends IPresenter> {

    /**
     * VIew初始化（你可以findVIewById，或者使用第三方注解框架：butterknife,xutils...）
     * @param rootview
     */
    void bindUI(View rootview); //绑定UI

    int getLayoutId();

    /**
     * 相当于onCreate
     * @param saveInstanceState
     */
    void initData(Bundle saveInstanceState);

    /**
     * 创建一个presenter，Fragment 必需重写，Activity为可选（当Activity作为Presenter时候，那么这个你也必须重写）
     * @return
     */
    P newP();

}
