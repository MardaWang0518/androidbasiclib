package com.hxh.component.business.arouter;
/**
 * Created by hxh on 2017/8/15.
 */

import android.support.v4.app.Fragment;

import com.hxh.component.business.arouter.provider.IActivityTestDemoProvider;
import com.hxh.component.business.arouter.router.MyRouter;
import com.hxh.component.business.arouter.router.ServiceManager;

/**
 * 创建者：hxh
 * 时间：  2017/8/15
 * 描述： 全局管理Router跳转，格式为：
 *  1个跳转类对应provider包下一个路由声明类
 *  然后，在此写入方法，以便轻松进行Router
 *  注意的是： Activity和Fragment写法不同，Fragment
 */
public class MyAppProvider {

    public static Fragment getTestFragment()
    {
        return ServiceManager.getInstance().getMyTestFragmentProvider().newInstance();
    }



    public static Object startTestActivty(String key,String value)
    {
        return MyRouter.newInstance(IActivityTestDemoProvider.KEY)
                .withString(key,value)
                .navigation();
    }




}
