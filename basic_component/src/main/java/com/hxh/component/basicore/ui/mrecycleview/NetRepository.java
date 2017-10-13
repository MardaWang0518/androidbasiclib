package com.hxh.component.basicore.ui.mrecycleview;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by hxh on 2017/7/14.
 */
public interface NetRepository<T>{
    Observable<NetResultBean<T>> getData(HashMap<String, Object> params);
}
