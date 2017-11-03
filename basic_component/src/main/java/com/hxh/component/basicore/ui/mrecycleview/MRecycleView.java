package com.hxh.component.basicore.ui.mrecycleview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hxh.component.basicore.Base.adapter.BaseRecyAdapter;
import com.hxh.component.basicore.R;
import com.hxh.component.basicore.recycle.RecycleViewDivider;
import com.hxh.component.basicore.ui.EmptyView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import com.hxh.component.basicannotation.annotation.DataSave;
import com.hxh.component.basicannotation.annotationenum.DataSourceType;

/**
 *  自定义的RecycleView，你需要做的：
 *  1. 给我一个Adapter
 *  2. 设置一些属性（是否支持上拉或者下拉，以及分页参数的配置）
 *  3. 接口返回值必须是被  NetResultBean<你的实体> 包住
 *
 *  D 代表一个实体类型，一般为Adapter里面item的数据类型
 */
public class MRecycleView<D> extends FrameLayout{
    //region contrau
    public MRecycleView(Context context) {
        super(context);
        init(context);
    }

    public MRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    //endregion

    private EmptyView empty_view;
    private Context mContext;
    private XRecyclerView recycleview;
    private View mRootView;
    private RecyclerView.LayoutManager mDefaultLayoutManager;
    private MDataSource<D> mAdapterDataSource;// 负责数据源的绑定
    private boolean isEnableRefresh=false, isEnableLoading=false,isRefresing,isLoading;//默认禁止上拉，下拉
    private BaseRecyAdapter mAdapter;

    private void init(Context context)
    {
        this.mContext = context;
        mRootView = inflate(mContext, R.layout.layout_customrecycleview,this);


        mAdapterDataSource = new MDataSource<D>(new AdaterDataSourceImpl<D>());
        empty_view = (EmptyView) mRootView.findViewById(R.id.empty_view);
        recycleview = (XRecyclerView) mRootView.findViewById(R.id.recycleview1);

        mDefaultLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);

        recycleview.setLayoutManager(mDefaultLayoutManager);

        recycleview.setHasFixedSize(true);

        recycleview.setItemAnimator(new DefaultItemAnimator());

        enableRecycleRefresh();

    }


    //region 你可以用的方法

    /**
     * 是否启用Recycleview 的刷新和加载功能
     */
    private void enableRecycleRefresh() {
        recycleview.setPullRefreshEnabled(this.isEnableRefresh);
        recycleview.setLoadingMoreEnabled(this.isEnableLoading);
        recycleview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if(isEnableRefresh){
                    isRefresing = true;
                    mAdapterDataSource.fetch();

                }
            }

            @Override
            public void onLoadMore() {
                if(isEnableLoading){
                    isLoading = true;
                    mAdapterDataSource.fetch();

                }
            }
        });
    }

    /**
     * 目前困难点在于ViewHolder，所以这个Adapter暂且由你自己去实现，
     * @param adapter
     * @return
     */
    public MRecycleView setAdapter(BaseRecyAdapter adapter)
    {
        this.mAdapter = adapter;
        this.recycleview.setAdapter(mAdapter);
        return this;
    }

    /**
     * 设置Recycle的LayoutMangager
     * @param mana
     * @return
     */
    public MRecycleView setLayoutManager(RecyclerView.LayoutManager mana)
    {
        this.mDefaultLayoutManager = mana;
        recycleview.setLayoutManager( this.mDefaultLayoutManager);
        return this;
    }

    public MRecycleView setDividerItemDecoration(RecyclerView.ItemDecoration itemDecoration)
    {

        recycleview.addItemDecoration(itemDecoration);
        return this;
    }

    /**
     *
     * @param gridNum
     * @return
     */
    public MRecycleView setLayoutManager(int gridNum)
    {
        this.mDefaultLayoutManager = new GridLayoutManager(this.getContext(),gridNum);
        recycleview.setLayoutManager( this.mDefaultLayoutManager);
        return this;
    }

    public MRecycleView setDiViderHeight(int height)
    {
        recycleview.addItemDecoration(new RecycleViewDivider(getContext(),20));
        return this;
    }

    public MRecycleView setLayoutManager(int oreition, boolean isreverse)
    {
        this.mDefaultLayoutManager = new LinearLayoutManager(this.getContext(),oreition,false);
        recycleview.setLayoutManager( this.mDefaultLayoutManager);
        return this;
    }

    /**
     * 设置是否禁止刷新和加载
     * @param isEnableRefresh
     * @param isEnableLoading
     * @return
     */
    public MRecycleView setLoadingEnable(boolean isEnableRefresh, boolean isEnableLoading)
    {
        this.isEnableRefresh = isEnableRefresh;
        this.isEnableLoading = isEnableLoading;
        enableRecycleRefresh();
        return this;
    }


    //endregion

    //region 可以得到的
    public RecyclerView.LayoutManager getDefaultLayoutManager()
    {
        return mDefaultLayoutManager;
    }


    /**
     *
     * @param
     * @author hxh
     * @time 2017/7/20 10:57
     * @return
     */
    public MDataSource<D> getDataRepositoryBuilder()
    {
        return mAdapterDataSource;
    }

    private void closeRefresh()
    {
        if(isEnableLoading && isLoading)
        {
            isLoading = false;
            this.recycleview.loadMoreComplete();
        }

        if(isEnableRefresh && isRefresing)
        {
            isRefresing = false;
            this.recycleview.refreshComplete();
        }
    }

    //endregion



    private class AdaterDataSourceImpl<P> implements IDataSourceView
    {



        //@DataSave(DataSourceType.SP)
        @Override
        public void setNetData(List result) {
            if (recycleview.getVisibility() == GONE) recycleview.setVisibility(VISIBLE);
            if (empty_view.getVisibility() == VISIBLE) empty_view.setVisibility(GONE);
            if(isEnableLoadAndRefresh() &&isLoadMore())
            {
                mAdapter.loadData(result);
            }else
            {
                mAdapter.refreshDatas(result);
            }
            closeRefresh();
        }


        @Override
        public void setLocalData(List result) {
            if(recycleview.getVisibility()==GONE)recycleview.setVisibility(VISIBLE);
            if(empty_view.getVisibility()==VISIBLE)empty_view.setVisibility(GONE);

            recycleview.refreshComplete();
            mAdapter.setDatas(result);
            closeRefresh();
        }

        @Override
        public void setEmpty() {
            recycleview.setVisibility(GONE);
            empty_view.setVisibility(VISIBLE);
            closeRefresh();
        }

        @Override
        public void clearData() {
            if(null != mAdapter)
            {
                mAdapter.clearDatas();
                closeRefresh();
            }
        }

        @Override
        public boolean isRefresh() {
            return isRefresing;
        }

        @Override
        public boolean isLoadMore() {
            return isLoading;
        }

        @Override
        public boolean isEnableLoadAndRefresh() {
            return isEnableRefresh&&isEnableLoading;
        }

        @Override
        public void enableRefreshAndLoadMore() {
            setLoadingEnable(true,true);
        }

        @Override
        public BaseRecyAdapter getAdapter() {
            return mAdapter;
        }
    }

    public XRecyclerView getRecycleView()
    {
        return recycleview;
    }


}
