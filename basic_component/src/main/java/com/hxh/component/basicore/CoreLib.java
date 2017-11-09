package com.hxh.component.basicore;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.ArrayMap;


import com.hxh.component.basicore.Base.app.AppComponent;
import com.hxh.component.basicore.Base.topbar.ActionBarProvider;
import com.hxh.component.basicore.net.BaseAPI;
import com.hxh.component.basicore.net.NetProvider;
import com.hxh.component.basicore.util.Log;
import com.hxh.component.basicore.util.Utils;

/**
 * 关于此库的说明
 * AppCompartAutoLayoutActivity/AppCompartAutoLayoutFragment
 * 是Activity/Fragment 的顶级接口（可扩展，没有继承于任何第三方库）
 *
 *
 * NetProvider       主要是网络相关的东西
 * AppComponent      从这里面你能得到大部分所使用的组件
 * Utils             工具类
 * TopBarXXXActivity/Fragment   提供了一个简易版TopBar的Activyt或者Fragment，通过ActionBarConfig 配置
 * BaseRecyAdapter            提供了RecycleView的Adapter
 *
 *
 * MRecycleView 包下提供了一个 自带缓存，上拉，下拉..功能的RecycleView
 *
 * ApiServices 注解提供了一个生成请求代码的功能
 * ApiServicesOtherPath 辅助ApiServices 进行使用
 * Safe   注解提供了一个安全的执行环境(保证不会崩溃)
 * SingleClick 注解提供了双击检测操作
 *
 * @更新时间   2017.7.27
 * @正在进行
 * 1. Base类的完善封装(将支持 swipeBack)
 * 2.业务抽离为单独的Delagate (7.31 已完成)
 * 3.BaseViewHolder
 * 4. Glide 的封装
 *
 *
 * @author hxh
*/
public class CoreLib {

    private  boolean isLogEnable; //配置是否显示Log
    private  boolean isLogWriteEnable; //配置是否输出Log到日志文件中
    private  Context mApplication; //配置AppContext(Application)
    private  String mLogTag; //配置Log的Tag名字
    private  NetProvider mNetProvider; //配置你的网络 提供者
    private AppComponent mAppComponent;//得到App组件类

    private boolean enableUMengUser,enableUMengAcoutn;
    private static CoreLib INSTANCE = null;


    public static CoreLib getInstance()
    {
        if(null == INSTANCE)
        {
            INSTANCE = new CoreLib();
        }
        return INSTANCE;
    }

    public CoreLib init()
    {
        Utils.init(mApplication);
        Utils.syncIsDebug(mApplication);
        Log.init(  Utils.isDebug(),Utils.isDebug(),mLogTag);
        if(null != mNetProvider)
        {
            BaseAPI.getInstance().register(mNetProvider);
        }
        return this;
    }

    public static class Builder
    {
        public Context appContext;
        public String logtag;
        public NetProvider mNetProvider;

        public ActionBarProvider actionBarProvider;

        public Builder setContext(Context appContext)
        {
            if(appContext instanceof Application)
            {
                this.appContext = appContext;
            }else
            {
                throw new IllegalStateException("context must is ApplicationContext");
            }
            return this;
        }



        public Builder setLogTag(String logtag)
        {
            this.logtag = logtag;
            return this;
        }

        public Builder setNetProvider(NetProvider pro)
        {
            this.mNetProvider = pro;
            return this;
        }


        private boolean isEnableUMengUserrecording;
        private boolean isEnableUMengAcountStatistics;

        /**
         * 是否开启U盟统计
         * @param userrecordingenable
         * @param acountStatistics
         * @return
         */
        public Builder setEnableUMengStatistics(boolean userrecordingenable,boolean acountStatistics)
        {
            this.isEnableUMengAcountStatistics = acountStatistics;
            this.isEnableUMengUserrecording = userrecordingenable;
            return this;
        }


        public CoreLib build() {
            CoreLib core = CoreLib.getInstance();
            core.setNetProvider(mNetProvider);
            core.setLogTag(logtag);
            core.setAppcontext(appContext);
            core.setEnableUMengAcoutn(isEnableUMengAcountStatistics);
            core.setEnableUMengUser(isEnableUMengUserrecording);
            return core;
        }
    }





    //region getter setter


    public boolean isLogEnable() {
        return isLogEnable;
    }

    public void setLogEnable(boolean logEnable) {
        isLogEnable = logEnable;
    }

    public boolean isLogWriteEnable() {
        return isLogWriteEnable;
    }

    public void setLogWriteEnable(boolean logWriteEnable) {
        isLogWriteEnable = logWriteEnable;
    }

    public NetProvider getNetProvider() {
        return mNetProvider;
    }

    public void setNetProvider(NetProvider mNetProvider) {
        this.mNetProvider = mNetProvider;
    }

    public Context getAppcontext() {
        return mApplication;
    }

    public void setAppcontext(Context appcontext) {
        this.mApplication = appcontext;
    }

    public String getLogTag() {
        return mLogTag;
    }

    public void setLogTag(String logTag) {
        this.mLogTag = logTag;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public void setAppComponent(AppComponent mAppComponent) {
        this.mAppComponent = mAppComponent;
    }

    public boolean isEnableUMengAcoutn() {
        return enableUMengAcoutn;
    }

    public void setEnableUMengAcoutn(boolean enableUMengAcoutn) {
        this.enableUMengAcoutn = enableUMengAcoutn;
    }

    public boolean isEnableUMengUser() {
        return enableUMengUser;
    }

    public void setEnableUMengUser(boolean enableUMengUser) {
        this.enableUMengUser = enableUMengUser;
    }

    public String getBaseUrl(String tagName)
    {
        return getNetProvider().getBaseUrl(tagName);
    }


    public String getBaseUrl()
    {
        return getNetProvider().getBaseUrl();
    }

    public void configDynamicHttpUrls(ArrayMap<String,String> urls)
    {
        this.getNetProvider().configDynamicHttpUrls(urls);
    }

    //endregion



}
