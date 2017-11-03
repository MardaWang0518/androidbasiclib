package com.hxh.component.basicore.Base;

/**
 * Recycleview Item点击事件
 * @param <T>
 * @param <H>
 */
public  interface onRecyItemClick<T,H> {
    /**
     *
     * @param position   当前位置
     * @param tag        当前标签（用控件id）
     * @param data       当前数据
     * @param holder     当前holder
     */
   public void onClick(int position,int tag,T data,H holder);


}
