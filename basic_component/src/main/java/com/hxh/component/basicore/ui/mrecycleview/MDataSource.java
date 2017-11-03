package com.hxh.component.basicore.ui.mrecycleview;

import android.os.Build;

import com.hxh.component.basicore.rx.resetfulhttpstyle.RESTFULProgressSubscribe;
import com.hxh.component.basicore.mvp.Mapper;
import com.hxh.component.basicore.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.functions.Action1;

/**
 * Adapter数据源
 *
 * @param <D> 一个实体类型
 */
public class MDataSource<D> {

    /**
     * 当没有数据时候，清除数据，并且什么也不展示
     */
    public static int EMPTY_WHENNODATA = 0x1;
    /**
     * 当没有数据时候，展示EmptyView（使用Adapter之前的数据）
     */
    public static int EMPTYVIEW_WHENNODATA = 0x2;
    /**
     * 当没有数据时候，强制展示EmptyView（舍弃Adapter）
     */
    public static int EMPTYVIEWFORCED_WHENNODATA = 0x3;

    //    /**
    //     * 当存在数据时候，进行覆盖数据，而不是刷新
    //     * 场景如： 有3种类型的书籍，每种数据不一样，每次将数据进行覆盖
    //     */
    public static int HAVEDATA_FORCEDCOVER = 0x4;

    public static int HAVEDATA_ADDTOFRONT = 0x5;

    public static int HAVEDATA_ADDTOEND = 0x6;


    public MDataSource(IDataSourceView<D> source) {
        this.mView = source;
    }

    private NetRepository mNetRepository;
    private LocalRepository mLocalRepository;
    private HashMap<String, Object> mParams;//参数
    private PaginationBuilder mPaginBuilder = null;
    private IDataSourceView<D> mView;
    private ParamCallback mParamCallback;
    private List<D> mFixedDatas;
    private boolean mFixedDataIsTop;//是否让固定数据处于最顶端
    /**
     * 标明只设置了参数的Key,和@Link mParamKeys 共用
     */
    private boolean isOnlyKey;
    /**
     * 设置参数的Key,Value通过Fetch(value...)传入，适用于已经有数据，不需要请求接口的场景
     */
    private String[] mParamKeys;
    /**
     * 当没有响应数据时，应该怎样处理
     */
    private int mNodataTypeWhenRequest = EMPTY_WHENNODATA;
    /**
     * 当有响应数据时，应该怎样处理
     */
    private int mHaveDataTypeWhenResponse = HAVEDATA_ADDTOFRONT;
    /**
     * 当没有数据时候的回调器
     */
    private NoDataCallback mNoDataCallback;
    /**
     * 初始页码（有可能初始页码是1，也有可能是2，根据用户来定，必须）
     */
    private int initPageIndex;

    /**
     * 结果拦截器
     */
    private MRecycleViewResponseInterceptor<D> mResInterceptor; //结果拦截器
    private MRecycleViewResponseInterceptorAsync<D> mResInterceptorAsync; //结果拦截器
    private boolean isEnableInterceptor;

    private boolean isEnableTranFrom;
    private Class<? extends Mapper> targetMapper;

    //region 配置

    /**
     * 设置网络加载器    一般如： APIFactor::方法
     *
     * @param mNetRepository
     * @param <T>
     * @return
     */
    public <T> MDataSource setNetRepository(NetRepository<T> mNetRepository) {
        this.mNetRepository = mNetRepository;
        return this;
    }

    /**
     * 设置本地加载器
     *
     * @param mLocalRepository
     * @param <T>
     * @return
     */
    public <T> MDataSource setLocalRepository(LocalRepository<T> mLocalRepository) {
        this.mLocalRepository = mLocalRepository;
        return this;
    }

    /**
     * 设置固定的数据，他将会拼接到网络数据或者本地数据库中
     *
     * @param list  固定数据
     * @param isTop 是否将固定数据处于顶端展示
     * @return
     */
    public MDataSource setFixedData(List<D> list, boolean isTop) {
        this.mFixedDatas = list;
        this.mFixedDataIsTop = isTop;
        return this;
    }


    /**
     * 设置请求参数 键值对
     *
     * @param params
     * @return
     */
    public MDataSource setParams(HashMap<String, Object> params) {
        this.mParams = params;
        return this;
    }

    /**
     * 设置单个请求参数 键值对
     *
     * @param key
     * @param value
     * @return
     */
    public MDataSource setParams(String key, Object value) {
        if (null == this.mParams) {
            this.mParams = new HashMap<String, Object>();
        }
        this.mParams.put(key, value);
        return this;
    }

    /**
     * 动态设置请求参数 键值对
     *
     * @param para
     * @return
     */
    public MDataSource setParams(ParamCallback para) {
        this.mParamCallback = para;
        return this;
    }

    /**
     * 只设置请求参数的Key值，然后在fetch时候，依次传入Value值
     *
     * @param keys
     * @return
     */
    public MDataSource setParamsKey(String... keys) {
        isOnlyKey = true;
        if (null == this.mParams) {
            this.mParams = new HashMap<String, Object>();
        }
        for (String key : keys) {
            this.mParams.put(key, "");
        }
        this.mParamKeys = keys;
        return this;
    }

    /**
     * 设置分页构造器
     *
     * @param builder
     * @return
     */
    public MDataSource setPagination(PaginationBuilder builder) {
        this.mPaginBuilder = builder;
        this.initPageIndex = builder.pageIndex;
        if (null != builder && null != mView) mView.enableRefreshAndLoadMore();

        return this;
    }

    /**
     * 当没有数据时，页面将如何显示
     *
     * @param type EMPTYVIEWFORCED_WHENNODATA     EMPTY_WHENNODATA ...
     * @return
     */
    public MDataSource setNoDataStateWhenRequest(int type) {
        this.mNodataTypeWhenRequest = type;
        return this;
    }

    public MDataSource setHaveDataStateWhenRequest(int type) {
        this.mHaveDataTypeWhenResponse = type;
        return this;
    }

    /**
     * 设置结果拦截器，你可以在数据响应时候，处理一些事情
     *
     * @param interceptor 拦截器
     * @return
     */
    public MDataSource setResponseInterceptor(MRecycleViewResponseInterceptor<D> interceptor) {
        this.mResInterceptor =interceptor;
        isEnableInterceptor  = true;
        this.mResInterceptorAsync =null;
        return this;
    }
    public MDataSource setResponseInterceptorAsync(MRecycleViewResponseInterceptorAsync<D> interceptor) {
        this.mResInterceptorAsync =interceptor;
        this.mResInterceptor =null;
        isEnableInterceptor  = true;
        return this;
    }


    /**
     * 设置结果转换，你可以把原结果转换为另外一个结果，用途：
     * 1. 很多时候，DTO需要转成VO对象，你可以使用上面的setResponseInterceptor 设置拦截器后自己转换，
     * 也可以使用此方法，设置转换目标类，即可把结果转换成目标对象，并且传递给Adapter
     * ,如果你还想对结果进行二次处理，请调用setResponseInterceptor 设置拦截器
     *
     * @param target
     * @return
     */
    public MDataSource setResponseTransfrom(Class<? extends Mapper> target) {
        this.isEnableTranFrom = true;
        this.targetMapper = target;
        return this;
    }

    /**
     * @param type
     * @return
     * @Title 当没有数据时，页面将如何显示
     */
    public MDataSource setNoDataStateWhenRequest(int type, NoDataCallback callback) {
        this.mNodataTypeWhenRequest = type;
        this.mNoDataCallback = callback;
        return this;
    }


    public interface ParamCallback {
        void getParam(HashMap<String, Object> parm);
    }

    public static abstract class NoDataCallback {

        public abstract void onNoData();

        public void onHaveData() {

        }
    }


    /**
     * @Title 关联数据源并且获取数据
     */
    public void fetch() {
        loadData(null);
    }



    /**
     * @param resulelist 从别的界面携带过来的数据
     * @Title 当你从别的界面跳转携带过来数据，不要再加载时候
     */
    public void fetch(List<D> resulelist) {
        if(isEnableTranFrom)
        {

        }
        loadData(resulelist);
    }

    /**
     * @param paraValues
     * @Title 当你调用setParamsKey 方法并且设置了参数名字，此时你可以通过此方法设置对应的value
     */
    public void fetch(Object... paraValues) {
        if (isOnlyKey) {
            for (int i = 0; i < this.mParamKeys.length; i++) {
                if (i <= paraValues.length) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        this.mParams.replace(this.mParamKeys[i], paraValues[i]);
                    } else {
                        this.mParams.remove(this.mParamKeys[i]);
                        this.mParams.put(this.mParamKeys[i], paraValues[i]);
                    }
                }
            }
        } else {
            throw new IllegalStateException("you can't call me,because First you need to call setParamsKey(String... keys)");
        }
        loadData(null);
    }

    private void loadData(List<D> list) {
        //region local
        if (null == mNetRepository) {
            if (null == list || 0 == list.size()) {
                if (mNodataTypeWhenRequest == EMPTYVIEWFORCED_WHENNODATA) {
                    showViewWhenNoData();
                } else if (null == mView.getAdapter().getmDatas()) {
                    showViewWhenNoData();
                }

            } else {
                mView.clearData();
                if (null != mNoDataCallback) {
                    mNoDataCallback.onHaveData();
                }
                mView.setLocalData(list);
            }
            return;
        }
        //endregion
        if (null != list && 0 != list.size()) {
            if (null != mNoDataCallback) {
                mNoDataCallback.onHaveData();
            }
            mView.setNetData(list);
        } else {
            //如果没有网络
            if (!Utils.NetWork.isConnected()) {
                getDbData();
                //从数据库中得到
                return;
            }

            initFetch();

            mNetRepository
                    .getData(getParam())
                    .subscribe(new RESTFULProgressSubscribe<NetResultBean<D>>() {
                        @Override
                        public void _OnError(Throwable msg) {
                            getDbData();
                        }

                        @Override
                        public void _OnNet(NetResultBean<D> o) {
                            if (null == o.items || 0 == o.items.size()) {
                                showViewWhenNoData();
                            } else {
                                if (null != mNoDataCallback) {
                                    mNoDataCallback.onHaveData();
                                }
                                if (mHaveDataTypeWhenResponse == HAVEDATA_FORCEDCOVER) {
                                    mView.setLocalData(checkFixedData(o.items));
                                } else {
                                    if(!isEnableInterceptor)
                                    {
                                        mView.setNetData(checkIsNeedTranFromData(checkFixedData(o.items)));
                                    }else
                                    {

                                        checkIsEnableInterceptor(checkIsNeedTranFromData(checkFixedData(o.items)));
                                    }
                                }

                            }
                        }
                    });
        }
    }


    private void showViewWhenNoData() {
        if (mNodataTypeWhenRequest == EMPTY_WHENNODATA) {
            mView.clearData();
        } else if (mNodataTypeWhenRequest == EMPTYVIEW_WHENNODATA) {
            mView.setEmpty();
            if (null != mNoDataCallback) mNoDataCallback.onNoData();
        } else if (mNodataTypeWhenRequest == EMPTYVIEWFORCED_WHENNODATA) {
            mView.clearData();
            mView.setEmpty();
            if (null != mNoDataCallback) mNoDataCallback.onNoData();
        }
    }

    public HashMap<String, Object> getParam() {
        if (null != mParams) {
            return mParams;
        } else if (null != mParamCallback) {
            mParamCallback.getParam(this.mParams);
            return this.mParams;
        } else {
            return mParams = new HashMap<>();
        }
    }

    private void getDbData() {
        if (null != mLocalRepository) {
            mLocalRepository
                    .getData(mParams)
                    .subscribe(new Action1<List>() {
                                   @Override
                                   public void call(List list) {

                                       if (null == list || 0 == list.size()) {
                                           showViewWhenNoData();
                                       } else {
                                           mView.setLocalData(checkFixedData(list));
                                       }
                                   }
                               },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    showViewWhenNoData();
                                }
                            });
        }
    }

    /**
     * 检查有没有固定数据
     *
     * @param o
     * @return
     */
    private List<D> checkFixedData(List<D> o) {
        if (null != o && 0 != o.size()) {
            if (null != mFixedDatas && 0 != mFixedDatas.size()) {
                //排重,需要你的实体是要hashcode和equal
                o.removeAll(mFixedDatas);
                if (mFixedDataIsTop) {
                    o.addAll(0, mFixedDatas);
                } else {
                    o.addAll(mFixedDatas);
                }
            }
        }
        return o;
    }

    private List<D> checkIsNeedTranFromData(List<D> datas) {

        if (isEnableTranFrom) {
            List target = new ArrayList();
            for (D item : datas) {
                if (item instanceof Mapper) {
                    target.add(((Mapper) item).transform());
                } else {
                    throw new IllegalStateException("if you enable responseTransFrom,So you have to get your Bean implemented in the Mapper<T>");
                }
            }
            datas.clear();
            datas.addAll(target);
        }
        return datas;
    }

    private List<D> checkIsEnableInterceptor(List<D> datas)
    {
        if (null != mResInterceptor) {

            return mResInterceptor.setData(datas);
        }else if(null != mResInterceptorAsync)
        {
            mResInterceptorAsync
                    .setData(datas)
                    .subscribe(new RESTFULProgressSubscribe<List<D>>() {
                        @Override
                        public void _OnError(Throwable msg) {

                        }

                        @Override
                        public void _OnNet(List<D> ds) {

                        }
                    });
        }
        return datas;
    }

    private void initFetch() {
        // if(null == mNetRepository)throw new IllegalStateException("newRepository can't is null");

        //看是否支持分页加载
        if (mView.isEnableLoadAndRefresh()) {
            if (null == mPaginBuilder) {
                throw new IllegalStateException("you open recycleview's refresh and loadmore,but you not setter refresh param!");
            } else {
                if (!getParam().containsKey(mPaginBuilder.getPageIndexFieldName())) {
                    getParam().put(mPaginBuilder.getPageIndexFieldName(), mPaginBuilder.getPageIndex());
                }
                if (!getParam().containsKey(mPaginBuilder.getPageSizeFieldName())) {
                    getParam().put(mPaginBuilder.getPageSizeFieldName(), mPaginBuilder.getPageSize());
                }
            }
        }
        if (null != mPaginBuilder) {
            if (mView.isRefresh()) {
                getParam().remove(PaginationBuilder.PAGEKEY);
                getParam().put(PaginationBuilder.PAGEKEY, initPageIndex);
            } else if (mView.isLoadMore()) {
                getParam().remove(PaginationBuilder.PAGEKEY);
                getParam().put(PaginationBuilder.PAGEKEY, ++mPaginBuilder.pageIndex);
            }
        }
    }

}
