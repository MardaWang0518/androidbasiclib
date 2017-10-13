package com.hxh.component.basicore.util.aspj;

import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by hxh on 2017/6/27.
 */

public class SafeAspj {
    private static final String method_piex = "execution(@routerlib.hxh.com.corelib_annotation1.annotation.Safe * *(..))";
    private static final String filed_piex = "";

   // @Pointcut(method_piex)
    public void method_piex(){}

   // @Around("execution(!synthetic * *(..)) && method_piex()")
    public Object addSoftCode(ProceedingJoinPoint point)
    {
        Object result = null;
        try
        {
            result = point.proceed();
        }catch (Exception e)
        {
            Log.e("===================异常已上报==============");
            if(null != CoreLib.getInstance().getAppComponent().globalBugManager())
            {
                CoreLib
                        .getInstance()
                        .getAppComponent()
                        .globalBugManager()
                        .postBug(e);
            }
//            CrashReport.postCatchedException(e);
        } catch (Throwable throwable) {
            if(null != CoreLib.getInstance().getAppComponent().globalBugManager())
            {
                CoreLib
                        .getInstance()
                        .getAppComponent()
                        .globalBugManager()
                        .postBug(throwable);
            }
        }
        return result;
    }


}
