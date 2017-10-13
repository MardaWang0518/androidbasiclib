package com.hxh.component.basicore.recycle;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by hxh on 2017/6/2.
 */
public class EnabledScollLinearManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public EnabledScollLinearManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
