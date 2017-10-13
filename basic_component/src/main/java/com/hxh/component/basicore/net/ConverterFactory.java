package com.hxh.component.basicore.net;

import com.hxh.component.basicore.net.factory.FastJsonConverterFactory;

import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 配置你的ConvertFactory，默认是Gson
 * Gson在遇到一些情况下，不做任何配置，转换一些动态对象时，及其容易出现错误
 */
public class ConverterFactory {

    public static final int GSON = 0x0;
    public static final int SIMPLEString = 0x1;
    public static final int JACKSON = 0x2;
    public static final int FASTJSON  = 0x3;
    public static Converter.Factory getConverter(int type)
    {
        switch (type)
        {
            case GSON:
                return GsonConverterFactory.create();
            case SIMPLEString:
                return ScalarsConverterFactory.create();
            case JACKSON:
                return JacksonConverterFactory.create();
            case FASTJSON:
                return FastJsonConverterFactory.create();
        }
        return null;
    }

    /**
     * 创建一个默认的ConvertFactory
     * @return
     */
    public static GsonConverterFactory defaultConvertFactory()
    {
        return (GsonConverterFactory) getConverter(GSON);
    }
}
