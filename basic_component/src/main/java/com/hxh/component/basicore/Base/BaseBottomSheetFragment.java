package com.hxh.component.basicore.Base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;

import com.hxh.component.basicore.util.Utils;

/**
 * Created by hxh on 2017/5/12.
 */
public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment {
    protected Context mContext;

    protected View rootView;
    protected BottomSheetBehavior mBehavior;

    protected Dialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除缓存View和当前ViewGroup的关联
        if(null != ((ViewGroup) (rootView.getParent())))
        {
            ((ViewGroup) (rootView.getParent())).removeView(rootView);
        }

    }


//    @Nullable
//    @Override
//    public View getView() {
//        return rootView;
//    }
//
//    @Override
//    public Context getContext() {
//        return mContext;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //每次打开都调用该方法 类似于onCreateView 用于返回一个Dialog实例
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        if (rootView == null) {
            //缓存下来的View 当为空时才需要初始化 并缓存
            rootView = View.inflate(mContext, getLayoutResId(), null);
            initView();
        }
        resetView();
        //设置View重新关联
        int height = getPeekHeight();//屏幕高的75%
        if(0 == height)
        {
            ViewGroup.LayoutParams layoutParams =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,940);
            dialog.setContentView(rootView, layoutParams);//设置View 并带有布局参数的
        }else
        {

            ViewGroup.LayoutParams layoutParams =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            dialog.setContentView(rootView, layoutParams);//设置View 并带有布局参数的
        }



        mBehavior = BottomSheetBehavior.from((View) rootView.getParent());
        mBehavior.setHideable(true);
        //圆角边的关键
        ((View) rootView.getParent()).setBackgroundColor(Color.TRANSPARENT);
        rootView.post(new Runnable() {
            @Override
            public void run() {
                /**
                 * PeekHeight默认高度256dp 会在该高度上悬浮
                 * 设置等于view的高 就不会卡住
                 */
                mBehavior.setPeekHeight(height);
//                if(getPeekHeight()>0)
//                {
//                    mBehavior.setPeekHeight(getPeekHeight());
//                }else
//                {
//                    mBehavior.setPeekHeight(rootView.getHeight());
//                }

            }
        });


        return dialog;
    }


    @Override
    public Dialog getDialog() {
        return dialog;
    }

    public abstract int getLayoutResId();

    /**
     * 初始化View和设置数据等操作的方法
     */
    public abstract void initView();

    /**
     * 重置的View和数据的空方法 子类可以选择实现
     * 为避免多次inflate 父类缓存rootView
     * 所以不会每次打开都调用{@link #initView()}方法
     * 但是每次都会调用该方法 给子类能够重置View和数据
     */
    public void resetView() {

    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 使用关闭弹框 是否使用动画可选
     * 使用动画 同时切换界面Aty会卡顿 建议直接关闭
     *
     * @param isAnimation
     */
    public void close(boolean isAnimation) {
        if (isAnimation) {
            if (mBehavior != null)
                mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            dismiss();
        }
    }

    public View getRootView() {
        return rootView;
    }

    public BottomSheetBehavior getmBehavior() {
        return mBehavior;
    }

    public abstract int getPeekHeight();

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        mBehavior = null;
    }
}
