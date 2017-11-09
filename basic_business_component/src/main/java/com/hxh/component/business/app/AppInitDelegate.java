package com.hxh.component.business.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;

import com.hxh.component.basicore.Base.app.AppDelegate;
import com.hxh.component.basicore.Base.topbar.ActionBarProvider;
import com.hxh.component.basicore.util.AppManager;
import com.hxh.component.basicore.util.BugManager;
import com.hxh.component.basicore.util.Utils;
import com.hxh.component.basicore.util.aspj.util.AspjManager;
import com.hxh.component.business.BuildConfig;
import com.hxh.component.business.R;
import com.hxh.component.business.common.UserInfoDTO;
import com.hxh.component.business.common.greendao.DaoMaster;
import com.hxh.component.business.common.greendao.DaoSession;
import com.hxh.component.business.utils.Constant;
import com.hxh.component.business.utils.oss.api.OSSUtil;
import com.hxh.component.business.utils.oss.request.Request_GetOssInfo;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.util.HashMap;

import me.yokeyword.fragmentation.Fragmentation;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by hxh on 2017/7/8.
 * 此类负责app的相关组件初始化工作
 */
public class AppInitDelegate extends com.hxh.component.basicore.Base.app.AppInitDelegate{

    public AppInitDelegate(AppDelegate appDelegate, Application app) {
        super(appDelegate, app);
        this.mApplication = app;
    }

    @Override
    public BugManager A_initBugManager() {
        return initBugManager();
    }

    @Override
    public BugManager getBugManager() {
        return super.getBugManager();
    }

    private Application mApplication;
    private  boolean ossIsInitd = false;
    private UserInfoDTO mUserDTO;
    private DaoSession mDaoSession;

    public void init()
    {
        initGreenDao();
        mUserDTO = new UserInfoDTO(mDaoSession);
        if (mUserDTO.getUser() != null) {
            initOss(); //不用初始化OSs
        }else
        {
            mUserDTO.setUser(null);
        }
        initBugManager();
        initX5();
    }


    private void initGreenDao() {
        // Application 中执行
        // DevOpenHelper 每次数据库升级会清空数据，一般用于开发
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.getContext(), "myworkat.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();

    }

    public UserInfoDTO getUserDTO() {
        return null==mUserDTO?mUserDTO=new UserInfoDTO(mDaoSession):mUserDTO;
    }



    /**
     * 请使用 OssUtil里面的初始化方法
     */
    public void initOss() {
        //以下是示例，可以自行更改
        if (!ossIsInitd || null == OSSUtil.mDefaultinitOSSResponse) {
            ossIsInitd = true;
            OSSUtil
                    .initOss_default(new Request_GetOssInfo(Constant.OSSId.OSS_contract,"sdk", ""));
        }
    }

    private BugManager initBugManager() {
        return new BugManager() {
            @Override
            public void postBug(Exception e) {
                CrashReport.postCatchedException(e);
            }

            @Override
            public void postBug(Throwable e) {
                CrashReport.postCatchedException(e);
            }

            @Override
            public void init() {
                // 获取当前包名
                String packageName = mApplication.getPackageName();
                // 获取当前进程名
                String processName = AppManager.getProcessName(android.os.Process.myPid());
                // 设置是否为上报进程
                CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mApplication);
                strategy.setUploadProcess(processName == null || processName.equals(packageName));
                CrashReport.initCrashReport(mApplication, "ff0fa2e23e", false);
            }
        };

    }



    private void initX5() {
        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {}
        };
        QbSdk.initX5Environment(mApplication, cb);
    }



    public DaoSession getDaoSession() {
        return mDaoSession;
    }



}
