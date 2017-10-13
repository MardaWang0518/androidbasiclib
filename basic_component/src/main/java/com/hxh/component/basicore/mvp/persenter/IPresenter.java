package com.hxh.component.basicore.mvp.persenter;

import com.hxh.component.basicore.mvp.view.IView;

/**
 * Presenter基类，必须提供一个View
 */
public interface IPresenter<V extends IView> {
    /**
     * 关联View
     * @param view
     */
    void AttachView(V view);

    /**
     * 销毁View
     */
    void DetachView();

    V getView();
}
