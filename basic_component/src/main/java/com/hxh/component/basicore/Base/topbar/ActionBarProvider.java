package com.hxh.component.basicore.Base.topbar;/**
 * Created by hxh on 2017/9/22.
 */

import android.graphics.Bitmap;

import java.util.List;

/**
 * 创建者：hxh
 * 时间：  2017/9/22
 * 描述：  如果你不做任何配置，那么当你在具体页面开启这个支持时候，会导致backview为空，以及大小为默认
 *
 *
 */
public class ActionBarProvider {

    private int oneColor,twoColor;
    private List<NLevelPageBgConfigBean> pageBackgroundConfigs;
    private int topBar_height;
    /**
     * 配置返回图标
     */
    public Bitmap m_backImg;
    /**
     * 配置返回文本
     */
    public String m_backTitle;
    /**
     * 图标和文字的颜色
     * 图标默认不改
     * 文字使用此设置的，没有设置就使用白色
     */
    public int backColor;


    public static class Builder
    {
        private int topBar_height;

        private Bitmap m_backImg;
        private String m_backTitle;
        private int backColor;
        private int oneBGColor,twoBGColor;
        private List<NLevelPageBgConfigBean> mNlevelPagesConfigs;
        public Builder height(int height)
        {
            this.topBar_height = height;
            return this;
        }

        public Builder backImg(Bitmap m_backImg)
        {
            this.m_backImg = m_backImg;
            return this;
        }

        public Builder backText(String m_backTitle)
        {
            this.m_backTitle = m_backTitle;
            return this;
        }

        public Builder backColor(int backColor)
        {
            this.backColor = backColor;
            return this;
        }

        public Builder oneLevelPageBackgroundColor(int oneBGColor)
        {
            this.oneBGColor = oneBGColor;
            return this;
        }


        public Builder twoLevelPageBackgroundColor(int twoBGColor)
        {
            this.twoBGColor = twoBGColor;
            return this;
        }

        public Builder NLevelPageBackgroundColor(List<NLevelPageBgConfigBean> configs)
        {
            this.mNlevelPagesConfigs = configs;
            return this;
        }




        public ActionBarProvider build()
        {
            return new ActionBarProvider(topBar_height,m_backImg,m_backTitle,backColor,oneBGColor,twoBGColor,mNlevelPagesConfigs);
        }

    }

    /**
     *
     * @param m_backImg  指定返回键
     * @param m_backTitle 指定返回文字
     * @param backColor  指定返回键的颜色
     */
    public ActionBarProvider(int height,Bitmap m_backImg, String m_backTitle,int backColor
    ,int oneColor,int twoColor,List<NLevelPageBgConfigBean> configs) {
        this.topBar_height = height;
        this.m_backImg = m_backImg;
        this.m_backTitle = m_backTitle;
        this.backColor = backColor;
        this.pageBackgroundConfigs = configs;
        this.oneColor = oneColor;
        this.twoColor = twoColor;
    }



    public Bitmap getM_backImg() {
        return m_backImg;
    }

    public void setM_backImg(Bitmap m_backImg) {
        this.m_backImg = m_backImg;
    }

    public String getM_backTitle() {
        return m_backTitle;
    }

    public void setM_backTitle(String m_backTitle) {
        this.m_backTitle = m_backTitle;
    }

    public int getBackColor() {
        return backColor;
    }

    public void setBackColor(int color) {
        this.backColor = color;
    }

    public int getOneColor() {
        return oneColor;
    }

    public int getTwoColor() {
        return twoColor;
    }

    public List<NLevelPageBgConfigBean> getPageBackgroundConfigs() {
        return pageBackgroundConfigs;
    }

    public int getTopBar_height() {
        return topBar_height;
    }
}
