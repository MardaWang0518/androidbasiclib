package com.hxh.component.basicore.Base;

/**
 * Created by hxh on 2017/4/7.
 */
public abstract class onRecyItemClick<T,H> {
    /**
     *
     * @param position   当前位置
     * @param tag        当前标签（用控件id）
     * @param data       当前数据
     * @param holder     当前holder
     */
   public abstract void onClick(int position,int tag,T data,H holder);


}
