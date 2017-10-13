package com.hxh.component.basicore.util.aspj;

import com.hxh.component.basicore.Config;
import com.hxh.component.basicore.util.Utils;
import com.hxh.component.basicore.util.aspj.util.AspjManager;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by hxh on 2017/6/29.
 */

public class CheckLoginAspj {
    public String TAG = Config.SP_DEFAULT_USERINFO_TAG;
    public final String  method_piex = "within(@routerlib.hxh.com.corelib_annotation1.annotation.CheckLogin *) || @annotation(ann)";

    //@Pointcut(method_piex)
    public void method_pointpiex(){}


  //  @Around("execution(!synthetic * *(..)) && method_pointpiex()")
    public void chckLogin(ProceedingJoinPoint point) throws Throwable {
        com.hxh.component.basicore.util.Log.d("登录切入");
        String isNoUser = Utils.SP.getString(TAG);
        Object resule = null;
        if(null == isNoUser || "".equals(isNoUser))
        {
            AspjManager.CheckLoginManager manager = AspjManager.CheckLoginManager.getInstance();
          //  manager.showLoginview();
        }else
        {
            resule = point.proceed();
        }

    }
}
