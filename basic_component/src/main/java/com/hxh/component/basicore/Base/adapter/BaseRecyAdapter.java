package com.hxh.component.basicore.Base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxh.component.basicore.Base.onRecyItemClick;


/**
 * Adaoter基类（不支持多布局）
 * @param <T>   ItemBean的类型
 * @param <H>   Item的Holder
 */
public abstract class BaseRecyAdapter<T,H extends RecyclerView.ViewHolder> extends AbsRecycleAdapter<T,H> {
    /**
     * item点击事件中代表着整体布局
     */
    public static final int ITEMMAINVIEW = 0;

    public BaseRecyAdapter(Context context) {
        super(context);
    }

    public BaseRecyAdapter(Context context,onRecyItemClick<T, H> mItemClickCallBack) {
        super(context,mItemClickCallBack);
        setmItemClickCallBack(mItemClickCallBack);
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),parent,false);
        return newViewHolder(view);
    }

    public abstract H newViewHolder(View view);
    public abstract int getLayoutId();

}
