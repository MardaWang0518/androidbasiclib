package com.hxh.component.basicore.mvp.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hxh.component.basicore.Base.delegate.ActivityDelegate;
import com.hxh.component.basicore.Base.view.EmptyActivity;
import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.mvp.persenter.IPresenter;
import com.hxh.component.basicore.util.Utils;

public abstract class BaseActivity<P extends IPresenter> extends EmptyActivity
        implements IView<P> {

    private ActivityDelegate<P> mDelegate = new ActivityDelegate<>(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mDelegate.onCreate(savedInstanceState);

    }


    @Override
    public void bindUI(View rootview) {
        mDelegate.bindUI(rootview);
    }

    public P getP()
    {
        return mDelegate.getP();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDelegate.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroy();
    }


    public <V extends View>V findViewBy(int resId)
    {
        return mDelegate.findViewBy(resId);
    }


}
