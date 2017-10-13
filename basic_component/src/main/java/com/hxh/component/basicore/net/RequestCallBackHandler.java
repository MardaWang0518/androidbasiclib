package com.hxh.component.basicore.net;



import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求过程中的回调事件,你可以在这里面去弹出Loading,你可以在初始化的时候去注册
 */
public interface RequestCallBackHandler {

    Request onBeforeRequest(Request request, Interceptor.Chain chain);

    Response onAfterRequest(Response response, String result, Interceptor.Chain chain);
}
