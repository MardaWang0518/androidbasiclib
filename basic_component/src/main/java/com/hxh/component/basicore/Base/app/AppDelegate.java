package com.hxh.component.basicore.Base.app;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;

import com.hxh.component.basicore.Base.topbar.ActionBarProvider;
import com.hxh.component.basicore.util.BugManager;
import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.imageLoader.GliderLoader;
import com.hxh.component.basicore.imageLoader.IImageLoader;
import com.hxh.component.basicore.net.NetProvider;
import com.hxh.component.basicore.net.RequestCallBackHandler;

/**
 * Application 委托类
 */
public class AppDelegate  implements IApp{


    private Application mApplication;
    private AppComponent mAppComponent;
    private AppComponentCallbacks mAppComponentCallbacks;
    private Application.ActivityLifecycleCallbacks mActivityLifecycle;
    private BugManager mBugManager;
    private ActionBarProvider actionBarProvider;
    public void onCreate(final Application application, final CoreLib corelib)
    {
        this.mApplication = application;
        this.mAppComponent = new AppComponent() {
            @Override
            public NetProvider globalNetProvider() {
                return corelib.getNetProvider();
            }

            @Override
            public IImageLoader globalImageLoader() {
                return new GliderLoader();
            }

            @Override
            public RequestCallBackHandler globalRequestInterceptor() {
                return corelib.getNetProvider().getConfigRequestCallBack();
            }

            @Override
            public Application globalApplication() {
                return application;
            }

            @Override
            public BugManager globalBugManager() {
                return mBugManager;
            }

            @Override
            public ActionBarProvider globalActionBarProvider() {
                return actionBarProvider;
            }


        };
        corelib.setAppComponent(mAppComponent);
        this.mAppComponentCallbacks = new AppComponentCallbacks(mApplication,mAppComponent);
        this.mApplication.registerComponentCallbacks(mAppComponentCallbacks);
        this.mActivityLifecycle = new ActivityLifecycle();
        this.mApplication.registerActivityLifecycleCallbacks(this.mActivityLifecycle);

    }

    public void onStop()
    {
        if(null != mAppComponentCallbacks)
        {
            this.mApplication.unregisterComponentCallbacks(mAppComponentCallbacks);
        }
        if(null != mActivityLifecycle)
        {
            this.mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        this.mActivityLifecycle = null;
        this.mAppComponentCallbacks = null;
        this.mAppComponent = null;
        this.mApplication = null;
    }



    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    private static class AppComponentCallbacks implements ComponentCallbacks2 {
        private Application mApplication;
        private AppComponent mAppComponent;

        public AppComponentCallbacks(Application application, AppComponent appComponent) {
            this.mApplication = application;
            this.mAppComponent = appComponent;
        }

        @Override
        public void onTrimMemory(int level) {

        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }



        @Override
        public void onLowMemory() {
            //内存不足时候，请求清除图片的内存缓存
            mAppComponent.globalImageLoader().clearMemoryCache(mApplication);
        }
    }


    public void registerBugManager(BugManager manager)
    {
        this.mBugManager = manager;
    }

    public void registerActionBarProvider(ActionBarProvider actionBarProvider)
    {
        this.actionBarProvider = actionBarProvider;
    }


}
