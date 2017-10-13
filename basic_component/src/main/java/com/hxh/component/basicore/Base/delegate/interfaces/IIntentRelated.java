package com.hxh.component.basicore.Base.delegate.interfaces;

import android.os.Bundle;
import android.os.Parcelable;

import com.hxh.component.basicore.Base.delegate.MyBundle;
import com.hxh.component.basicore.Config;

import java.io.Serializable;

/**
 * Created by hxh on 2017/7/31.
 */
public interface IIntentRelated<T> {

    String FROM_OBJ_TAG = Config.FROM_OBJ_TAG;
    String DEFAULTKEY =Config.FROM_DEFAULTKEY;
    int DEFAULT_ACTIVITY_REQUESTCODE = 0x1;


    Parcelable getExtra_Parceable(String key);
    Parcelable getExtra_Parceable(String key, Parcelable defaultvalue);

    String getExtra_String(String key);
    String getExtra_String(String key,String defaultvalue);
    Integer getExtra_Int(String key,int defaultvalue);
    Boolean getExtra_Boolean(String key,boolean defaultvalue);
    Double getExtra_Double(String key,double defaultvalue);

    /**
     * 使用具体的Key值
     * @param key
     * @param defaultvalue
     * @return
     */
    Float getExtra_Float(String key,float defaultvalue);

    /**
     * 使用@Link DEFAULTKEY 当做Key值，去取得 【Integer】 数据
     * @return
     */
    Integer getExtra_Int(int defaultvalue);
    /**
     * 使用@Link DEFAULTKEY 当做Key值，去取得 【Boolean】 数据
     * @return
     */
    Boolean getExtra_Boolean(boolean defaultvalue);
    /**
     * 使用@Link DEFAULTKEY 当做Key值，去取得 【Double】 数据
     * @return
     */
    Double getExtra_Double(double defaultvalue);
    /**
     * 使用@Link DEFAULTKEY 当做Key值，去取得 【Float】 数据
     * @return
     */
    Float getExtra_Float(float defaultvalue);
    /**
     * 使用@Link DEFAULTKEY 当做Key值，去取得 【String】 数据
     * @return
     */
    String getExtra_String();
    String getExtra_StringDefa(String defavalue);
    /**
     * 根据Key得到一个Seriallizable类型的数据
     * @param key
     * @return
     */
    Serializable getExtra_Seriallizable(String key);

    /**
     * 使用@Link DEFAULTKEY 当做Key值，去取得【Parceable】数据
     * @return
     */
    Parcelable getExtra_Parceable();
    Parcelable getExtra_Parceable(Parcelable defaultvalue);
    /**
     *  使用@Link DEFAULTKEY 当做Key值，去取得【Seriallizable】数据
     * @return
     */
    Serializable getExtra_Seriallizable();
    Serializable getExtra_Seriallizable(Serializable defaultvalue);

    /**
     * 启动一个Activity
     * @param classzz Activity的class
     */
    void startActivity(Class<?> classzz);

    void startActivity(Class<?> classzz,String key,String value);

    /**
     * 启动一个Activity，并且返回结果,使用 DEFAULT_ACTIVITY_REQUESTCODE 当做RequestCode
     * @param classzz Activity的class
     */
    void startActivityForResult(Class<?> classzz);

    /**
     * 启动一个Actiivyt  并且携带数据
     * @param classzz   Activity的class 使用 DEFAULT_ACTIVITY_REQUESTCODE 当做RequestCode
     * @param data   要携带的数据,使用 DEFAULTKEY 当做Key值，取得时候请注意！
     */
    void startActivity(Class<?> classzz,Bundle data);
    void startActivityForResult(Class<?> classzz,Bundle data);
}