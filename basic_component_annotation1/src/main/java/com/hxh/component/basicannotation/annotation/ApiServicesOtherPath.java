package com.hxh.component.basicannotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标题: ApiServicesOtherPath.java
 * 作者: hxh
 * 日期: 2017/7/3 14:40
 * 描述: 该注解配合{@link ApiServices}使用，具体使用方法详见{@link ApiServices}
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ApiServicesOtherPath {
    String value() ;
}
