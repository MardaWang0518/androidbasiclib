package com.hxh.component.basicore.util.aspj;


import com.hxh.component.basicore.ui.mrecycleview.NetResultBean;
import com.hxh.component.basicore.util.Log;
import com.hxh.component.basicore.util.Singleton;
import com.hxh.component.basicore.util.Utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

import com.hxh.component.basicannotation.annotation.DataSave;
import com.hxh.component.basicannotation.annotationenum.DataSourceType;
import rx.Observable;
import rx.Subscription;

/**
 * 暂时采用Sp做存储，因为发现目前的ORM框架，对多模块支持都不太友善，在这个项目中，存在各式各样的问题
 */
@Aspect
public class DataSaveAspj {
    //应用了DataSave注解，并且有个参数为ann
    public final String method_piex1 = "execution(@routerlib.hxh.com.corelib_annotation1.annotation.DataSave * *(..)) && @annotation(ann)";
    //上面的表达式并不是错误的，但是在你仔细检查表达式没发现问题的话，那么你可以这样描述你的表达式
    public final String method_piex = "@within(routerlib.hxh.com.corelib_annotation1.annotation.DataSave) || @annotation(ann)";

    @Pointcut(method_piex)
    public void method_datasavepiex(DataSave ann)
    {}


    @Around("execution(!synthetic * *(..)) && method_datasavepiex(ann)")
    public void dbsave(ProceedingJoinPoint point,final DataSave ann)
    {
        Log.d("--->切入");
        Subscription subscription = null;
        try
        {
            point.proceed();

            subscription =
                    Observable
                            .from(point.getArgs())
                            .filter(o -> o instanceof List || o instanceof NetResultBean)
                            .subscribe(o -> {

                                NetResultBean bean =  o instanceof NetResultBean ? ((NetResultBean) o) : null;
                             //   NetResultBean bean = ((NetResultBean) o);

                                if(ann.value() ==  DataSourceType.SP)
                                {
                                    if(null != bean)
                                    {
                                        String key1 = bean.items.get(0).getClass().getSimpleName();
                                        Log.d("存储网络数据"+key1);
                                        //先删除
                                        Utils.SP.editor()
                                                .remove(key1)
                                                .commit();
                                        //后添加
                                        Utils
                                                .SP
                                                .putString(key1, Singleton.defaultGson().toJson(bean.items));

                                    }else if(null == bean)
                                    {
                                        List result = o instanceof List ? ((List) o) : null;
                                        if(null != result && result.size()>=1)
                                        {
                                            String key1 =  result.get(0).getClass().getSimpleName();
                                            Log.d("存储网络数据"+key1);
                                            //先删除
                                            Utils.SP.editor()
                                                    .remove(key1)
                                                    .commit();
                                            //后添加
                                            Utils
                                                    .SP
                                                    .putString(key1, Singleton.defaultGson().toJson(result));
                                        }

                                    }
                                }else if(ann.value() ==  DataSourceType.DB)
                                {

                                }else
                                {

                                }
                            });


        }catch (Exception e)
        {
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            if (subscription != null) subscription.unsubscribe();
        }


    }
}
