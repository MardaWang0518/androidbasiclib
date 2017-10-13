package com.hxh.component.basicore.ui.mrecycleview;

import java.util.HashMap;
import java.util.List;

import rx.Observable;

/**
 * Created by hxh on 2017/7/14.
 */
public interface LocalRepository<T> {
    Observable<List<T>> getData(HashMap<String, Object> param);

}
