package com.hxh.component.basicore.Base.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hxh on 2017/4/6.
 */
public abstract class AbsListAdapter<T> extends BaseAdapter{

    protected List<T> mDatas = new ArrayList<>();
    protected Context mContext;
    public AbsListAdapter(Context context)
    {
        this.mContext = context;
    }

    public AbsListAdapter(Context context, List<T> datas)
    {
        this.mContext = context;
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<T> datas)
    {
        if(null != datas && 0 != datas.size())
        {
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void setDatas(T[] datas)
    {
        if(null != datas && 0 !=datas.length)
        {
            setDatas(Arrays.asList(datas));
        }
    }

    public void addDatas(List<T> datas)
    {
        if(datas != null && 0!= datas.size())
        {
            if(this.mDatas == null)
            {
                mDatas = new ArrayList<>();
            }
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addDatas(T[] datas)
    {
        if(null != datas && 0 != datas.length)
        {
            addDatas(Arrays.asList(datas));
        }
    }

    public void addData(T data)
    {
        if(null != data)
        {
            mDatas.add(data);
            notifyDataSetChanged();
        }

    }

    public void updateData(int position,T data)
    {
        if(position > 0 && position < mDatas.size())
        {
            mDatas.set(position,data);
            notifyDataSetChanged();
        }

    }


    public List<T> getmDatas() {
        return mDatas;
    }

    //region 设置holder中某一个控件显示状态
    public void setVisible(View view)
    {
        view.setVisibility(View.VISIBLE);
    }

    public void setInVisible(View view)
    {
        view.setVisibility(View.INVISIBLE);
    }

    public void setEnable(View view)
    {
        view.setEnabled(true);
    }

    public void setDisable(View view)
    {
        view.setEnabled(false);
    }

    public void setGone(View view)
    {
        view.setVisibility(View.GONE);
    }

    //endregion
    //region 获取一些资源
    public Drawable getDrawable(int resid)
    {
        return this.mContext.getResources().getDrawable(resid);
    }

    public String getString(int resid)
    {
        return this.mContext.getResources().getString(resid);
    }

    public int getColor(int resId)
    {
        return this.mContext.getResources().getColor(resId);
    }

    //endregion


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mDatas!=null?mDatas.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
