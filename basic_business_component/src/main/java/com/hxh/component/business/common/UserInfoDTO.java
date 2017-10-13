package com.hxh.component.business.common;

import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.hxh.component.basicore.CoreLib;
import com.hxh.component.basicore.util.Singleton;
import com.hxh.component.basicore.util.Utils;


import java.io.File;

import com.hxh.component.business.BuildConfig;
import com.hxh.component.business.app.App;
import com.hxh.component.business.common.greendao.DaoSession;
import com.hxh.component.business.common.greendao.UserInfoBeanDao;
import com.hxh.component.business.utils.Constant;

/**
 * Created by hxh on 2017/7/8.
 */
public class UserInfoDTO {

    public UserInfoDTO(DaoSession daoSession)
    {
        this.mDaoSession = daoSession;
    }
    ArrayMap<String,String> urls = null;

    //region 用户
    //得到用户信息的方法
    public  UserInfoBean user;

    public  UserInfoBean getUser() {
        if (null == user) {
            user = Singleton.defaultGson().fromJson(Utils.SP.getString(Constant.SP_USERINFO_TAG), UserInfoBean.class);
        }
        if(null != user && !Utils.Text.isEmpty(user.getTenant_str()))
        {
            user.setTenant(JSON.parseObject(user.getTenant_str(),UserInfoBean.TenantBean.class));
        }
        return user;
    }

    public  void setUser(UserInfoBean bean) {
        if (null == bean) {
            //清空
            this.user = null;
            mCurrentToken = "";
            Utils.SP.editor().remove(Constant.SP_USERINFO_TAG).commit();
        } else {

            if(null != bean.getTenant()) bean.setTenant_str(Singleton.defaultGson().toJson(bean.getTenant()));
            if(null != bean.getCustomer())   bean.setCustomer_str(Singleton.defaultGson().toJson(bean.getCustomer()));
            if(null != bean.getUser()) bean.setUser_str(Singleton.defaultGson().toJson(bean.getUser()));

            //往sp中放入一份
            Utils.SP.editor().clear().commit();
            Utils.SP.editor().putString(Constant.SP_USERINFO_TAG, Singleton.defaultGson().toJson(bean)).commit();
            getUserDao()
                    .queryBuilder()
                    .where(UserInfoBeanDao.Properties.User_str.eq(Singleton.defaultGson().toJson(bean.getUser())))
                    .buildDelete()
                    .executeDeleteWithoutDetachingEntities();
            //  Log.d("--->用户数" + mUserDao.queryBuilder().list().size());
            mUserDao.save(bean);

            mCurrentToken = "";
            this.user = null;
            this.user = bean;
        }
         App.getAppInitDelegate().initOss();
        //动态Url
        CoreLib.getInstance().configDynamicHttpUrls(generateBaseUrlMap());
    }

    //region 多Base Url配置

    private ArrayMap<String,String> generateBaseUrlMap()
    {
        if(null == urls)
        {
            urls = new ArrayMap<>();
            urls.put(Constant.APIServicesTag.API_APISERVICES,BuildConfig.AUTH_ENDPOINT);
        }
        return urls;
    }


    public  String getBaseUrl(int name) {
        return getBaseUrl(App.getContext().getResources().getString(name));
    }

    public  String getBaseUrl(String name) {
        if(null == urls)
        {
            CoreLib.getInstance().configDynamicHttpUrls(generateBaseUrlMap()); //动态Url
        }
        String mBaseUrl = CoreLib.getInstance().getBaseUrl(name);

        if (null != mBaseUrl && mBaseUrl.lastIndexOf("/") < 0) {
            mBaseUrl += File.separator;
        }
        return Utils.Text.isEmpty(mBaseUrl) ? BuildConfig.AUTH_ENDPOINT : mBaseUrl;
    }

    //endregion

    public  String mCurrentToken;

    public  String getmCurrentToken() {
        if (!Utils.Text.isEmpty(mCurrentToken)) {
            return mCurrentToken;
        }
        if (getUser() != null) {
            mCurrentToken = "Bearer " + getUser().getAccess_token();
        }
        return mCurrentToken;
    }

    private  UserInfoBeanDao mUserDao;

    private  DaoSession mDaoSession;

    public UserInfoBeanDao getUserDao() {
        if (null != mUserDao) {
            return mUserDao;
        }

        return  mUserDao = mDaoSession.getUserInfoBeanDao();
    }
    //endregion

}
