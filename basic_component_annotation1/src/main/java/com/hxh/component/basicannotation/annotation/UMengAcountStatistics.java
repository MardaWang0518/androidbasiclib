package com.hxh.component.basicannotation.annotation;/**
 * Created by hxh on 2017/8/11.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建者：hxh
 * 时间：  2017/8/11
 * 描述：
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UMengAcountStatistics
{
    //输入UserId字段
    String value();
}
