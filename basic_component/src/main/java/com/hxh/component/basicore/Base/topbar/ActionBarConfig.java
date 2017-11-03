package com.hxh.component.basicore.Base.topbar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;

import com.hxh.component.basicore.util.AppManager;

/**
 * ActionBar/toolbar 配置类
 */
public class ActionBarConfig {
    private String titleText;
    private BackViewConfig backViewConfig;
    private int backViewColor,right_buttontitleColor,titleColor,backgroundColor;
    private boolean enable_backview = false;
    private boolean enable_rightview = false;
    private String right_buttontitle;
    private int right_buttonImgResId;
    private int[] right_imgResIds;
    private OnClickLinstener listener;
    private boolean enable_splitline ;


    public ActionBarConfig(String titleText,String right_buttontitle,BackViewConfig backViewConfig,
                           boolean enable_backview, boolean enable_rightview,int right_buttonImgResId,
                           int[] right_imgResIds,int backViewColor,int right_buttontitleColor,
                           int titleColor,int backgroundColor,OnClickLinstener lis,boolean enable_splitline)
    {
        this.titleColor = titleColor;
        this.titleText = titleText;
        this.backViewConfig = backViewConfig;
        this.backViewColor = backViewColor;
        this.right_buttontitleColor = right_buttontitleColor;
        this.backgroundColor = backgroundColor;
        this.enable_backview = enable_backview;
        this.enable_rightview = enable_rightview;
        this.right_buttontitle = right_buttontitle;
        this.right_buttonImgResId = right_buttonImgResId;
        this.right_imgResIds = right_imgResIds;
        this.listener = lis;
        this.enable_splitline = enable_splitline;
    }


    public static class Builder {
        private String titleText;
        private BackViewConfig backViewConfig;
        private int backViewColor = 1234, right_buttontitleColor = Color.WHITE, titleColor, backgroundColor;
        private boolean enable_backview = false;
        private boolean enable_rightview = false;
        private String right_buttontitle;
        private int right_buttonImgResId = 1234;
        private int[] right_imgResIds;
        private OnClickLinstener listener;
        private boolean enable_splitline=true ;
        public Builder title(String text) {
            this.titleText = text;
            return this;
        }

        public Builder title(int textcolor) {
            this.titleColor = textcolor;
            return this;
        }

        public Builder title(String text, int textcolor) {
            this.titleText = text;
            this.titleColor = textcolor;
            return this;
        }

        public Builder backgroundColor(int textcolor) {
            this.backgroundColor = textcolor;
            return this;
        }

        public Builder enableSplitLine(boolean isenable)
        {
            this.enable_splitline = isenable;
            return this;
        }

        public Builder listener(OnClickLinstener listener) {
            this.listener = listener;
            return this;
        }


        public ActionBarConfig build() {
            return new ActionBarConfig(titleText,right_buttontitle,backViewConfig,enable_backview,enable_rightview,
                    right_buttonImgResId,right_imgResIds,backViewColor,right_buttontitleColor,titleColor,backgroundColor,listener,enable_splitline);
        }

        //region BackView
        /**
         * 自定义BackView
         * @param backViewConfig
         * @return
         */
        public Builder enableBackView(BackViewConfig backViewConfig)
        {
            this.enable_backview = true;
            this.backViewConfig = backViewConfig;
            return this;
        }

        /**
         * 使用默认的BackView(用你配置的GlobalActionBarConfig)
         * @param backColor  修改Backview的颜色 (图标和文字都改)
         * @return
         */
        public Builder enableBackView(int backColor)
        {
            this.enable_backview = true;
            this.backViewColor = backColor;
            return this;
        }



        /**
         * 使用默认的BackView（颜色及图标由初始化配置提供）
         * @return
         */
        public Builder enableBackView()
        {
            this.enable_backview = true;
            return this;
        }
        //endregion


        //region 右边
        public Builder enableRightView(String rightButtonTitle)
        {
            this.right_buttontitle = rightButtonTitle;
            this.enable_rightview = true;
            return this;
        }

        public Builder enableRightView(String rightButtonTitle,int rightButtonTitleColor)
        {
            this.right_buttontitle = rightButtonTitle;
            this.enable_rightview = true;
            this.right_buttontitleColor = rightButtonTitleColor;
            return this;
        }

        public Builder enableRightView(int rightButtonImgResId)
        {
            this.right_buttonImgResId = rightButtonImgResId;
            this.enable_rightview = true;
            return this;
        }


        public Builder enableRightView(int... imgResId)
        {
            this.right_imgResIds = imgResId;
            this.enable_rightview = true;
            return this;
        }


        //endregion

    }




    //region Getter
    public String getTitleText() {
        return titleText;
    }

    public BackViewConfig getBackViewConfig() {
        return backViewConfig;
    }

    public int getBackViewColor() {
        return backViewColor;
    }

    public int getRight_buttontitleColor() {
        return right_buttontitleColor;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public boolean isEnable_backview() {
        return enable_backview;
    }

    public boolean isEnable_rightview() {
        return enable_rightview;
    }

    public String getRight_buttontitle() {
        return right_buttontitle;
    }

    public int getRight_buttonImgResId() {
        return right_buttonImgResId;
    }

    public int[] getRight_imgResIds() {
        return right_imgResIds;
    }

    public OnClickLinstener getListener() {
        return listener;
    }

    public boolean isEnable_splitline() {
        return enable_splitline;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public void setBackViewConfig(BackViewConfig backViewConfig) {
        this.backViewConfig = backViewConfig;
    }

    public void setBackViewColor(int backViewColor) {
        this.backViewColor = backViewColor;
    }

    public void setRight_buttontitleColor(int right_buttontitleColor) {
        this.right_buttontitleColor = right_buttontitleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setEnable_backview(boolean enable_backview) {
        this.enable_backview = enable_backview;
    }

    public void setEnable_rightview(boolean enable_rightview) {
        this.enable_rightview = enable_rightview;
    }

    public void setRight_buttontitle(String right_buttontitle) {
        this.right_buttontitle = right_buttontitle;
    }

    public void setRight_buttonImgResId(int right_buttonImgResId) {
        this.right_buttonImgResId = right_buttonImgResId;
    }

    public void setRight_imgResIds(int[] right_imgResIds) {
        this.right_imgResIds = right_imgResIds;
    }

    public void setListener(OnClickLinstener listener) {
        this.listener = listener;
    }

    public void setEnable_splitline(boolean enable_splitline) {
        this.enable_splitline = enable_splitline;
    }
    //endregion





    //region 配置类
    public static class BackViewConfig
    {
        private int color;
        private Bitmap backBitmap;
        private String backTitle;
        private View backView;

        /**
         * 不提供返回图标
         * @param backTitleColor 返回文本的颜色
         * @param backTitle  返回文本
         */
        public BackViewConfig(int backTitleColor, String backTitle)
        {
            this.color = backTitleColor;
            this.backTitle = backTitle;
        }

        /**
         * 提供返回图标，返回文本，返回文本颜色
         * @param backTitleColor  返回文本颜色
         * @param backImg   返回图标
         * @param backTitle   返回文本
         */
        public BackViewConfig(int backTitleColor,Bitmap backImg, String backTitle)
        {
            this.color = backTitleColor;
            this.backBitmap = backImg;
            this.backTitle = backTitle;
        }


        public BackViewConfig(String backTitle)
        {
            this.backTitle = backTitle;
        }


        public BackViewConfig(Bitmap backImg)
        {
            this.backBitmap = backImg;
        }

        /**
         * 提供一个返回View
         * @param backview
         */
        public BackViewConfig(View backview)
        {
            this.backView = backview;
        }

        public int getColor() {
            return color;
        }

        public Bitmap getBackBitmap() {
            return backBitmap;
        }

        public String getBackTitle() {
            return backTitle;
        }

        public View getBackView() {
            return backView;
        }
    }

    public static  class OnClickLinstener
    {

        public void onLeftClick()
        {
            AppManager.back();
        }
        public  void onRightClick(){

        }

        public void onRightButtonsClick(int position)
        {

        }


    }
    //endregion
}
