package com.hxh.component.business.arouter;
/**
 * Created by hxh on 2017/8/15.
 */

import android.support.v4.app.Fragment;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import com.hxh.component.business.arouter.provider.IMainActivityProvider;
import com.hxh.component.business.arouter.provider.IUserMainActivityProvider;
import com.hxh.component.business.arouter.router.MyRouter;
import com.hxh.component.business.arouter.router.ServiceManager;

/**
 * 创建者：hxh
 * 时间：  2017/8/15
 * 描述：
 */
public class MyAppProvider {

    public static Fragment getEmploySevicesFragment(Object... args)
    {
        return ServiceManager.getInstance().getMyEmployServicesProvider().newInstance(args);
    }


    public static Fragment getMineFragment(Object... args)
    {
        return ServiceManager.getInstance().getMyMineProvider().newInstance(args);
    }

    public static ISupportFragment getMyRiXinBaoFragment()
    {
        return ServiceManager.getInstance().getMyRiXinBaoProvider();
    }

    public static Fragment getGrowingUpFragment(Object... args)
    {
        return ServiceManager.getInstance().getMyGrowingUpProvider().newInstance(args);
    }

    public static Object startUserMainActivty(String key,String value)
    {
        return MyRouter.newInstance(IUserMainActivityProvider.KEY)
                .withString(key,value)
                .navigation();
    }

    public static Object startMainActivty(String key,String value)
    {
        return MyRouter.newInstance(IMainActivityProvider.KEY)
                .withString(key,value)
                .navigation();
    }

    public static Object getMySetSignPwdFragment(Object... args)
    {
        return ServiceManager.getInstance().getMySetSignPwdProvider().newInstance(args);
    }


    public static Object startUserMainActivty()
    {
        return MyRouter.newInstance(IUserMainActivityProvider.KEY)
                .navigation();
    }



}
