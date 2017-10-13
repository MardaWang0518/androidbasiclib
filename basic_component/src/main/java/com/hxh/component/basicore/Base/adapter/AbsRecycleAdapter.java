package com.hxh.component.basicore.Base.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class AbsRecycleAdapter<T,H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H>{
    protected List<T> mDatas = new ArrayList<>();
//    protected Map<T,T> mDatas_map = new HashMap<>();
    private onRecyItemClick<T,H> mItemClickCallBack;
    private onRecyItemLongClick<T,H> mItemLongClickCallBack;
    protected Context mContext;




    public AbsRecycleAdapter(Context context)
    {
        this.mContext = context;
    }


    @Override
    public int getItemCount() {
        return mDatas.size() ;
    }

    @Override
    public abstract H onCreateViewHolder(ViewGroup parent, int viewType);


    //region 对于数据的一些操作
    public List<T> getmDatas() {
        return mDatas !=null?mDatas:null;
    }
    public int getmDataSize()
    {
        return mDatas!=null?mDatas.size():0;
    }

    public boolean isDataSourceNull()
    {
        if(null == getmDatas() || 0 == getmDatas().size())
        {
            return true;
        }
        return false;
    }


    public void setDatas(List<T> mDatas) {
        if(null != mDatas)
        {
            this.mDatas.clear();
            this.mDatas.addAll(mDatas);
        }
        notifyDataSetChanged();

    }

    public void setDatas(T[] mDatas)
    {
        if(null != mDatas)
        {
            setDatas(Arrays.asList(mDatas));
        }
    }



    public void addDatas(List<T> mDatas)
    {
        int presize = this.mDatas.size();
        if(null != mDatas && mDatas.size() >0)
        {
            if(this.mDatas == null)
            {
                this.mDatas = new ArrayList<>();
            }
            this.mDatas.addAll(mDatas);
            //通知有数据添加，实现动画
            notifyItemRangeInserted(presize,mDatas.size());
        }
        notifyDataSetChanged();
    }

    public void addDatas(T[] mDatas)
    {
        if(null != mDatas && mDatas.length > 0)
        {
            addDatas(Arrays.asList(mDatas));
        }
    }

    public void removeData(T data)
    {
        if(null != mDatas)
        {
            if(mDatas.contains(data))
            {
                int position = mDatas.indexOf(data);
                mDatas.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position);
            }
        }
    }

    public void removeData(int position)
    {
        if(null != mDatas && mDatas.size() > position)
        {
            mDatas.remove(position);
            notifyItemRemoved(position);
            notifyItemChanged(position);
        }
    }

    public void addData(T data)
    {
        if(null != mDatas && null != data)
        {
            mDatas.add(data);
            notifyItemInserted(this.mDatas.size());
            notifyDataSetChanged();
        }
    }

    public void addData(int position,T data)
    {
        if(null != mDatas && null != data && mDatas.size() >position)
        {
            mDatas.remove(data);
            mDatas.add(position,data);
            notifyItemInserted(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 更新数据的方法（用于下拉刷新）
     * @param datas
     */
    public void refreshDatas(List<T> datas)
    {
        if(null != datas && 0!=datas.size())
        {
            if (null == mDatas || mDatas.size() == 0) {
                setDatas(datas);
            } else {
                if(mDatas.size()<=10 && datas.size()<=10)
                {
                    setDatas(datas);
                    return;
                }
                int insertposition=0;
                int insertcount =0;

                for (int i = 0; i < datas.size(); i++) {
                    if(i <= mDatas.size()-1)
                    {
                        mDatas.set(i,datas.get(i));
                    }else{
                        mDatas.add(datas.get(i));
                        insertposition = i;
                        ++insertcount;
                    }
                }

                notifyItemRangeChanged(0,datas.size());
                if(insertcount>0)
                {
                    notifyItemRangeInserted(insertposition,insertcount);
                }
                notifyDataSetChanged();
            }

        }

    }

    /**
     * 加载数据的方法（用于上拉加载）
     * @param datas
     */
    public void loadData(List<T> datas)
    {
        if(null != datas && 0!=datas.size())
        {
            int oldsize = mDatas.size();
            mDatas.addAll(datas);
            notifyItemRangeInserted(oldsize,datas.size());
        }
    }

    /**
     * 设置你的点击监听器
     * @return
     */
    public onRecyItemClick<T, H> getmItemClickCallBack() {
        return mItemClickCallBack;
    }

    public void setmItemClickCallBack(onRecyItemClick<T, H> mItemClickCallBack) {
        this.mItemClickCallBack = mItemClickCallBack;
    }

    public onRecyItemLongClick<T, H> getmItemLongClickCallBack() {
        return mItemLongClickCallBack;
    }

    public void setmItemLongClickCallBack(onRecyItemLongClick<T, H> mItemLongClickCallBack) {
        this.mItemLongClickCallBack = mItemLongClickCallBack;
    }

    public void clearDatas() {
        if (null != mDatas)
        {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }
    //endregion
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

    public T getDataAtPosition(int position)
    {
        if(null != mDatas&& 0!= mDatas.size())
        {
            return mDatas.get(position);
        }
        throw new IllegalStateException("you datas is null!");
    }

}
