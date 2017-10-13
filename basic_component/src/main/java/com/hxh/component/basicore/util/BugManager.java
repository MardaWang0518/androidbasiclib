package com.hxh.component.basicore.util;

/**
 * Created by hxh on 2017/7/26.
 */
public interface BugManager {
    void postBug(Exception e);
    void postBug(Throwable e);
    void init();
}
