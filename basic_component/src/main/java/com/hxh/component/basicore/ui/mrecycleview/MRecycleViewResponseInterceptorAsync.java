package com.hxh.component.basicore.ui.mrecycleview;

import java.util.List;

import rx.Observable;

/**
 * Created by hxh on 2017/10/19.
 */

public interface MRecycleViewResponseInterceptorAsync<D> {
    Observable<List<D>> setData(List<D> datas);

}
