package com.hxh.component.business.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.multidex.MultiDex;

import com.hxh.component.basicore.Base.app.AppComponent;
import com.hxh.component.basicore.Base.app.AppDelegate;
import com.hxh.component.basicore.Base.app.IApp;
import com.hxh.component.basicore.Base.topbar.ActionBarProvider;
import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.net.ConverterFactory;
import com.hxh.component.basicore.net.HttpInterceptor;
import com.hxh.component.basicore.net.NetProvider;
import com.hxh.component.basicore.rx.RxUtils;
import com.hxh.component.basicore.util.Utils;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import com.hxh.component.business.BuildConfig;
import com.hxh.component.business.R;
import com.hxh.component.business.common.UserInfoDTO;
import com.hxh.component.business.utils.Constant;

/**
 * App初始化流程：
 * 1. oncreate委托给AppDelegate去进行一些系统初始化(一般而言，不用动)
 * 2. AppInitDelegate 是进行初始化的地方(初始化你的各种sdk，或者数据之类)
 */
public class App extends Application implements IApp {
    private AppDelegate mAppDelegate;
    private static AppInitDelegate mAppInitDelegate;
    private static Context mContext;

    @Override
    public void onCreate() {

        super.onCreate();
        mContext = this;
        Utils.syncIsDebug(this);

        mAppDelegate = new AppDelegate();
        mAppDelegate.onCreate(this, initCoreLib());

        mAppInitDelegate = new AppInitDelegate(mAppDelegate, this);
        mAppInitDelegate.init();



    }


    private CoreLib initCoreLib() {
        return new CoreLib
                .Builder()
                .setContext(this)
                .setLogTag(Constant.LOGTAG)
                .setNetProvider(
                        new NetProvider.Builder()
                                .configTokenInterceptor(HttpInterceptor.buildTokenInterceptor("Authorization", new HttpInterceptor.TokenCallBack() {
                                    @Override
                                    public String getToken() {
                                        return getUserDTO().getmCurrentToken();
                                    }
                                }))
                                .configLogInteceptor(HttpInterceptor.buildLogInterceptor())
                                .configConverterFactory(ConverterFactory.getConverter(ConverterFactory.FASTJSON))
                                .enableCache(10 * 1024 * 1024)
                                .configHttpBaseUrl(BuildConfig.AUTH_ENDPOINT, true)
                                .build()
                )
                .build()
                .init();
    }


    /**
     * 当程序终止时候
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (null != mAppDelegate) {
            mAppDelegate.onStop();
        }
        mAppInitDelegate = null;
        mAppDelegate = null;
        mContext = null;
    }


    @Override
    public AppComponent getAppComponent() {
        return mAppDelegate.getAppComponent();
    }

    public static AppInitDelegate getAppInitDelegate() {
        return mAppInitDelegate;
    }

    public static Context getContext() {
        return mContext;
    }

    public static UserInfoDTO getUserDTO() {
        return mAppInitDelegate.getUserDTO();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
