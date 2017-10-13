package com.hxh.component.business.utils.oss.action;

import com.alibaba.sdk.android.oss.model.OSSResult;

/**
 * Created by hxh on 2017/5/16.
 */
public interface RequestCallBack {
    void onSuccess(OSSResult result);
    void onFaild();
}
