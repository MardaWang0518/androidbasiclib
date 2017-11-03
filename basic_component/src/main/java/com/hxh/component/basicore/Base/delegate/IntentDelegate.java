package com.hxh.component.basicore.Base.delegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hxh.component.basicore.Base.delegate.interfaces.IIntentRelated;
import com.hxh.component.basicore.Config;
import com.hxh.component.basicore.util.AppManager;

import java.io.Serializable;

/**
 * Intent相关支持
 * 更新日志：
 * 1. 现在的getExtra_Parceable()   getExtra_Seriallizable() 的默认Key是 "FROM_OBJ_TAG"
 * @param <T>
 */
public class IntentDelegate<T extends Parcelable> implements IIntentRelated<T> {
    private Bundle mDefaultBundle;

    public IntentDelegate(Bundle bun) {
        this.mDefaultBundle = bun;

    }


    @Override
    public String getExtra_String(String key) {
        if(null != mDefaultBundle)
        {
            return mDefaultBundle.getString(key);
        }
        return "";
    }

    @Override
    public Integer getExtra_Int(String key, int defaultvalue) {
        if(null != mDefaultBundle)
        {
            return mDefaultBundle.getInt(key,defaultvalue);
        }
        return defaultvalue;
    }

    @Override
    public Boolean getExtra_Boolean(String key, boolean defaultvalue) {
        if(null != mDefaultBundle)
        {
            return mDefaultBundle.getBoolean(key,defaultvalue);
        }
        return defaultvalue;
    }

    @Override
    public Double getExtra_Double(String key, double defaultvalue) {
        if(null != mDefaultBundle)
        {
            return mDefaultBundle.getDouble(key,defaultvalue);
        }
        return defaultvalue;
    }

    @Override
    public Float getExtra_Float(String key, float defaultvalue) {
        if(null != mDefaultBundle)
        {
            return mDefaultBundle.getFloat(key,defaultvalue);
        }
        return defaultvalue;
    }

    @Override
    public Integer getExtra_Int(int defaultvalue) {
        return getExtra_Int(DEFAULTKEY,defaultvalue);
    }

    @Override
    public Boolean getExtra_Boolean(boolean defaultvalue) {
        return getExtra_Boolean(DEFAULTKEY,defaultvalue);
    }

    @Override
    public Double getExtra_Double(double defaultvalue) {
        return getExtra_Double(DEFAULTKEY,defaultvalue);
    }

    @Override
    public Float getExtra_Float(float defaultvalue) {
        return getExtra_Float(DEFAULTKEY,defaultvalue);
    }

    @Override
    public String getExtra_String() {
        return getExtra_String(DEFAULTKEY);
    }


    @Override
    public T getExtra_Parceable() {
        if (null != mDefaultBundle) return mDefaultBundle.getParcelable(Config.FROM_OBJ_TAG);
        else return null;
    }
    @Override
    public T getExtra_Parceable(String key) {
        if (null != mDefaultBundle) return mDefaultBundle.getParcelable(key);
        else return null;
    }
    @Override
    public Serializable getExtra_Seriallizable() {
        if (null != mDefaultBundle) return mDefaultBundle.getSerializable(Config.FROM_OBJ_TAG);
        else return null;
    }

    @Override
    public Serializable getExtra_Seriallizable(String key) {
        if (null != mDefaultBundle) return mDefaultBundle.getSerializable(key);
        else return null;
    }

    @Override
    public Parcelable getExtra_Parceable(String key, Parcelable defaultvalue) {
        if (null != mDefaultBundle)
        {
            Parcelable bean = mDefaultBundle.getParcelable(key);
            if(null != bean)
            {
                return bean;
            }else
            {
                return defaultvalue;
            }
        }
        else return null;
    }

    @Override
    public String getExtra_String(String key, String defaultvalue) {
        if (null != mDefaultBundle) return mDefaultBundle.getString(key,defaultvalue);
        else return null;
    }

    @Override
    public String getExtra_StringDefa(String defavalue) {
        if (null != mDefaultBundle) return mDefaultBundle.getString(DEFAULTKEY,defavalue);
        else return null;
    }

    @Override
    public Parcelable getExtra_Parceable(Parcelable defaultvalue) {
        if (null != mDefaultBundle)
        {
            Parcelable bean = mDefaultBundle.getParcelable(DEFAULTKEY);
            if(null != bean)
            {
                return bean;
            }else
            {
                return defaultvalue;
            }
        }
        return null;
    }

    @Override
    public Serializable getExtra_Seriallizable(Serializable defaultvalue) {
        if (null != mDefaultBundle)
        {
            Serializable bean = mDefaultBundle.getSerializable(DEFAULTKEY);
            if(null != bean)
            {
                return bean;
            }else
            {
                return defaultvalue;
            }
        }
        return null;
    }

    @Override
    public void startActivity(Class<?> classzz) {
        Intent intent = new Intent(AppManager.getCurrentActivity(),classzz);
        AppManager.getCurrentActivity().startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<?> classzz) {
        Intent intent = new Intent(AppManager.getCurrentActivity(),classzz);
        AppManager.getCurrentActivity().startActivityForResult(intent,DEFAULT_ACTIVITY_REQUESTCODE);
    }

    @Override
    public void startActivity(Class<?> classzz, Bundle data) {
        Intent intent = new Intent(AppManager.getCurrentActivity(),classzz);
        intent.putExtras(data);
        AppManager.getCurrentActivity().startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<?> classzz, Bundle data) {
        Intent intent = new Intent(AppManager.getCurrentActivity(),classzz);
        intent.putExtras(data);
        AppManager.getCurrentActivity().startActivityForResult(intent,DEFAULT_ACTIVITY_REQUESTCODE);
    }

    @Override
    public void startActivity(Class<?> classzz, String key, String value) {
        Intent intent = new Intent(AppManager.getCurrentActivity(),classzz);
        intent.putExtra(key,value);
        AppManager.getCurrentActivity().startActivity(intent);
    }
}