package com.hxh.component.basicore.Base.delegate.interfaces;

/**
 * Created by hxh on 2017/8/1.
 */
public interface ILogRelated {
    void d(String msg);
    void d(Throwable th);
    void d(Exception e);

    void e(String msg);
    void e(Throwable th);
    void e(Exception e);

    void i(String msg);
    void i(Throwable th);
    void i(Exception e);



}
