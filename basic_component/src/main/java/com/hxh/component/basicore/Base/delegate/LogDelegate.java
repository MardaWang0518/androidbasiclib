package com.hxh.component.basicore.Base.delegate;

import com.hxh.component.basicore.Base.delegate.interfaces.ILogRelated;
import com.hxh.component.basicore.util.Log;

/**
 * Created by hxh on 2017/8/1.
 */
public class LogDelegate implements ILogRelated {
    @Override
    public void d(String msg) {
        Log.d(msg);
    }

    @Override
    public void d(Throwable th) {
        Log.d(th);
    }

    @Override
    public void d(Exception e) {
        Log.d(e);
    }

    @Override
    public void e(String msg) {
        Log.e(msg);
    }

    @Override
    public void e(Throwable th) {
        Log.e(th);
    }

    @Override
    public void e(Exception e) {
        Log.e(e);
    }

    @Override
    public void i(String msg) {
        Log.i(msg);
    }

    @Override
    public void i(Throwable th) {
        Log.i(th);
    }

    @Override
    public void i(Exception e) {
        Log.i(e);
    }
}
