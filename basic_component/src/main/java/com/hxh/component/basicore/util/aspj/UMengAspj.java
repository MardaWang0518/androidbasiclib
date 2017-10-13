package com.hxh.component.basicore.util.aspj;/**
 * Created by hxh on 2017/8/11.
 */

import com.umeng.analytics.MobclickAgent;

import org.aspectj.lang.ProceedingJoinPoint;

import com.hxh.component.basicannotation.annotation.UMengAcountStatistics;

/**
 * 创建者：hxh
 * 时间：  2017/8/11
 * 描述：
 */

public class UMengAspj
{

    //上面的表达式并不是错误的，但是在你仔细检查表达式没发现问题的话，那么你可以这样描述你的表达式
    public final String method_piex = "@within(routerlib.hxh.com.corelib_annotation1.annotation.UMengAcountStatistics) || @annotation(ann)";
   // @Pointcut(method_piex)
    public void pointcut_umengstas(UMengAcountStatistics ann){}

  //  @Around("execution(!synthetic * *(..)) && pointcut_umengstas(ann)")
    public void around_umeng(ProceedingJoinPoint point,UMengAcountStatistics ann)
    {
        Object[] objects = point.getArgs();
        if(null != objects)
        {
            try {
                String value = (String) objects[0].getClass().getField(ann.value()).get("");
                MobclickAgent.onProfileSignIn(value);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
