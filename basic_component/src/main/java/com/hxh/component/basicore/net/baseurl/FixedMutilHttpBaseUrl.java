package com.hxh.component.basicore.net.baseurl;/**
 * Created by hxh on 2017/8/14.
 */



import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * 创建者：hxh
 * 时间：  2017/8/14
 * 描述：多BaseUrl配置（静态）
 */
public interface FixedMutilHttpBaseUrl {
    void configBaeUrl(ArrayMap<Object,String> httpurls);
}
