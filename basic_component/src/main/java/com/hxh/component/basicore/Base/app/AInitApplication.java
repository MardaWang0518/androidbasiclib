package com.hxh.component.basicore.Base.app;

/**
 * Created by hxh on 2017/11/3.
 */
@Deprecated
public abstract class AInitApplication {
//    private AppDelegate appDelegate;
//    private Application mApplication;
//    private String mDatasDir;
//    private String mImagesDir;
//    private boolean ossIsInitd = false;
//    private UserInfoDTO mUserDTO;
//    private DaoSession mDaoSession;
//    private BugManager mBugManager;
//    private HashMap<String, String> mSaveData; //SaveData可以看做一个小型数据存储库
//
//
//    public AInitApplication(AppDelegate appDelegate, Application app) {
//        this.appDelegate = appDelegate;
//        this.mApplication = app;
//    }
//
//    public void init() {
//        if (A_enableDataSaveCache()) {
//            mSaveData = new HashMap<>(1);
//        }
//        String appCacheDir =  A_initAppCacheDir();
//        String appImgCacheDir =   A_initAppImgCacheDir();
//        if(Utils.Text.isEmpty( appCacheDir))
//        {
//            getAppCacheDir();
//        }
//        if(Utils.Text.isEmpty(appImgCacheDir))
//        {
//            getAppImgCacheDir();
//        }
//        initGreenDao();
//        mUserDTO = new UserInfoDTO(mDaoSession);
//        if (mUserDTO.getUser() != null) {
//            A_initOss(); //不用初始化OSs
//        } else {
//            mUserDTO.setUser(null);
//        }
//        A_initBugManager();
//        A_initArouter();
//        A_initSharpe();
//        A_initAspectJLoginRouter();
//        A_initFragmention();
//        A_initActionBarProvider();
//    }
//
//    abstract void A_initFragmention();
//
//    abstract String A_initAppCacheDir();
//
//    abstract String A_initAppImgCacheDir();
//
//    abstract String A_initBugManager();
//
//    abstract void A_initUserDTO();
//
//    abstract void A_initSharpe();
//
//    abstract void A_initOss();
//
//    abstract void A_initArouter();
//
//    abstract void A_initActionBarProvider();
//
//    abstract void A_initAspectJLoginRouter();
//
//    abstract boolean A_enableDataSaveCache();
//
//    public void initFragmention() {
//        Fragmentation.builder()
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(BuildConfig.DEBUG)
//                .install();
//    }
//
//    public void initGreenDao() {
//        // Application 中执行
//        // DevOpenHelper 每次数据库升级会清空数据，一般用于开发
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(App.getContext(), "mycorelib.db", null);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//        mDaoSession = daoMaster.newSession();
//
//    }
//
//    //region FileCache
//    public String getAppCacheDir() {
//        if (null == mDatasDir) {
//            mDatasDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "xakj";
//            File file = new File(mDatasDir);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//
//        }
//        return mDatasDir;
//    }
//
//
//    public String getAppImgCacheDir() {
//        if (null == mImagesDir) {
//            mImagesDir = getAppCacheDir() + File.separator + "img";
//            File file = new File(mImagesDir);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//
//        }
//        return mImagesDir;
//    }
//    //endregion
//
//    public BugManager getBugManager() {
//        return mBugManager;
//    }
//
//    public UserInfoDTO getUserDTO() {
//        return null == mUserDTO ? mUserDTO = new UserInfoDTO(mDaoSession) : mUserDTO;
//    }
//
//    //region init方法
//
//    /**
//     * 请使用 OssUtil里面的初始化方法
//     */
//    public void initOss() {
//        if (!ossIsInitd || null == OSSUtil.mDefaultinitOSSResponse) {
//            ossIsInitd = true;
//            OSSUtil
//                    .initOss_default(new Request_GetOssInfo(Constant.OSSId.OSS_contract, "sdk", ""));
//        }
//    }
//
//    private void initBugManager() {
//        mBugManager = new BugManager() {
//            @Override
//            public void postBug(Exception e) {
//                CrashReport.postCatchedException(e);
//            }
//
//            @Override
//            public void postBug(Throwable e) {
//                CrashReport.postCatchedException(e);
//            }
//
//            @Override
//            public void init() {
//                // 获取当前包名
//                String packageName = mApplication.getPackageName();
//                // 获取当前进程名
//                String processName = AppManager.getProcessName(android.os.Process.myPid());
//                // 设置是否为上报进程
//                CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mApplication);
//                strategy.setUploadProcess(processName == null || processName.equals(packageName));
//                CrashReport.initCrashReport(mApplication, A_initBugManager(), false);
//            }
//        };
//        mBugManager.init();
//
//        appDelegate.registerBugManager(mBugManager);
//    }
//
//
//    public void initSharpe() {
//
//    }
//
//    /**
//     * 初始化Arouter
//     */
//    public void initArouter() {
//        if (BuildConfig.DEBUG) {
//            ARouter.openLog();     // 打印日志
//            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//            //
//        }
//        ARouter.init(mApplication);
//    }
//
//    public void initActionBarProvider() {
//        Observable
//                .just(R.mipmap.icon_back_white)
//                .map(new Func1<Integer, Bitmap>() {
//                    @Override
//                    public Bitmap call(Integer resid) {
//                        return BitmapFactory.decodeResource(mApplication.getResources(), resid);
//                    }
//                })
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) {
//                        appDelegate.registerActionBarProvider(new ActionBarProvider.Builder()
//                                .backColor(Color.parseColor("#ffffff"))
//                                .backImg(bitmap)
//                                .enableImmeriveMode()
//                                .build());
//                    }
//                });
//    }
//
//    public void initAspj() {
//        AspjManager.CheckLoginManager.getInstance().registerLoginView(Constant.RouterPath_DefalutLoginView, AspjManager.CheckLoginManager.MODE_ACTIVTY);
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    //endregion
//
//    public void saveData(String key, String value) {
//        if (A_enableDataSaveCache()) {
//            if (mSaveData.containsKey(key)) mSaveData.remove(key);
//            mSaveData.put(key, value);
//        }
//
//    }
//
//    public String getData(String key) {
//
//        if (A_enableDataSaveCache()) {
//            return TextUtils.isEmpty(mSaveData.get(key)) ? "" : mSaveData.get(key);
//        }
//        return "";
//    }


}
