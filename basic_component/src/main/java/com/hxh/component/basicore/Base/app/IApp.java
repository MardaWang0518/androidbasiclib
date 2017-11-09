package com.hxh.component.basicore.Base.app;

/**
 * 标题: IApp.java
 * 作者: hxh
 * 日期: 2017/7/3 14:43
 * 描述:  Application 的接口，请将Application实现此接口，然后在oncreate()中将事件委托给{@link AppDelegate}
 *
 */
public interface IApp {
    AppComponent getAppComponent();
}
