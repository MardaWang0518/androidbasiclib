package com.hxh.component.basicore.ui.mrecycleview.datasource;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.hxh.component.basicore.util.Singleton;
import com.hxh.component.basicore.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by hxh on 2017/7/25.
 */
public class DataSource_SP<E> implements IDataSource {

    private List<E> mDatas;

    public DataSource_SP getDatas(Class<E> classzz) {
        if (null == classzz) {
            throw new IllegalStateException("class can't is null");
        }

        String datastr = Utils.SP.getString(classzz.getSimpleName());


        mDatas = Utils.Text.isEmpty(datastr)
                ?
                new ArrayList<E>()
                :
                JSON.parseArray(datastr,classzz);


        return this;
    }

    public List<E> list()
    {
        return mDatas;
    }

    @Override
    public List<E> getDatas(HashMap param) {

        if (null == param) {
            throw new IllegalStateException("class can't is null");
        }
        if(Utils.Text.isEmpty(param.get("classname")))
        {
            return  new ArrayList<E>();
        }

        String datastr = Utils.SP.getString(param.get("classname").toString());
        return Utils.Text.isEmpty(datastr)
                ?
                new ArrayList<E>()
                :
                ((List<E>) Singleton.defaultGson().fromJson(datastr, new TypeToken<List<E>>() {
                }.getType()));
    }

    public Observable<List<E>> asObservable()
    {
        return Observable.defer(new Func0<Observable<List<E>>>() {
            @Override
            public Observable<List<E>> call() {
                return Observable.just(mDatas);
            }
        });
    }

}
