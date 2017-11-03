package com.hxh.component.basicore.Base.delegate.interfaces;

import android.widget.ImageView;

import com.hxh.component.basicore.Base.onRecyItemClick;
import com.hxh.component.basicore.Base.onRecyItemLongClick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hxh on 2017/10/19.
 */

public interface IAdapterRelated<T,H> extends IViewRelated{
    void loadimg(ImageView iv,String url);
    void loadimg(ImageView iv,String ossStr,Class<?> classzz);

    T getDataAtPosition(int position);
    public List<T> getmDatas();
    public int getmDataSize();
    public void setDatas(List<T> mDatas);

    public void setDatas(T[] mDatas);



    public void addDatas(List<T> mDatas);

    public void addDatas(T[] mDatas);

    public void removeData(T data);

    public void removeData(int position);

    public void addData(T data);

    public void addData(int position,T data);

    /**
     * 更新数据的方法（用于下拉刷新）
     * @param datas
     */
    public void refreshDatas(List<T> datas);

    /**
     * 加载数据的方法（用于上拉加载）
     * @param datas
     */
    public void loadData(List<T> datas);

    /**
     * 设置你的点击监听器
     * @return
     */
    public onRecyItemClick<T, H> getmItemClickCallBack();

    public void setmItemClickCallBack(onRecyItemClick<T, H> mItemClickCallBack);

    public onRecyItemLongClick<T, H> getmItemLongClickCallBack();

    public void setmItemLongClickCallBack(onRecyItemLongClick<T, H> mItemLongClickCallBack);

    public void clearDatas();
}
