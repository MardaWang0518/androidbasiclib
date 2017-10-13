package com.hxh.component.basicore.CommonConponet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxh.component.basicore.R;
import com.hxh.component.basicore.util.Utils;

/**
 * Created by hxh on 2017/7/3.
 */

public class DefaultBackButton extends FrameLayout {
    public DefaultBackButton(Context context) {
        this(context, null);
    }

    private TextView tv_defaultleftview;
    private ImageView actionbar_leftview;

    public DefaultBackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.layout_default_button, null);
        actionbar_leftview = (ImageView) view.findViewById(R.id.actionbar_leftview);
        tv_defaultleftview = (TextView) view.findViewById(R.id.tv_defaultleftview);
        addView(view);
    }


    public void setColor(int color) {
        this.tv_defaultleftview.setTextColor(color);
        Bitmap map = BitmapFactory.decodeResource(Utils.getApplicationContext().getResources(), R.mipmap.icon_back_white);
        this.actionbar_leftview.setImageBitmap(Utils.BitmapUtils.replaceBitmapColor(map, Color.WHITE, color));
    }

    /**
     * 配置BackView
     *
     * @param backMap   返回图标
     * @param backTitle 返回文字
     * @param color     整体颜色
     */
    public void setBackView(Bitmap backMap, String backTitle, int color) {
        if (null == backMap) {
            this.actionbar_leftview.setVisibility(GONE);
        } else {
            this.actionbar_leftview.setVisibility(View.VISIBLE);
            //
            //int color1 = Color.WHITE;
            this.actionbar_leftview.setImageBitmap(Utils.BitmapUtils.replaceBitmapColor(backMap, Color.WHITE, color));
        }
        if (null != backTitle || !"".equals(backTitle)) {


            this.tv_defaultleftview.setTextColor(color);
            this.tv_defaultleftview.setText(backTitle);
        } else {
            this.tv_defaultleftview.setVisibility(GONE);
        }

    }

    public void setBacViewTitleMaxNumber()
    {
        this.tv_defaultleftview.setSingleLine();
        this.tv_defaultleftview.setMaxEms(5);
        this.tv_defaultleftview.setEllipsize(TextUtils.TruncateAt.END);
    }

    /**
     * 配置BackView
     * @param backMap    返回图标
     * @param backTitle    返回文字
     */
    public void setBackView_noReplace(Bitmap backMap,String backTitle,int color)
    {

        if (null == backMap) {
            this.actionbar_leftview.setVisibility(GONE);
        } else
        {
            this.actionbar_leftview.setVisibility(View.VISIBLE);
            this.actionbar_leftview.setImageBitmap(backMap);
        }
        if(null!=backTitle || !"".equals(backTitle) )
        {
            this.tv_defaultleftview.setTextColor(color);
            this.tv_defaultleftview.setText(backTitle);
        }else
        {
            this.tv_defaultleftview.setVisibility(GONE);
        }

    }





    /**
     *
     * 配置BackView
     * @param backMap       返回图标
     * @param backTitle     返回文字
     * @param oldColor      原来图标中的主色值
     * @param newColor      替换为什么颜色值
     */
    public void setBackView(Bitmap backMap,String backTitle,int oldColor,int newColor)
    {
        //0  65536
        // this.tv_defaultleftview.setTextColor(newColor);
        if (null == backMap) {
            this.actionbar_leftview.setVisibility(GONE);
        } else
        {
            this.actionbar_leftview.setVisibility(View.VISIBLE);
            this.actionbar_leftview.setImageBitmap(Utils.BitmapUtils.replaceBitmapColor(backMap, oldColor,newColor));
        }
        if(null!=backTitle || !"".equals(backTitle) )
        {
            this.tv_defaultleftview.setTextColor(newColor);
            this.tv_defaultleftview.setText(backTitle);
        }else
        {
            this.tv_defaultleftview.setVisibility(GONE);
        }
    }



    public void setImageAndTextVisible(boolean isshowbacktext, boolean isshowbackimg)
    {
        this.tv_defaultleftview.setVisibility(isshowbacktext==true?VISIBLE:GONE);
        this.actionbar_leftview.setVisibility(isshowbackimg==true?VISIBLE:GONE);
    }


    public void setBackText(String text)
    {
        this.tv_defaultleftview.setVisibility(VISIBLE);
        this.tv_defaultleftview.setText(text);
    }


    public TextView getBackTextView()
    {
        return this.tv_defaultleftview;
    }
}
