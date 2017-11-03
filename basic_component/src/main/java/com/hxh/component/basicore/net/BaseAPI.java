package com.hxh.component.basicore.net;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.hxh.component.basicore.Config;
import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.net.cookie.CookiesManager;
import com.hxh.component.basicore.net.factory.FastJsonConverterFactory;
import com.hxh.component.basicore.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by hxh on 2017/5/8.
 */
public class BaseAPI {
    private Converter.Factory mFactory = GsonConverterFactory.create();
    private NetProvider mNetProvider; //默认的网络配置
    /**
     * 1. 第一个Key为BaseUrl
     * 2. 第二个Key为ConvertFactoryName
     */
    private ArrayMap<String, ArrayMap<String,NetProvider>> mProviders = new ArrayMap<>();  //存储着网络配置集合
    private ArrayMap<String, ArrayMap<String,Retrofit>> mRetrofits = new ArrayMap<>(); //存储着Retrofit集合
    private ArrayMap<String, ArrayMap<String,OkHttpClient>> mOKHttpClients = new ArrayMap<>(); //存储着OkhttpClient集合
    //region 单例
    private static class SingletonHolder {
        private static final BaseAPI INSTANCE = new BaseAPI();
    }
    private BaseAPI(){}
    public static final BaseAPI getInstance() {
        return SingletonHolder.INSTANCE;
    }
    //endregion

    private String CONVERT_GSON ="gson";
    private String CONVERT_JACKSON="jackson";
    private String CONVERT_SIMPLESTRING = "string";
    private String CONVERT_FASTJSON = "fastjson";
    /**
     * 设置一个转换器
     * @param factorytype 给一个转换器类别，可通过COnvertFactory获得
     * @return
     */
    public BaseAPI setConvertFactory(int factorytype)
    {
        if(factorytype == ConverterFactory.GSON)
        {
            mFactory = ConverterFactory.getConverter(ConverterFactory.GSON);
        }else if(factorytype == ConverterFactory.JACKSON)
        {
            mFactory = ConverterFactory.getConverter(ConverterFactory.JACKSON);
        }else if(factorytype == ConverterFactory.SIMPLEString)
        {
            mFactory = ConverterFactory.getConverter(ConverterFactory.SIMPLEString);
        }else if(factorytype == ConverterFactory.FASTJSON)
        {
            mFactory = ConverterFactory.getConverter(ConverterFactory.FASTJSON);
        }
     //   getCurrentNetProvider().setConfigConverterFactory(mFactory);
        return this;
    }


    //在你的application中注册
    public void register(NetProvider provider)
    {
        this.mNetProvider = provider;
    }

    //在你的application中注册
    public void register(String baseurl,NetProvider provider)
    {

        ArrayMap<String,NetProvider> arrayMap_provider = new ArrayMap<>();
        arrayMap_provider.put(getConvertFactoryName(provider.getConfigConverterFactory()),provider);

        getInstance().mProviders.put(baseurl,arrayMap_provider );
    }

    public void unRegister()
    {
        mNetProvider = null;
        if(null != mProviders)
        {
            mProviders.clear();
            mProviders = null;
        }

        if(null != mRetrofits)
        {
            mRetrofits.clear();
            mRetrofits= null;
        }

        if(null != mOKHttpClients)
        {
            mOKHttpClients.clear();
            mOKHttpClients = null;
        }
    }

    /**
     * 创建一个Service
     * @param clazz  Service的类
     * @param <S>
     * @return
     */
    public <S>S createServices(Class<S> clazz)
    {
        if(null != clazz)
        {
            return getInstance().getRetrofit(CoreLib.getInstance().getBaseUrl()).create(clazz);
        }
        return null;
    }

    /**
     * 创建一个Service，根据BaseUrl
     * @param baseurl
     * @param clazz
     * @param <S>
     * @return
     */
    public <S>S createServices(String baseurl,Class<S> clazz)
    {
        if(null != clazz)
        {
            return getRetrofit(baseurl).create(clazz);
        }
        return null;
    }


    /**
     * 创建一个Service,根据NetProvider去创建
     * @param clazz
     * @param <S>
     * @return
     */
    public <S>S createServices(NetProvider provider, Class<S> clazz)
    {
        if(null != clazz)
        {

            return getInstance().getRetrofit(CoreLib.getInstance().getBaseUrl(),provider).create(clazz);
        }
        return null;
    }

    /**
     * 得到一个Retrofit
     * @param baseUrl
     * @return
     */
    private Retrofit getRetrofit(String baseUrl)
    {
        return getRetrofit(baseUrl,null);
    }

    /**
     * 得到一个Retrofit
     * @param baseUrl
     * @param provider
     * @return
     */
    private Retrofit getRetrofit(String baseUrl,NetProvider provider)
    {
        //如果没有传入baseUrl
        if("".equals(baseUrl) || null == baseUrl)
        {
            throw new IllegalStateException("baseurl can not is null");
        }
        if(null == provider && null == mNetProvider)
        {
            throw new IllegalStateException("first,you must call BaseAPI.register is to the regist Or Call BaseApi.defaultNetProvider,But you must go to override some method!!");
        }
        //得到转换器名称
        String convertFactoryname = getConvertFactoryName(mFactory);

        if(mProviders.size() == 0 && mRetrofits.size() == 0 && mOKHttpClients.size() == 0)
        {
            provider = this.mNetProvider;
        }else
        {
            //如果没有传入 网络配置，那么看看是否有保存住的，如果没有就用默认的，默认的为空就抛错
            if(null == provider)
            {
                ArrayMap<String,NetProvider> providerArrayMap = mProviders.get(baseUrl);
                if(null != providerArrayMap)
                {
                    provider = providerArrayMap.get(convertFactoryname);
                }

                if(null == provider)
                {
                    provider = this.mNetProvider;
                }
            }
            //如果用户设置了ConvertFactory
            if(mFactory instanceof GsonConverterFactory)
            {
            }else
            {
                provider.setConfigConverterFactory(mFactory);
            }

            ArrayMap<String,Retrofit> retrofits = mRetrofits.get(baseUrl);
            if(retrofits != null)
            {
                Retrofit retrofit = retrofits.get(convertFactoryname);
                if(null != retrofit)
                {

                    String old = getConvertFactoryName(retrofit.converterFactories().get(1));
                    String news = getConvertFactoryName(provider.getConfigConverterFactory());
                    boolean isEquest = old.equals(news);

                    if(isEquest)
                    {
                        //还原
                        mFactory = ConverterFactory.defaultConvertFactory();
                        mNetProvider.setConfigConverterFactory(mFactory);

                        return retrofit;
                    }
                }
            }
        }

        return createRetrofit(baseUrl,convertFactoryname,provider);
    }

    private Retrofit createRetrofit(String baseUrl,String convertFactoryname,NetProvider provider)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient(baseUrl,provider))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(provider.getConfigConverterFactory()!=null?provider.getConfigConverterFactory(): GsonConverterFactory.create())
                .build();

        ArrayMap<String,Retrofit> arrayMap_retrofit = new ArrayMap<>();
        arrayMap_retrofit.put(convertFactoryname,retrofit);

        ArrayMap<String,NetProvider> arrayMap_provider = new ArrayMap<>();
        arrayMap_provider.put(convertFactoryname,provider);

        mRetrofits.put(baseUrl,arrayMap_retrofit);
        mProviders.put(baseUrl,arrayMap_provider);
        //能进入这个方法，基本可以判定是新的请求
       if(mFactory instanceof GsonConverterFactory)
       {

       }else
       {
           mFactory = ConverterFactory.defaultConvertFactory();
           mNetProvider.setConfigConverterFactory(mFactory);
       }
        return retrofit;
    }

    private String getConvertFactoryName(Converter.Factory factory)
    {
        if(null == factory)
        {
            return CONVERT_GSON;
        }
        if(factory instanceof ScalarsConverterFactory)
        {
            return CONVERT_SIMPLESTRING;
        }else if(factory instanceof JacksonConverterFactory)
        {
            return CONVERT_JACKSON;
        }else if(factory instanceof FastJsonConverterFactory)
        {
            return CONVERT_FASTJSON;
        }
        else
        {
            return CONVERT_GSON;
        }
    }

    /**
     * 得到一个OKhttpClient
     * @param baseUrl
     * @param provider
     * @return
     */
    private OkHttpClient getOkHttpClient(String baseUrl,NetProvider provider)
    {
        String convertName = getConvertFactoryName(provider.getConfigConverterFactory());

        ArrayMap<String,OkHttpClient> clientArrayMap = mOKHttpClients.get(baseUrl);
        if(clientArrayMap!=null)
        {
            OkHttpClient client =  clientArrayMap.get(getConvertFactoryName(provider.getConfigConverterFactory()));
            if(null != client)
            {
               return client;
            }
        }
        //okhttpclient基本配置
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(provider.getConfigConnectTimeOut(), TimeUnit.SECONDS)
                .readTimeout(provider.getConfigReadTimeOut(),  TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                ;

        //看看是否配置有cookie
        if(provider.isEnableCookie())builder.cookieJar(new CookiesManager());
        //是否配置有Token拦截器
        if(null != provider.getConfigTokenInterceptor()) builder.addInterceptor(provider.getConfigTokenInterceptor());

        if(null != provider.getConfigRESTFULTokenInterceptor())builder.authenticator(provider.getConfigRESTFULTokenInterceptor());

        //是否启用Log
        if(CoreLib.getInstance().isLogEnable())
        {
            String logtag = CoreLib.getInstance().getLogTag();
            if(null != provider.getConfigLogTnterceptor())
            {
                builder.addInterceptor(provider.getConfigLogTnterceptor());
            }else
            {
                //是否存在Log的Tag
                if("".equals(logtag) || null == logtag)  builder.addInterceptor(HttpInterceptor.buildLogInterceptor());
                else  HttpInterceptor.buildLogInterceptor();
            }

        }
        //是否配置有 请求拦截器
        if(null != provider.getConfigRequestCallBack()) builder.addInterceptor(new RequestInterceptor(provider.getConfigRequestCallBack()));

        if(provider.getCacheSize()>0)
        {
            if(!Utils.Text.isEmpty(provider.getCachePath()))
            {
                builder.cache(new Cache(new File(provider.getCachePath()),provider.getCacheSize()));
            }else
            {
                File cacheFIle = new File(Utils.getApplicationContext().getCacheDir(),"httpResponse");
                mHttpCacheDir = cacheFIle.getAbsolutePath();
                builder.cache(new Cache(cacheFIle,provider.getCacheSize()));
            }

        }

        OkHttpClient client = builder.build();

        ArrayMap<String,OkHttpClient> arrayMap_okhttpclient = new ArrayMap<>();
        arrayMap_okhttpclient.put(convertName,client);

        ArrayMap<String,NetProvider> arrayMap_provider = new ArrayMap<>();
        arrayMap_provider.put(convertName,provider);

        mOKHttpClients.put(baseUrl,arrayMap_okhttpclient);
        mProviders.put(baseUrl,arrayMap_provider);

        return client;
    }

    /**
     * 得到当前的NetProvider
     * @return
     */
    public NetProvider getCurrentNetProvider() {
        return mNetProvider;
    }

    private String mHttpCacheDir;

    public String getmHttpCacheDir() {
        return mHttpCacheDir;
    }

}
