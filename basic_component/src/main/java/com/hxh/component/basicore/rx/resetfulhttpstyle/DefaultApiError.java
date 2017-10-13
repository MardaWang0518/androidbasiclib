package com.hxh.component.basicore.rx.resetfulhttpstyle;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hxh.component.basicore.net.IApiError;
import com.hxh.component.basicore.util.Utils;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 错误的实体类
 */
public class DefaultApiError implements IApiError{

    //region da
    private static volatile DefaultApiError singleton = null;

    private DefaultApiError() {
    }

    public static DefaultApiError getInstance() {
        if (singleton == null) {
            synchronized (DefaultApiError.class) {
                if (singleton == null) {
                    singleton = new DefaultApiError();
                }
            }
        }
        return singleton;
    }
    //endregion

    /**
     * code : 400
     * errors : {"name":["此项是必填项",""]}
     * message : 请求输入存在不合法的参数
     * title : INVALID_INPUT_PARAMS
     * x_request_id : e0e5a699-f725-461f-a655-746c39099fa4
     */

    public int code;
    public Map<String, List<String>> errors;  // 参数校验 ,详细出错信息
    public String message = "";  // 总体错误信息
    public String title;
    @SerializedName("x_request_id")
    public String xRequestId;

    /**
     * 处理异常
     * @param throwable
     */
    @Override
    public void handleApiError(Throwable throwable) {
        // api error
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Gson gson = new Gson();
            try {
                DefaultApiError error = gson.fromJson(httpException.response().errorBody().string(), DefaultApiError.class);
                if (error != null) {
                    Utils.Toast.toast(error.message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (throwable instanceof ConnectException) {
            // network error
            if (!Utils.NetWork.isConnected()) {
                Utils.Toast.toast("当前无网络哦！请检查您的网络");
            } else {

            }
        }
    }


    /**
     * 将Throwable 转换为一个HttpException ，但是前提是这个Throable可以被转换成功
     * @param throwable 异常类
     * @return
     */
    @Override
    public DefaultApiError getApiError(Throwable throwable)
    {
        try
        {
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Gson gson = new Gson();
                return gson.fromJson(httpException.response().errorBody().string(), DefaultApiError.class);
            }
        }catch (Exception e)
        {

        }
        return null;
    }

    @Override
    public boolean checResponseBodyContainErrorBody(Response body)
    {

        if(null != body.errorBody())
        {
            return false;
        }

        return true;
    }

    @Override
    public int getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

    @Override
    public String getErrorTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "code=" + code +
                ", errors=" + errors +
                ", message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", xRequestId='" + xRequestId + '\'' +
                '}';
    }
}
