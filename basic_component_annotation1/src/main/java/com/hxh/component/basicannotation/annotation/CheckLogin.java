package com.hxh.component.basicannotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hxh.component.basicannotation.annotationenum.ShowType;

/**
 * @Title 检查是否登录
 * 请注意，如果你使用了此注解，我默认会从sp 中得到用户信息，sp 的key 为：
 * {@link com.hxh.component.basicore.Config.}  中 SP_USERINFO_TAG 字段
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckLogin {
   ShowType type() default ShowType.TOAST;
}
