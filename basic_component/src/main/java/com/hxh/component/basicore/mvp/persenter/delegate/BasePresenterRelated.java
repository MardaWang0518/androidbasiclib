package com.hxh.component.basicore.mvp.persenter.delegate;

import android.widget.EditText;
import android.widget.TextView;

import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.net.IApiError;
import com.hxh.component.basicore.util.Utils;

import java.util.List;

import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hxh on 2017/8/2.
 */
public class BasePresenterRelated implements  IPresenterRelated{
    protected CompositeSubscription mSubscription;//管理subscription
    //region 管理sub
    /**
     * 当你每次调用 subscribe(observer...)时候，此方法会返回给你一个Subscribtion,你可以调用此方法收集它
     * @param sub
     */
    public void addSubscription(Subscription sub)
    {
        if(null == mSubscription)
        {
            mSubscription = new CompositeSubscription();
        }
        mSubscription.add(sub);
    }


    /**
     * 解绑（当你解绑后，请及时再次new CompositeSubscription）
     */
    public void unSubscription()
    {
        if(null != mSubscription)
        {
            if(mSubscription.hasSubscriptions())
                mSubscription.unsubscribe();
                //mSubscription.clear();
        }

    }

    /**
     * 此方法可以再次创建一个 Subscription 的管理器(CompositeSubscription)，因为当你调用过 unsubscribe 过后
     * 第二次使用这个管理器（CompositeSubscription）你必须去重新创建，否则被他收集的rx事件将不会执行
     */
    public void newCompositeSubscription()
    {
        if(null != mSubscription)
        {
            if(mSubscription.isUnsubscribed())
            {
                mSubscription = new CompositeSubscription();
            }
        }else
        {
            mSubscription = new CompositeSubscription();
        }
    }

    //endregion

    //region 辅助方法
    @Override
    public boolean isEmpty(List list) {
        return Utils.Text.isEmpty(list);
    }

    @Override
    public boolean isEmpty(String msg) {
        return Utils.Text.isEmpty(msg);
    }

    @Override
    public boolean isEmpty(CharSequence str) {
        return Utils.Text.isEmpty(str);
    }

    @Override
    public boolean isEmpty(String... args) {
        return Utils.Text.isEmpty(args);
    }

    @Override
    public boolean isEmpty(EditText text) {
        return Utils.Text.isEmpty(text);
    }

    @Override
    public boolean isEmpty(EditText text, String tipmsg) {
        return Utils.Text.isEmpty(text,tipmsg);
    }

    @Override
    public boolean isEmpty(TextView tv) {
        return Utils.Text.isEmpty(tv);
    }
    //endregion

    @Override
    public IApiError getApiError(Throwable e)
    {
        return  CoreLib.getInstance().getNetProvider().getApiErrorClasszz().getApiError(e);
    }

    @Override
    public int getErrorCode() {
        return CoreLib.getInstance().getNetProvider().getApiErrorClasszz().getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return CoreLib.getInstance().getNetProvider().getApiErrorClasszz().getErrorMessage();
    }

    @Override
    public void handleApiError(Throwable msg) {
        CoreLib.getInstance().getNetProvider().getApiErrorClasszz().handleApiError(msg);
    }

    @Override
    public boolean checResponseBodyContainErrorBody(Response body) {
        return CoreLib.getInstance().getNetProvider().getApiErrorClasszz().checResponseBodyContainErrorBody(body);
    }
}
