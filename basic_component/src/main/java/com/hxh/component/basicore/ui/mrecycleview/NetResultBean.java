package com.hxh.component.basicore.ui.mrecycleview;

import java.util.List;


/**
 * Created by hxh on 2017/7/14.
 */
public class NetResultBean<T> {
    public int total_count;

    public List<T> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
