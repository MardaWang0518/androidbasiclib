package com.hxh.component.basicore.Base.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxh.component.basicore.Base.delegate.LogDelegate;
import com.hxh.component.basicore.Base.delegate.IntentDelegate;
import com.hxh.component.basicore.Base.delegate.ViewRelatedDelegate;
import com.hxh.component.basicore.Base.delegate.interfaces.ILogRelated;
import com.hxh.component.basicore.Base.delegate.interfaces.IIntentRelated;
import com.hxh.component.basicore.Base.delegate.interfaces.IViewRelated;

import java.io.Serializable;
import java.util.List;

/**
 *  这个类当做Fragment的基础类，它可以继承于任何框架的Fragment
 */
public class EmptyFragment extends AppCompartAutoLayoutFragment
        implements ILogRelated,IViewRelated,IIntentRelated {

    private LogDelegate mLogDelegate;
    private ViewRelatedDelegate mViewRelateDelegate;
    private IntentDelegate mParceableDelegate;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogDelegate = new LogDelegate();
        mViewRelateDelegate =  new ViewRelatedDelegate();
        mParceableDelegate = new IntentDelegate(getArguments());
    }

    //region 日志
    @Override
    public void d(String msg) {
        mLogDelegate.d(msg);
    }

    @Override
    public void d(Throwable th) {
        mLogDelegate.d(th);
    }

    @Override
    public void d(Exception e) {
        mLogDelegate.d(e);
    }

    @Override
    public void e(String msg) {
        mLogDelegate.e(msg);
    }

    @Override
    public void e(Throwable th) {
        mLogDelegate.e(th);
    }

    @Override
    public void e(Exception e) {
        mLogDelegate.e(e);
    }

    @Override
    public void i(String msg) {
        mLogDelegate.i(msg);
    }

    @Override
    public void i(Throwable th) {
        mLogDelegate.i(th);
    }

    @Override
    public void i(Exception e) {
        mLogDelegate.i(e);
    }
    //endregion

    //region ViewRelated
    @Override
    public void visibe(View view) {
        mViewRelateDelegate.visibe(view);
    }

    @Override
    public void gone(View view) {
        mViewRelateDelegate.gone(view);
    }

    @Override
    public void visibe(View... views) {
        mViewRelateDelegate.visibe(views);
    }

    @Override
    public void gone(View... views) {
        mViewRelateDelegate.gone(views);
    }

    @Override
    public void inVisibe(View... views) {
        mViewRelateDelegate.inVisibe(views);
    }

    @Override
    public void inVisibe(View view) {
        mViewRelateDelegate.inVisibe(view);
    }

    @Override
    public void toastShort(String msg) {
        mViewRelateDelegate.toastShort(msg);
    }

    @Override
    public void toastLong(String msg) {
        mViewRelateDelegate.toastLong(msg);
    }

    @Override
    public String getRES_String(int resId) {
        return mViewRelateDelegate.getRES_String(resId);
    }

    @Override
    public int getRES_Color(int resId) {
        return mViewRelateDelegate.getRES_Color(resId);
    }

    @Override
    public Drawable getRES_drawable(int resId) {
        return mViewRelateDelegate.getRES_drawable(resId);
    }

    @Override
    public boolean getRES_boolean(int resId) {
        return mViewRelateDelegate.getRES_boolean(resId);
    }

    @Override
    public float getRES_dimen(int resId) {
        return mViewRelateDelegate.getRES_dimen(resId);
    }

    //region TextView的操作
    @Override
    public String getText(EditText et) {
        return mViewRelateDelegate.getText(et);
    }

    @Override
    public String getText(TextView tv) {
        return mViewRelateDelegate.getText(tv);
    }

    @Override
    public String getText(EditText et, String defaulttext) {
        return mViewRelateDelegate.getText(et,defaulttext);
    }

    @Override
    public String getText(TextView tv, String defaulttext) {
        return mViewRelateDelegate.getText(tv,defaulttext);
    }

    @Override
    public String getText(TextView tv, int defaulttextResId) {
        return mViewRelateDelegate.getText(tv,defaulttextResId);
    }

    @Override
    public String getText(EditText et, int defaulttextResId) {
        return mViewRelateDelegate.getText(et,defaulttextResId);
    }

    @Override
    public String getHint(EditText et) {
        return mViewRelateDelegate.getHint(et);
    }

    @Override
    public String getHint(EditText et, String defaulttext) {
        return mViewRelateDelegate.getHint(et,defaulttext);
    }

    @Override
    public String getHint(EditText et, int defaulttextResId) {
        return mViewRelateDelegate.getHint(et,defaulttextResId);
    }

    @Override
    public <V extends TextView> void setText(V tv, String msg) {
        mViewRelateDelegate.setText(tv,msg);
    }

    @Override
    public <V extends TextView> void setText(V tv, int msgResId) {
        mViewRelateDelegate.setText(tv,msgResId);
    }
    //endregion

    @Override
    public boolean isEmpty(List list) {
        return mViewRelateDelegate.isEmpty(list);
    }

    @Override
    public boolean isEmpty(String msg) {
        return mViewRelateDelegate.isEmpty(msg);
    }

    @Override
    public boolean isEmpty(CharSequence str) {
        return mViewRelateDelegate.isEmpty(str);
    }

    @Override
    public boolean isEmpty(String... args) {
        return mViewRelateDelegate.isEmpty(args);
    }

    @Override
    public boolean isEmpty(EditText text) {
        return mViewRelateDelegate.isEmpty(text);
    }

    @Override
    public boolean isEmpty(EditText text, String tipmsg) {
        return mViewRelateDelegate.isEmpty(text,tipmsg);
    }

    @Override
    public boolean isEmpty(TextView tv) {
        return mViewRelateDelegate.isEmpty(tv);
    }

    @Override
    public boolean isEmpty(Object obj) {
        return mViewRelateDelegate.isEmpty(obj);
    }

    @Override
    public void loadimg(ImageView iv, String url) {
        mViewRelateDelegate.loadimg(iv,url);
    }

    @Override
    public void loadimg(ImageView iv, int resid) {
        mViewRelateDelegate.loadimg(iv,resid);
    }
    //endregion

    //region parceable

    @Override
    public Parcelable getExtra_Parceable(String key) {
        return mParceableDelegate.getExtra_Parceable(key);
    }

    @Override
    public String getExtra_String(String key) {
        return mParceableDelegate.getExtra_String(key);
    }

    @Override
    public Integer getExtra_Int(String key, int defaultvalue) {
        return mParceableDelegate.getExtra_Int(key,defaultvalue);
    }

    @Override
    public Boolean getExtra_Boolean(String key, boolean defaultvalue) {
        return mParceableDelegate.getExtra_Boolean(key,defaultvalue);
    }

    @Override
    public Double getExtra_Double(String key, double defaultvalue) {
        return mParceableDelegate.getExtra_Double(key,defaultvalue);
    }

    @Override
    public Float getExtra_Float(String key, float defaultvalue) {
        return mParceableDelegate.getExtra_Float(key,defaultvalue);
    }

    @Override
    public Serializable getExtra_Seriallizable(String key) {
        return mParceableDelegate.getExtra_Seriallizable(key);
    }

    @Override
    public Parcelable getExtra_Parceable() {
        return mParceableDelegate.getExtra_Parceable();
    }

    @Override
    public Serializable getExtra_Seriallizable() {
        return mParceableDelegate.getExtra_Seriallizable();
    }

    @Override
    public Integer getExtra_Int(int defaultvalue) {
        return mParceableDelegate.getExtra_Int(defaultvalue);
    }

    @Override
    public Boolean getExtra_Boolean(boolean defaultvalue) {
        return mParceableDelegate.getExtra_Boolean(defaultvalue);
    }

    @Override
    public Double getExtra_Double(double defaultvalue) {
        return mParceableDelegate.getExtra_Double(defaultvalue);
    }

    @Override
    public Float getExtra_Float(float defaultvalue) {
        return mParceableDelegate.getExtra_Float(defaultvalue);
    }

    @Override
    public Parcelable getExtra_Parceable(String key, Parcelable defaultvalue) {
        return mParceableDelegate.getExtra_Parceable(key,defaultvalue);
    }

    @Override
    public String getExtra_String(String key, String defaultvalue) {
        return mParceableDelegate.getExtra_String(key,defaultvalue);
    }

    @Override
    public String getExtra_StringDefa(String defavalue) {
        return mParceableDelegate.getExtra_StringDefa(defavalue);
    }

    @Override
    public Parcelable getExtra_Parceable(Parcelable defaultvalue) {
        return mParceableDelegate.getExtra_Parceable(defaultvalue);
    }

    @Override
    public Serializable getExtra_Seriallizable(Serializable defaultvalue) {
        return mParceableDelegate.getExtra_Seriallizable(defaultvalue);
    }

    @Override
    public String getExtra_String() {
        return mParceableDelegate.getExtra_String();
    }


    @Override
    public void startActivity(Class classzz) {
        mParceableDelegate.startActivity(classzz);
    }

    @Override
    public void startActivity(Class classzz, String key, String value) {
        mParceableDelegate.startActivity(classzz,key,value);
    }

    @Override
    public void startActivityForResult(Class classzz) {
        mParceableDelegate.startActivityForResult(classzz);
    }

    @Override
    public void startActivity(Class classzz, Bundle data) {
        mParceableDelegate.startActivity(classzz,data);
    }

    @Override
    public void startActivityForResult(Class classzz, Bundle data) {
        mParceableDelegate.startActivityForResult(classzz,data);
    }
    //endregion
}