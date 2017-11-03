package com.hxh.component.business.arouter.router;


import com.alibaba.android.arouter.launcher.ARouter;
import com.hxh.component.business.arouter.provider.IFragmentTestDemoProvider;

/**
 * 创建者：hxh
 * 时间：  2017/8/15
 * 描述： 此类只是管理Router跳转（简便MyAppProvider操作），请将所有Provider在此初始化，供MyAppProvider调用
 *
 */
public class ServiceManager {

    private IFragmentTestDemoProvider mIFragmentTestDemoProvider;

    //region 单利
    public ServiceManager() {
        ARouter.getInstance().inject(this);
    }

    private static final class ServiceManagerHolder {
        private static final ServiceManager instance = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return ServiceManagerHolder.instance;
    }
    //endregion

    public IFragmentTestDemoProvider getMyTestFragmentProvider() {
        return mIFragmentTestDemoProvider != null ? mIFragmentTestDemoProvider : (mIFragmentTestDemoProvider = ((IFragmentTestDemoProvider) MyRouter.newInstance(mIFragmentTestDemoProvider.key).navigation()));
    }
}

