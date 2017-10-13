package com.hxh.component.basicore.rx.resetfulhttpstyle;

import android.content.Context;

import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.ui.loading.LoadingDialog;
import com.hxh.component.basicore.util.AppManager;
import com.hxh.component.basicore.util.Log;
import com.hxh.component.basicore.util.Utils;

import java.lang.ref.WeakReference;
import java.net.ConnectException;

import rx.Subscriber;


/**
 * 创建者：hxh
 * 时间：  2017/8/4
 * 描述： 符合RestFul风格Http请求的Subscribe
 *
 */
public abstract class RESTFULProgressSubscribe<T> extends Subscriber<T>  {


    private LoadingDialog mDialog;
    private WeakReference<Context> mContext;

    private boolean isself;
    private boolean isNoConnection;
    public RESTFULProgressSubscribe()
    {
        this.mContext = new WeakReference<Context>(AppManager.getCurrentActivity());
        //mDialogHandler = new ProgressDialogHandler(mContext.get(),this);
        mDialog = new LoadingDialog(mContext.get(),"");

    }

    public RESTFULProgressSubscribe(String msg)
    {

        this.mContext = new WeakReference<Context>(AppManager.getCurrentActivity());
        // mDialogHandler = new ProgressDialogHandler(msg,mContext.get(),this);
        mDialog = new LoadingDialog(mContext.get(),msg);
    }

    public RESTFULProgressSubscribe(boolean IsExceptionSelfCommand)
    {
        this.isself = IsExceptionSelfCommand;
        this.mContext = new WeakReference<Context>(AppManager.getCurrentActivity());
        //mDialogHandler = new ProgressDialogHandler(msg,mContext.get(),this);
        mDialog = new LoadingDialog(mContext.get(),"");
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
            isNoConnection = true;
            onError(new ConnectException("当前没有网络"));
        }

    }
    //
    @Override
    public void onError(Throwable e) {
        dissmisDialog();
        if(isself&&!isNoConnection)
        {
            _OnError(e);
            isself=false;
        }else
        {
            CoreLib.getInstance().getNetProvider().getApiErrorClasszz().handleApiError(e);
        }
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
    public void onCancel() {
        if(!this.isUnsubscribed())
        {
            this.unsubscribe();
        }
    }

    public abstract void _OnError(Throwable msg);
    public abstract void _OnNet(T t);

    //关闭Loading
    private void dissmisDialog()
    {

        mDialog.close();
        onCancel();
        if(null != mContext)
        {
            mContext.clear();
        }

        mContext = null;
    }
    //打开Loading
    private void showDialog()
    {

        mDialog.show();
    }
}
