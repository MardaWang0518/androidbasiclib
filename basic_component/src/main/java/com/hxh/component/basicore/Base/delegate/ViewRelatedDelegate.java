package com.hxh.component.basicore.Base.delegate;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxh.component.basicore.Base.delegate.interfaces.IViewRelated;
import com.hxh.component.basicore.imageLoader.IImageFactory;
import com.hxh.component.basicore.imageLoader.IImageLoader;
import com.hxh.component.basicore.util.Utils;

import java.io.File;
import java.util.List;

import com.hxh.component.basicannotation.annotation.Safe;

/**
 * Created by hxh on 2017/7/28.
 */
public class ViewRelatedDelegate implements IViewRelated
{
    @Safe
    @Override
    public void visibe( View view) {
        view.setVisibility(View.VISIBLE);
    }

    @Safe
    @Override
    public void gone( View view) {
        if(!isEmpty(view)) view.setVisibility(View.GONE);

    }

    @Safe
    @Override
    public void inVisibe( View view) {
        if(!isEmpty(view))   view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void visibe(View... views) {
        if(!isEmpty(views))
        {
            for (View view : views) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void gone(View... views) {
        if(!isEmpty(views))
        {
            for (View view : views) {
                view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void inVisibe(View... views) {
        if(!isEmpty(views))
        {
            for (View view : views) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void toastShort(String msg) {
        Utils.Toast.toast(msg);
    }


    @Override
    public void toastLong(String msg) {
        Utils.Toast.toast_long(msg);
    }

    @Override
    public String getRES_String(int resId) {
        return Utils.Resource.getString(resId);
    }

    @Override
    public int getRES_Color(int resId) {
        return Utils.Resource.getColor(resId);
    }


    @Override
    public Drawable getRES_drawable(int resId) {
        return Utils.Resource.getDrawable(resId);
    }

    @Override
    public boolean getRES_boolean(int resId) {
        return Utils.Resource.getBoolean(resId);
    }

    @Override
    public float getRES_dimen(int resId) {
        return Utils.Resource.getDimen(resId);
    }

    @Safe
    @Override
    public String getText(EditText et) {

        return et.getText().toString();
    }

    @Safe
    @Override
    public String getText(TextView tv) {
        return tv.getText().toString();
    }

    @Safe
    @Override
    public String getText(EditText et, String defaulttext) {
        return isEmpty(et.getText())?defaulttext:et.getText().toString();
    }

    @Safe
    @Override
    public String getText(TextView tv, String defaulttext) {
        return isEmpty(tv.getText())?defaulttext:tv.getText().toString();
    }

    @Safe
    @Override
    public String getText(TextView tv, int defaulttextResId) {
        return isEmpty(tv.getText())?getRES_String(defaulttextResId):tv.getText().toString();
    }

    @Safe
    @Override
    public String getText(EditText et, int defaulttextResId) {
        return isEmpty(et.getText())?getRES_String(defaulttextResId):et.getText().toString();
    }

    @Safe
    @Override
    public String getHint(EditText et) {
        return et.getHint().toString();
    }

    @Safe
    @Override
    public String getHint(EditText et, String defaulttext) {
        return isEmpty(et.getHint())?defaulttext:et.getHint().toString();
    }

    @Safe
    @Override
    public String getHint(EditText et, int defaulttextResId) {
        return isEmpty(et.getHint())?getRES_String(defaulttextResId):et.getHint().toString();
    }

    @Override
    public <V extends TextView> void setText(V tv, String msg) {
        tv.setText(msg);
    }

    @Override
    public <V extends TextView> void setText(V tv, int msgResId) {
        tv.setText(msgResId);
    }

    @Safe
    @Override
    public void loadimg(ImageView iv, String url) {
        if(url.contains("file://") || url.contains("sdcard"))
        {
            IImageFactory.getLoader().loadFile(iv,new File(url),IImageLoader.Options.defaultOptions());
        }else
        {
            IImageFactory.getLoader().loadFormNet(iv,url,IImageLoader.Options.defaultOptions());
        }

    }

    @Safe
    @Override
    public void loadimg(ImageView iv, int resid) {
        IImageFactory.getLoader().loadResource(iv,resid,IImageLoader.Options.defaultOptions());
    }

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

    @Override
    public boolean isEmpty(Object obj) {
        return null==obj;
    }

    private boolean isEmpty(View view)
    {
        if(null != view)return false;
        else return true;
    }

    private boolean isEmpty(View... view)
    {
        if(null != view)return false;
        else return true;
    }
}
