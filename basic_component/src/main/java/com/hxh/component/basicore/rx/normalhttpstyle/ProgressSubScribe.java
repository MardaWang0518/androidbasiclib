package com.hxh.component.basicore.rx.normalhttpstyle;

import android.content.Context;


import com.hxh.component.basicore.util.Utils;

import java.lang.ref.WeakReference;
import java.net.ConnectException;

import rx.Subscriber;

/**
 * @Title 可以显示Loading的Subscribe,《RestFul风格的http请求中，不建议使用这个》
 * 正常请求中，由于无法知道你的响应体格式，所以无法做到统一处理error
 * @param <T>
 */
@Deprecated
public abstract class ProgressSubScribe<T> extends Subscriber<T> implements ProgressCancelListener{
    private ProgressDialogHandler mDialogHandler;
    private WeakReference<Context> mContext;
    private String msg;
    public ProgressSubScribe(Context context)
    {
        this.mContext = new WeakReference<>(context);
        mDialogHandler = new ProgressDialogHandler(mContext.get(),this);
    }

    public ProgressSubScribe(String msg,Context context)
    {
        this.msg = msg;
        this.mContext = new WeakReference<>(context);
        mDialogHandler = new ProgressDialogHandler(msg,mContext.get(),this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //检查网络
        if(Utils.NetWork.isConnected())
        {
            showDialog();
        }else
        {
            //没有网络，直接结束
            onError(new ConnectException());
        }

    }

    @Override
    public void onError(Throwable e) {
        dissmisDialog();
        ExceptionHandle.ResponeThrowable errmsg = ExceptionHandle.handleException(e);
        _OnError(errmsg.getMessage());

    }

    @Override
    public void onCompleted() {
        dissmisDialog();
    }

    @Override
    public void onNext(T t) {
        dissmisDialog();
        _OnNet(t);

    }

    /**
     * 调用时机是当Loading结束，如：  报错;执行完毕
     */
    @Override
    public void onCancel() {
        if(!this.isUnsubscribed())
        {
            this.unsubscribe();
        }
    }

    public abstract void _OnError(String msg);
    public abstract void _OnNet(T t);

    //关闭Loading
    private void dissmisDialog()
    {
        if(null != mDialogHandler)
        {
            mDialogHandler.obtainMessage(ProgressDialogHandler.HIDE_DIALOG).sendToTarget();
        }
    }
    //打开Loading
    private void showDialog()
    {
        if(null != mDialogHandler)
        {
            mDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_DIALOG).sendToTarget();
        }
    }
}
