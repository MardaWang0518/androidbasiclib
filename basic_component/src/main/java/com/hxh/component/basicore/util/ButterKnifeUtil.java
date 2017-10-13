package com.hxh.component.basicore.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ButterKnife的工具类
 */
public class ButterKnifeUtil {

    /**
     * 当你绑定Activity时候
     * @param obj
     * @return
     */
    public static Unbinder bind(Object obj)
    {
        if(obj instanceof Activity)
        {
           return ButterKnife.bind((Activity) obj);
        }
        return Unbinder.EMPTY;
    }

    /**
     * 当你绑定Fragment时候
     * @param tager
     * @param source
     * @return
     */
    public static Unbinder bind(Object tager,Object source)
    {
        if(source instanceof Activity) //应用于Activity
        {
            return ButterKnife.bind(tager,(Activity)source);
        }else if(source instanceof View) //应用于Fragment
        {

            return ButterKnife.bind(tager, ((View) source));
        }
        return Unbinder.EMPTY;
    }

    public static void unbind(Unbinder unbinder)
    {
        if(unbinder != Unbinder.EMPTY)
        {
            unbinder.unbind();
        }
    }
}
