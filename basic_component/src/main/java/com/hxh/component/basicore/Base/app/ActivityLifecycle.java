package com.hxh.component.basicore.Base.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.util.AppManager;
import com.umeng.analytics.MobclickAgent;

/**
 * 所有的Activity都会走这个接口！ 简化BaseActivity.. 一些逻辑，集中抽取到此类
 * 1. 后续再更新
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks{
   
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState)
    {


        if(activity instanceof  AppCompatActivity)AppManager.addActivity(((AppCompatActivity) activity));

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(CoreLib.getInstance().isEnableUMengUser()) MobclickAgent.onResume(activity);

    }

    @Override
    public void onActivityPaused(Activity activity) {
        if(CoreLib.getInstance().isEnableUMengUser()) MobclickAgent.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if(activity instanceof  AppCompatActivity)AppManager.removeActivity(((AppCompatActivity) activity));

    }
}
