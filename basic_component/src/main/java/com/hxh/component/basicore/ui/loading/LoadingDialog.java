package com.hxh.component.basicore.ui.loading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxh.component.basicore.R;

/**
* 创建者：hxh
* 时间：  2017/3/4
* 描述： 加入一个单例
*/
public class LoadingDialog {

    private LVCircularRing mLoadingView;
    private Dialog mLoadingDialog;
    private TextView loadingText;



    public LoadingDialog(Context context, String msg) {
        init(context,msg,false);
    }
    public LoadingDialog(Context context, String msg,boolean isCancelable) {
        init(context,msg,isCancelable);
    }

    private void init(Context context,String msg,boolean isCancelable)
    {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.loading_dialog_view, null);
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = (LVCircularRing) view.findViewById(R.id.lv_circularring);
        // 页面中显示文本
        loadingText = (TextView) view.findViewById(R.id.loading_text);
        if ("".equals(msg) || null == msg) {
            loadingText.setVisibility(View.GONE);
        } else {
            // 显示文本
            loadingText.setText(msg);
        }

        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void show() {
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
            mLoadingView.startAnim();
        }
    }


    public void close() {
        if (mLoadingDialog != null) {
            mLoadingView.stopAnim();
            try {
                mLoadingDialog.dismiss();
                //mLoadingView = null;
               // mLoadingDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setLoadingText(String loadmsg)
    {
        loadingText.setText(loadmsg);
    }
}
