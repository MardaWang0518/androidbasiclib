package com.hxh.component.basicore.Base.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxh.component.basicore.Base.delegate.IAdapterDelegate;
import com.hxh.component.basicore.Base.delegate.interfaces.IAdapterRelated;
import com.hxh.component.basicore.Base.onRecyItemClick;
import com.hxh.component.basicore.Base.onRecyItemLongClick;
import com.hxh.component.basicore.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxh on 2017/4/6.
 */
public abstract class AbsRecycleAdapter<T,H extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<H> implements
        IAdapterRelated<T,H>{

    protected Context mContext;

    private IAdapterDelegate<T,H> mAdapterDelegate;

    public AbsRecycleAdapter(Context context)
    {
        mAdapterDelegate = new IAdapterDelegate<T, H>(this);
        this.mContext = context;
    }


    public AbsRecycleAdapter(Context context,onRecyItemClick<T, H> mItemClickCallBack)
    {
        mAdapterDelegate = new IAdapterDelegate<T, H>(this);
        this.mContext = context;
        this.setmItemClickCallBack(mItemClickCallBack);
    }


    @Override
    public int getItemCount() {
        return mAdapterDelegate.getmDataSize() ;
    }

    @Override
    public abstract H onCreateViewHolder(ViewGroup parent, int viewType);





    //region 对于数据的一些操作
    public boolean isDataSourceNull()
    {
        if(null == getmDatas() || 0 == getmDatas().size())
        {
            return true;
        }
        return false;
    }

    @Override
    public List<T> getmDatas() {
        return mAdapterDelegate.getmDatas();
    }

    @Override
    public int getmDataSize() {
        return mAdapterDelegate.getmDataSize();
    }

    @Override
    public void setDatas(List<T> mDatas) {
        mAdapterDelegate.setDatas(mDatas);
    }

    @Override
    public void setDatas(T[] mDatas) {
        mAdapterDelegate.setDatas(mDatas);
    }

    @Override
    public void addDatas(List<T> mDatas) {
        mAdapterDelegate.addDatas(mDatas);
    }

    @Override
    public void addDatas(T[] mDatas) {
        mAdapterDelegate.addDatas(mDatas);
    }

    @Override
    public void removeData(T data) {
        mAdapterDelegate.removeData(data);
    }

    @Override
    public void removeData(int position) {
        mAdapterDelegate.removeData(position);
    }

    @Override
    public void addData(T data) {
        mAdapterDelegate.addData(data);
    }

    @Override
    public void addData(int position, T data) {
        mAdapterDelegate.addData(position,data);
    }

    @Override
    public void refreshDatas(List<T> datas) {
        mAdapterDelegate.refreshDatas(datas);
    }

    @Override
    public void loadData(List<T> datas) {
        mAdapterDelegate.loadData(datas);
    }

    @Override
    public onRecyItemClick<T, H> getmItemClickCallBack() {
        return mAdapterDelegate.getmItemClickCallBack();
    }

    @Override
    public void setmItemClickCallBack(onRecyItemClick<T, H> mItemClickCallBack) {
        mAdapterDelegate.setmItemClickCallBack(mItemClickCallBack);
    }

    @Override
    public onRecyItemLongClick<T, H> getmItemLongClickCallBack() {
        return mAdapterDelegate.getmItemLongClickCallBack();
    }

    @Override
    public void setmItemLongClickCallBack(onRecyItemLongClick<T, H> mItemLongClickCallBack) {
        mAdapterDelegate.setmItemLongClickCallBack(mItemLongClickCallBack);
    }

    @Override
    public void clearDatas() {
        mAdapterDelegate.clearDatas();
    }

    //endregion
    //region 设置holder中某一个控件显示状态
    @Override
    public void visibe(View view) {
        mAdapterDelegate.visibe(view);
    }

    @Override
    public void visibe(View... views) {
        mAdapterDelegate.visibe(views);
    }

    @Override
    public void gone(View view) {
        mAdapterDelegate.gone(view);
    }

    @Override
    public void gone(View... views) {
        mAdapterDelegate.gone(views);
    }

    @Override
    public void inVisibe(View view) {
        mAdapterDelegate.inVisibe(view);
    }

    @Override
    public void inVisibe(View... views) {
        mAdapterDelegate.inVisibe(views);
    }

    @Override
    public void toastShort(String msg) {
        mAdapterDelegate.toastShort(msg);
    }

    @Override
    public void toastLong(String msg) {
        mAdapterDelegate.toastShort(msg);
    }

    @Override
    public String getRES_String(int resId) {
        return mAdapterDelegate.getRES_String(resId);
    }

    @Override
    public int getRES_Color(int resId) {
        return mAdapterDelegate.getRES_Color(resId);
    }

    @Override
    public Drawable getRES_drawable(int resId) {
        return mAdapterDelegate.getRES_drawable(resId);
    }

    @Override
    public boolean getRES_boolean(int resId) {
        return mAdapterDelegate.getRES_boolean(resId);
    }

    @Override
    public float getRES_dimen(int resId) {
        return mAdapterDelegate.getRES_dimen(resId);
    }

    //region TextView的操作
    @Override
    public String getText(EditText et) {
        return mAdapterDelegate.getText(et);
    }

    @Override
    public String getText(TextView tv) {
        return mAdapterDelegate.getText(tv);
    }

    @Override
    public String getText(EditText et, String defaulttext) {
        return mAdapterDelegate.getText(et,defaulttext);
    }

    @Override
    public String getText(TextView tv, String defaulttext) {
        return mAdapterDelegate.getText(tv,defaulttext);
    }

    @Override
    public String getText(TextView tv, int defaulttextResId) {
        return mAdapterDelegate.getText(tv,defaulttextResId);
    }

    @Override
    public String getText(EditText et, int defaulttextResId) {
        return mAdapterDelegate.getText(et,defaulttextResId);
    }

    @Override
    public String getHint(EditText et) {
        return mAdapterDelegate.getHint(et);
    }

    @Override
    public String getHint(EditText et, String defaulttext) {
        return mAdapterDelegate.getHint(et,defaulttext);
    }

    @Override
    public String getHint(EditText et, int defaulttextResId) {
        return mAdapterDelegate.getHint(et,defaulttextResId);
    }

    @Override
    public <V extends TextView> void setText(V tv, String msg) {
        mAdapterDelegate.setText(tv,msg);
    }

    @Override
    public <V extends TextView> void setText(V tv, int msgResId) {
        mAdapterDelegate.setText(tv,msgResId);
    }


    @Override
    public void loadimg(ImageView iv, String url) {
        mAdapterDelegate.loadimg(iv,url);
    }

    @Override
    public void loadimg(ImageView iv, int resid) {
        mAdapterDelegate.loadimg(iv,resid);
    }

    @Override
    public boolean isEmpty(List list) {
        return mAdapterDelegate.isEmpty(list);
    }

    @Override
    public boolean isEmpty(String msg) {
        return mAdapterDelegate.isEmpty(msg);
    }

    @Override
    public boolean isEmpty(CharSequence str) {
        return mAdapterDelegate.isEmpty(str);
    }

    @Override
    public boolean isEmpty(String... args) {
        return mAdapterDelegate.isEmpty(args);
    }

    @Override
    public boolean isEmpty(EditText text) {
        return mAdapterDelegate.isEmpty(text);
    }

    @Override
    public boolean isEmpty(EditText text, String tipmsg) {
        return mAdapterDelegate.isEmpty(text,tipmsg);
    }

    @Override
    public boolean isEmpty(TextView tv) {
        return mAdapterDelegate.isEmpty(tv);
    }

    @Override
    public boolean isEmpty(Object obj) {
        return mAdapterDelegate.isEmpty(obj);
    }
    //endregion

    @Override
    public void loadimg(ImageView iv, String ossStr, Class<?> classzz) {
        //Adapter不要执行此操作
        //mAdapterDelegate.loadimg(iv,ossStr,classzz);
    }

    //endregion

    public T getDataAtPosition(int position)
    {
        return mAdapterDelegate.getDataAtPosition(position);
    }

}
