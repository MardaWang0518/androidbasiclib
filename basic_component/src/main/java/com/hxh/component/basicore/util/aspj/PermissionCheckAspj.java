package com.hxh.component.basicore.util.aspj;

import android.util.Log;

import com.bigkoo.alertview.OnItemClickListener;
import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.permissions.PermissionActivityActivity;
import com.hxh.component.basicore.util.AppManager;
import com.hxh.component.basicore.util.Utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * @Title 检查用户是否登录
 * 封装Permission 权限管理比较麻烦，因为授权结果的回调方法onRequestPermissionsResult 是在Activity/Fragment中，另外
 * 小米手机上有个天大的坑，当用户第一次授权不给时，那么在此请求授权，会一直返回true(但实际上依旧没授权)，折中的
 * 解决办法只能使用另一个透明Activity来检查
 * <p>
 * --------------------8.3
 * 今日看博客，了解到几种办法
 * 1. 透明的Activity 进行权限检查，就是目前用的，但是会比较明显看出是启动了一个新Actvity，效果不好
 * 2. Fragment 进行权限检查，因为Fragment也有onRequestPermissionsResult方法，但是把Fragment直接添加到Actvity，参考RxPermission
 * 3. BaseActivity进行封装，But，本项目是使用AOP来进行权限业务的横向插入
 */
@Aspect
public class PermissionCheckAspj {
    //@within(routerlib.hxh.com.corelib_annotation1.annotation.DataSave) || @annotation(ann)

    //private static final String method_piex1 = "@within(* com.hxh.component.basicore.util.aspj.PermissionCheck.*(..))";
    private static final String method_piex1 = "execution(@PermissionCheck * *.*(..))  && @annotation(ann)";

    @Pointcut(method_piex1)
    public void method_perpointcut(PermissionCheck ann) {
    }

    private Object result = null;
    //execution(!synthetic * *(..)) &&


    @Around("method_perpointcut(ann)")
    public Object around(final ProceedingJoinPoint joinPoint, final PermissionCheck ann) throws Throwable {
        com.hxh.component.basicore.util.Log.d("权限Aspj切入");


        if (ann.permissions() == null || ann.permissions().length == 0) {
           // throw new IllegalStateException("permissions can't null");
        } else {
            String tipmsg = ann.tipMsgBeforeRequest();
            Utils.DialogUtils.showDefaulStyleDialog(
                    Utils.Text.isEmpty(tipmsg) ? "接下来\n我们会进行权限请求\n请您点击允许" : tipmsg,
                    "我知道了",
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            if (position >= 0) {
                                PermissionActivityActivity.startSelf(AppManager.getCurrentActivity(), ann.typeWhenDeny(), ann.permissions());
                            }
                        }
                    }).show();

            //        }
        }
        return joinPoint.proceed();
    }
}
