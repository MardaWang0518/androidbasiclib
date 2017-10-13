package com.hxh.component.business.utils;

/**
 * Created by hxh on 2017/5/4.
 */
public class Constant {

    public static final String SP_USERINFO_TAG ="userinfo";
    public static final String SP_ISFIRST_TAG ="isfristenter";
    public static final String LOGTAG = "xiaoaikeji";

    public static final String RouterPath_DefalutLoginView = "/user/main/login1";

    public static class OSSId
    {
        public static final String OSS_crm  = "crm";// 客户管理)
        public static final String OSS_recruit  = "recruit";// 招聘管理
        public static final String OSS_contract = "contract";// 合同管理
        public static final String OSS_user_image = "user_image";//个人图像
        public static final String OSS_tenant_logo = "tenant_logo";//企业logo

    }

    //服务器地址统一在此注册
    public static class APIServicesTag
    {
        public static final String API_WORK = "working";
        public static final String API_APISERVICES= "apiservices";
        public static final String API_SMS = "sms";

        public static final String API_SCORE = "score";
        public static final String API_HR = "hr";
        public static final String API_OSSCALLBACK = "oss_callback";
        public static final String API_MOMENT = "momment";
        public static final String API_CONTRACT = "contract";

        public static final String API_ATTENDANCE = "attendance";
        public static final String API_WELFARE = "welfare";
    }

    //Router 相关Tag值再此注册
    public static class RouterTag
    {
        public static final String SHOWTAG = "loginflag";
        public static final String LOGIN_SHOWTYPE_LOGIN = "pwdlogin";
        public static final String LOGIN_SHOWTYPE_SMSLOGIN = "veritylogin";
        public static final String LOGIN_SHOWTYPE_SETPWD = "setpwd";



        public static final String MINE_SHOWTYPE_MYSETTING = "mysetting";
        public static final String MINE_SHOWTYPE_MYMESSAGE = "mymsg";
        public static final String MINE_SHOWTYPE_MYOPINION = "myopinion";


        public static final   String EMPLOYSERVICES_CONFIRMMYBASICINFO = "confimbasicinfo";
        public static final   String EMPLOYSERVICES_INPUTMYBASICINFO = "INPUTBASICINFO";


        public static final String MINE_ADDBANKCARD = "addcard";
        public static final String MINE_UPDATEBANKCARD = "updatecard";
    }

}
