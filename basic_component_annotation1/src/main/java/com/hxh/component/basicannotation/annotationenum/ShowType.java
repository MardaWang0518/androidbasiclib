package com.hxh.component.basicannotation.annotationenum;

/**
 * Created by hxh on 2017/7/7.
 */
public enum ShowType {
    TOAST,  // 以Toast告知用户
    DIALOG, // 以Dialog形式告知用户
    NORMAL,  // 以展示Fragment，Activity形式告知用户
    DIALOG_AND_NORMAL//先展示一个Dialog，提示用户，当点击确认的时候，弹出登录
}
