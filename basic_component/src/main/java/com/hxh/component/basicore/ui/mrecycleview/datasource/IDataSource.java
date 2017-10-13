package com.hxh.component.basicore.ui.mrecycleview.datasource;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hxh on 2017/7/25.
 */
public interface IDataSource<E extends Object> {
    List<E> getDatas(HashMap<String,Object> param);
}
