package com.hxh.component.business.arouter.router;

import com.alibaba.android.arouter.launcher.ARouter;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import com.hxh.component.business.arouter.provider.employservices.IEmployeeServicesProvider;
import com.hxh.component.business.arouter.provider.employservices.IEmployeeSetSignPwdProvider;
import com.hxh.component.business.arouter.provider.mine.IMineProvider;
import com.hxh.component.business.arouter.provider.mygrowingup.IMyGrowingUpProvider;
import com.hxh.component.business.arouter.provider.myrixinbao.IMyRiXinBaoProvider;


/**
 * 作者：黎伟杰-子然 on 2017/4/13.
 * 邮箱：liweijie@linghit.com
 * description：注意，这不是一个完全的单例模式，不能私有化构造器以及属性
 * update by:
 * update day:
 */
public class ServiceManager {
    //服务注入看自己的具体实现
    IEmployeeServicesProvider myEmployeeServicesProvider;
    IMineProvider myMineProvider;
    IMyGrowingUpProvider myGrowingUpProvider;
    IMyRiXinBaoProvider myRiXInBaoProvider;
    IEmployeeSetSignPwdProvider mySetpwdProvider;
    //可以不使用@Autowired，手动发现服务
    //工作

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

    /**
     * @return
     */
    public IEmployeeServicesProvider getMyEmployServicesProvider() {
        return myEmployeeServicesProvider != null ? myEmployeeServicesProvider : (myEmployeeServicesProvider = ((IEmployeeServicesProvider) MyRouter.newInstance(IEmployeeServicesProvider.MYWORK_MAIN_SERVICE).navigation()));
    }

    public IMineProvider getMyMineProvider() {
        return myMineProvider != null ? myMineProvider : (myMineProvider = ((IMineProvider) MyRouter.newInstance(IMineProvider.MYWORK_MAIN_SERVICE).navigation()));
    }

    public IMyGrowingUpProvider getMyGrowingUpProvider() {
        return myGrowingUpProvider != null ? myGrowingUpProvider : (myGrowingUpProvider = ((IMyGrowingUpProvider) MyRouter.newInstance(IMyGrowingUpProvider.MYWORK_MAIN_SERVICE).navigation()));
    }

    public ISupportFragment getMyRiXinBaoProvider() {
        return (ISupportFragment) MyRouter.newInstance(IMyRiXinBaoProvider.MYWORK_MAIN_SERVICE).navigation();
    }


    public IEmployeeSetSignPwdProvider getMySetSignPwdProvider() {
        return mySetpwdProvider != null ? mySetpwdProvider : (mySetpwdProvider = ((IEmployeeSetSignPwdProvider) MyRouter.newInstance(IEmployeeSetSignPwdProvider.MYWORK_MAIN_SERVICE).navigation()));
    }
}

