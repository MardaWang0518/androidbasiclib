package com.hxh.component.basicannotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 标题: ApiServices.java
 * 作者: hxh
 * 日期: 2017/7/3 14:35
 * 描述: 此注解用于生成 请求代码
 * 如果是组件化，一个组件内，所有的请求放在一个接口内。在这个接口使用此注解
 * @value() 代表这些接口的BaseUrl。
 * 注意：1. 如果有多个BaseUrl，请开启多BaseUrl支持。
 *       2. 如果一个这个接口内部的部分请求的baseUrl不一样，请使用{@link ApiServicesOtherPath}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiServices {
    String value();
}
