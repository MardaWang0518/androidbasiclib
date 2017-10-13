package com.hxh.component.basicore.net.baseurl;/**
 * Created by hxh on 2017/8/14.
 */

import android.support.v4.util.ArrayMap;
import android.util.SparseArray;

import com.hxh.component.basicore.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者：hxh
 * 时间：  2017/8/14
 * 描述：多BaseUrl配置（动态）
 * 请在你获取到Url的地方，调用CoreLib.getInstance().configDynamicHttpUrls() 放入你的Url集合
 */
public class DynamicMutilHttpBaseUrl {
    private ArrayMap<String,String> mHttpUrls = null;


    public void setHttpUrls(ArrayMap<String,String> urls)
    {
        this.mHttpUrls = urls;
    }

    public ArrayMap<String,String> getHttpUrls()
    {
        if(null == mHttpUrls || 0== mHttpUrls.size())
        {
            return null;
        }
        return mHttpUrls;
    }



    public String getUrl(String tag)
    {
        if(null != mHttpUrls)
        {
            String a = mHttpUrls.get(tag);
            return a;
        }
        return null;
    }
}
