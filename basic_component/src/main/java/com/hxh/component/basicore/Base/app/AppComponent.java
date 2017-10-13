package com.hxh.component.basicore.Base.app;

import android.app.Application;

import com.hxh.component.basicore.Base.topbar.ActionBarProvider;
import com.hxh.component.basicore.util.BugManager;
import com.hxh.component.basicore.imageLoader.IImageLoader;
import com.hxh.component.basicore.net.NetProvider;
import com.hxh.component.basicore.net.RequestCallBackHandler;

/**
*@Title 集合所有组件,你可以从这个接口中得到这个基础库中的所有组件（在Applicaiton里面公开获取此对象的方法）
*@param
*@Time 15:04
*/
public interface AppComponent {

    /**
     * 全局的 网络 提供者
     * @return
     */
    NetProvider globalNetProvider();

    /**
     * 全局的 ImageLoader
     * @return
     */
    IImageLoader globalImageLoader();

    /**
     * 全局的 请求 拦截器
     * @return
     */
    RequestCallBackHandler globalRequestInterceptor();


    /**
     * 全局的 Application
     * @return
     */
    Application globalApplication();


    /**
     * bug 管理器
     * @return
     */
    BugManager globalBugManager();


    ActionBarProvider globalActionBarProvider();
}
