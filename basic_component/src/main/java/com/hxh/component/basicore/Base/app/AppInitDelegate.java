package com.hxh.component.basicore.Base.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.hxh.component.basicore.Base.topbar.ActionBarProvider;
import com.hxh.component.basicore.Config;
import com.hxh.component.basicore.R;
import com.hxh.component.basicore.util.AppManager;
import com.hxh.component.basicore.util.BugManager;
import com.hxh.component.basicore.util.Utils;
import com.hxh.component.basicore.util.aspj.util.AspjManager;

import java.io.File;
import java.util.HashMap;

import me.yokeyword.fragmentation.Fragmentation;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 标题: 系统初始化类
 * 作者: hxh
 * 日期: 2017/11/7 18:29
 * 描述: 初始化相关组件
 */
public abstract class AppInitDelegate
{
    public AppInitDelegate(AppDelegate appDelegate, Application app)
    {
        this.appDelegate = appDelegate;
        this.mApplication = app;
        init();
    }

    private AppDelegate appDelegate;
    private Application mApplication;
    private String mDatasDir;
    private String mImagesDir;
    private BugManager mBugManager;
    private HashMap<String,String> mSaveData; //SaveData可以看做一个小型数据存储库






    public BugManager getBugManager()
    {
        return mBugManager;
    }

    //region -----------------------你必须要实现的方法-----------------------


    /**
     * 初始化Bug管理器
     */
    private void initBugManager() {
        mBugManager =  A_initBugManager();
        if(null != mBugManager )
        {
            appDelegate.registerBugManager(mBugManager);
        }
    }
    public abstract BugManager A_initBugManager();

    //endregion


    //region -----------------------可选配置方法-----------------------
    /**
     * 初始化ActionBarProvider
     */
    public void initActionBarProvider()
    {
        Observable
                .just(R.mipmap.icon_back_white)
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer resid) {
                        return BitmapFactory.decodeResource(mApplication.getResources(),resid);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        appDelegate.registerActionBarProvider(new ActionBarProvider.Builder()
                                .backColor(Color.parseColor("#ffffff"))
                                .backImg(bitmap)
                                .backText("返回")
                                .enableImmeriveMode()
                                .build());
                    }
                });
    }


    public void initSharpe() {

    }

    public String getAppCacheDir() {
        if (null == mDatasDir) {
            mDatasDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "xakj";
            File file = new File(mDatasDir);
            if (!file.exists()) {
                file.mkdirs();
            }

        }
        return mDatasDir;
    }


    public String getAppImgCacheDir() {
        if (null == mImagesDir) {
            mImagesDir = getAppCacheDir() + File.separator + "img";
            File file = new File(mImagesDir);
            if (!file.exists()) {
                file.mkdirs();
            }

        }
        return mImagesDir;
    }

    //endregion

    //region -----------------------系统必须初始化的，无需干预-----------------------
    private void init()
    {

        // Utils.init(mApplication);
        mSaveData = new HashMap<>(1);
        getAppCacheDir();
        getAppImgCacheDir();
        initBugManager();
        initArouter();
        initSharpe();
        initCheckLoginAspj();
        initFragmention();
        initActionBarProvider();
    }

    private void initFragmention()
    {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(Utils.isDebug())
                .install();
    }

    /**
     * 初始化Arouter
     */
    private void initArouter() {
        if (Utils.isDebug())
        {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            //
        }
        ARouter.init(mApplication);
    }

    /**
     * 初始化自动登录的织入点
     */
    private void initCheckLoginAspj()
    {
        AspjManager.CheckLoginManager.getInstance().registerLoginView(Config.RouterPath_DefalutLoginView, AspjManager.CheckLoginManager.MODE_ACTIVTY);
    }


    //endregion

    public void saveData(String key,String value)
    {
        if(mSaveData.containsKey(key))mSaveData.remove(key);
        mSaveData.put(key,value);
    }

    public String getData(String key)
    {

        return    TextUtils.isEmpty(mSaveData.get(key))?"":mSaveData.get(key);
    }

}
