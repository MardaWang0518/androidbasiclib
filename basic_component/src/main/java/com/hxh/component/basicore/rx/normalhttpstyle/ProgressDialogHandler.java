package com.hxh.component.basicore.rx.normalhttpstyle;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.hxh.component.basicore.ui.loading.LoadingDialog;

import java.lang.ref.WeakReference;

/**
 * Loading
 */
@Deprecated
public class ProgressDialogHandler extends Handler {

    //显示
    public static final int SHOW_DIALOG = 1;
    //隐藏
    public static final int HIDE_DIALOG = 2;

    private LoadingDialog mDialog;

    private WeakReference<Context> mContext;
    private ProgressCancelListener mListener; //回调
    private String loadingMsg;//loading的文本
    private boolean isCanable;//Loading是否可以取消

    public ProgressDialogHandler(Context context, ProgressCancelListener listener)
    {
        super();
        this.mContext =new WeakReference<Context>(context);
        this.mListener = listener;
    }

    public ProgressDialogHandler(String msg,Context context, ProgressCancelListener listener)
    {
        super();
        this.loadingMsg = msg;
        this.mContext =new WeakReference<Context>(context);
        this.mListener = listener;
    }

    public ProgressDialogHandler(String msg,Context context, ProgressCancelListener listener ,boolean iscanable)
    {
        super();
        this.loadingMsg = msg;
        this.mContext =new WeakReference<Context>(context);
        this.mListener = listener;
        this.isCanable = iscanable;
    }



    private void showDialog()
    {
        if(null == mDialog )
        {
            mDialog = new LoadingDialog(mContext.get(),"",isCanable);
        }
        //用户手动取消loading时候，结束事件链
        mDialog.show();
    }

    private void dissDialog()
    {
        if(null != mDialog)
        {
            mDialog.close();
        }
    }



    @Override
    public void handleMessage(Message msg) {
        //  super.handleMessage(msg);
        switch (msg.what)
        {
            case HIDE_DIALOG:
                dissDialog();
                break;
            case SHOW_DIALOG:
                showDialog();
                break;
        }
    }
}
