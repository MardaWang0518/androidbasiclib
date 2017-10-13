package com.hxh.component.basicore.util.aspj;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hxh.component.basicannotation.annotationenum.PermissionDenyEnum;

/**
 * 权限检测器
 * @tipMsgBeforeRequest 当进行权限获取之前，提示用户的信息,此为一个友好的体验，用户可能并不想授权，但是如果你告诉用户，你必须需要权限，否则...
 * @typeWhenDeny 当出现权限被拒绝后，应该怎么办
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCheck {
    /**
    *@Title 需要获取的权限
    *@param 
    *@Time 10:19
    */
    String[] permissions();

    /**
     * @Title 当进行权限获取之前，提示用户的信息,此为一个友好的体验，用户可能并不想授权，但是如果你
     * 告诉用户，你必须需要它，否则...
     * @return
     */
    String tipMsgBeforeRequest() default "";

    /**
     * @Title 当出现权限被拒绝后，应该怎么办
     * @return
     */
    PermissionDenyEnum typeWhenDeny() default PermissionDenyEnum.IGNORE;
}
