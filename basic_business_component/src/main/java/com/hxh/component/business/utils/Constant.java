package com.hxh.component.business.utils;

import com.hxh.component.basicore.Config;

/**
 * Created by hxh on 2017/5/4.
 */
public class Constant {

    public static final String SP_USERINFO_TAG ="userinfo";
    public static final String SP_ISFIRST_TAG ="isfristenter";
    public static final String LOGTAG = "xiaoaikeji";

    public static final String RouterPath_DefalutLoginView = Config.RouterPath_DefalutLoginView;

    public static class OSSId
    {
        //示例
        public static final String OSS_contract = "contract";// 合同管理
    }

    //服务器地址统一在此注册
    public static class APIServicesTag
    {
        public static final String API_WORK = "working";
        public static final String API_APISERVICES= "apiservices";
        public static final String API_SMS = "sms";


    }

    //Router 相关Tag值再此注册
    public static class RouterTag
    {

        public static final String LOGIN_SHOWTYPE_SMSLOGIN = "veritylogin";

        public static final String FROM_MINE_TO_RESUME = "fromMinetoresume";

    }

    public static class SpTag
    {
        public static final String MINE_MINERESUME_RESUMEDATA = "resumedata";
    }
}
