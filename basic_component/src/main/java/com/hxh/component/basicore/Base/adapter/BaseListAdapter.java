package com.hxh.component.basicore.Base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hxh on 2017/4/6.
 */
public abstract class BaseListAdapter<T,H>  extends AbsListAdapter<T> {
    public BaseListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder = null;
        T item = mDatas.get(position);
        if(null == convertView)
        {
            convertView = LayoutInflater.from(mContext).inflate(getLayoutId(),parent,false);
            holder = newViewHolder(convertView);
            convertView.setTag(holder);
        }else
        {
            holder = (H) convertView.getTag();
        }

        nextOpearOngetConvertView(holder,item,position);
        return null;
    }

    public abstract int getLayoutId();
    public abstract H newViewHolder(View itemview);
    public abstract void nextOpearOngetConvertView(H holder,T data,int position);
}
