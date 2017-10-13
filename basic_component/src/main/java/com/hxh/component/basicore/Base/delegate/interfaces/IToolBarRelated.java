package com.hxh.component.basicore.Base.delegate.interfaces;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxh.component.basicore.Base.topbar.ActionBarConfig;

/**
 * ToolBar相关功能
 */
public interface IToolBarRelated {

    ActionBarConfig getActionBarConfig();


    /**
     * 得到toolbar 的顶级view
     *
     * @return
     */
    View getToolBarRootView();

    /**
     * 设置ActionbarTitle
     *
     * @param title
     */
    void setActionBar_Title(String title);

    void setActionbar_rightText(String text);

    void setActionbar_rightImg(int resId);

    void setActionbar_rightImg(Drawable drawable);

    void setActionbar_title(TextView tv_title);

    void setBackViewTitle(String title);


    void setActionBarConfig(ActionBarConfig config);
    /**
     * 得到
     *
     * @return
     */
    LinearLayout getActionbar_rightImageViewButtons();

    ImageView getActionbar_rightview_img();

    View getActionbar_rightview();

    TextView getActionbar_title();

}
