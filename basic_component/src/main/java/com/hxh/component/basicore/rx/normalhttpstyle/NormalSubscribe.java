package com.hxh.component.basicore.rx.normalhttpstyle;

import com.hxh.component.basicore.util.Utils;

import java.net.ConnectException;

import rx.Subscriber;

/**
 * Created by hxh on 2017/5/5.
 */
@Deprecated
public abstract class NormalSubscribe<T> extends Subscriber<T> {
    private boolean isself;
    private boolean isNoConnection;
    public NormalSubscribe()
    {}

    public NormalSubscribe(boolean isself )
    {
        this.isself = isself;
    }

    @Override
    public void onStart() {
        super.onStart();
        //检查网络
        if(!Utils.NetWork.isConnected())
        {
            //没有网络，直接结束
            isNoConnection = true;
            onError(new ConnectException("当前网络状况不好"));
        }
    }


    @Override
    public void onError(Throwable e) {
        if(isself&&!isNoConnection)
        {
            _OnError(e);
            isself=false;
        }else
        {
            ExceptionHandle.ResponeThrowable body = ExceptionHandle.handleException(e);
            Utils.Toast.toast(body.getMessage());
            //_OnError(e);
            //ApiError.handleError(e);
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        _OnNet(t);
    }



    public abstract void _OnError(Throwable msg);
    public abstract void _OnNet(T t);


}
