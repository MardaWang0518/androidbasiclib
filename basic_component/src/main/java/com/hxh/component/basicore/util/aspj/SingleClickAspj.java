package com.hxh.component.basicore.util.aspj;


import com.hxh.component.basicore.util.aspj.util.AspjUtils;

import org.aspectj.lang.ProceedingJoinPoint;

import com.hxh.component.basicannotation.annotation.SingleClick;


/**
 * Created by hxh on 2017/6/29.
 */

public class SingleClickAspj {
   // static int TIME_TAG = R.id.click_time;
    private static final int MIN_CLICK_DELAY_TIME = 600;//间隔时间600ms
    private final String method_piex = "execution(@routerlib.hxh.com.corelib_annotation1.annotation.SingleClick * *(..)) && @annotation(ann)";



   // @Pointcut(method_piex)//根据SingleClick注解找到方法切入点
    public void methodAnnotated(SingleClick ann) {}

   // @Around("execution(!synthetic * *(..)) && methodAnnotated(ann)")//在连接点进行方法替换
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint,SingleClick ann) throws Throwable {
        if(null != ann)
        {
            int waittime = ann.waittime();
            if(!AspjUtils.App.isDoubleClick(waittime))
            {
                return joinPoint.proceed();//执行原方法
            }
        }
        return null;
    }
}
