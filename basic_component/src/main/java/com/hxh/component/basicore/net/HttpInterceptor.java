package com.hxh.component.basicore.net;

import com.hxh.component.basicore.util.Log;
import com.hxh.component.basicore.util.Utils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hxh on 2017/7/8.
 */
public class HttpInterceptor {
    private static String TAG = "corelib";


    public static Interceptor buildTokenInterceptor(final String tokenFieldName,final TokenCallBack callback)
    {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!Utils.Text.isEmpty(callback.getToken())) {
                    Log.d("植入token--->" + callback.getToken());
                    Request authorised = request.newBuilder()
                            .removeHeader(tokenFieldName)
                            .header(tokenFieldName,callback.getToken())
                            .build();
                    return chain.proceed(authorised);
                }
                return chain.proceed(request);
            }
        };
    }

    public static Interceptor buildDefaultLogInterceptor()
    {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long startTime = System.currentTimeMillis();
                okhttp3.Response response = chain.proceed(chain.request());
                long endTime = System.currentTimeMillis();
                long duration=endTime-startTime;
                okhttp3.MediaType mediaType = response.body().contentType();
                String content = response.body().string();
                Log.d("/n");
                Log.d("----------Start----------------");
                Log.d("| "+request.toString());
                String method=request.method();

                if("POST".equals(method)){
                    StringBuilder sb = new StringBuilder();
                    if (request.body() instanceof FormBody) {
                        FormBody body = (FormBody) request.body();
                        for (int i = 0; i < body.size(); i++) {
                            sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                        }
                        sb.delete(sb.length() - 1, sb.length());
                        Log.d( "| RequestParams:{"+sb.toString()+"}");
                    }
                }
                Log.d( "| Response:" + content);
                Log.d("----------End:"+duration+"毫秒----------");
                return response.newBuilder()
                        .body(okhttp3.ResponseBody.create(mediaType, content))
                        .build();
            }
        };
    }

    public static Interceptor buildLogInterceptor()
    {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long startTime = System.currentTimeMillis();
                okhttp3.Response response = chain.proceed(chain.request());
                long endTime = System.currentTimeMillis();
                long duration=endTime-startTime;
                okhttp3.MediaType mediaType = response.body().contentType();
                String content = response.body().string();
                 Log.d("/n");
                 Log.d("----------Start----------------");
                 Log.d( "| "+request.toString());
                String method=request.method();

                if("POST".equals(method)){
                    StringBuilder sb = new StringBuilder();

                    if (request.body() instanceof FormBody) {
                        FormBody body = (FormBody) request.body();
                        for (int i = 0; i < body.size(); i++) {
                            sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                        }
                        sb.delete(sb.length() - 1, sb.length());
                         Log.d( "| RequestParams:{"+sb.toString()+"}");
                    }
                }

                 Log.d( "| Response:" + content);
                 Log.d("----------End:"+duration+"毫秒----------");
                return response.newBuilder()
                        .body(okhttp3.ResponseBody.create(mediaType, content))
                        .build();
            }
        };
    }

    public interface TokenCallBack
    {
        String getToken();
    }





}
