package com.hxh.component.basicore.net;/**
 * Created by hxh on 2017/8/7.
 */

import retrofit2.Response;

/**
 * 创建者：hxh
 * 时间：  2017/8/7
 * 描述： 自定义的ApiError，请注意，这个ApiError只适用于RestFul风格的，如果你是普通风格的请求，
 * 那么请忽略此文件
 */
public interface IApiError
{
    int getErrorCode();
    String getErrorMessage();
    String getErrorTitle();
    /**
     * 处理网络错误
     * @param msg
     */
    void handleApiError(Throwable msg);

    /**
     * 将一个Throwable 转换成IApierror
     * @param msg
     * @return
     */
    IApiError getApiError(Throwable msg);

    /**
     * 检查相应体是否有错误
     * 通常情况用于你的返回结果为 Response<Void> 之类的
     * @param body
     * @return
     */
    boolean checResponseBodyContainErrorBody(Response body);
}
