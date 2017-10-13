package com.hxh.component.business.utils.oss.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.hxh.component.basicore.net.BaseAPI;
import com.hxh.component.basicore.rx.RxUtils;


import java.util.HashMap;
import java.util.Map;

import com.hxh.component.basicannotation.annotation.Safe;
import rx.Observable;
import rx.functions.Action1;
import com.hxh.component.business.app.App;
import com.hxh.component.business.utils.Constant;
import com.hxh.component.business.utils.oss.CallBackBody;
import com.hxh.component.business.utils.oss.request.Request_GetOssInfo;
import com.hxh.component.business.utils.oss.response.InitOssResponse;

/**
 * 阿里云存储 工具类
 * 图片的处理操作可见：
 * https://help.aliyun.com/document_detail/32226.html?spm=5176.doc32228.6.982.kpEJB0
 */
public class OSSUtil {

    public static long OSS_EXPIRE = 60 * 60; // 阿里云oss url签名有效时间 60分钟
    public static InitOssResponse mDefaultinitOSSResponse;
    private static Request_GetOssInfo mRequestBody;//默认保存请求信息
    private static OSS mDefaultOss;
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";

    public static OSS getOss(InitOssResponse ossResponse) {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ossResponse.getAccess_key_id(), ossResponse.getAccess_key_secret(), ossResponse.getSecurity_token());
        return new OSSClient(App.getContext(), ossResponse.getDomain(), credentialProvider);
    }

    /**
     * 初始化Oss
     *
     * @param request 返回给你一个Oss初始化结果，其中有你想得到的数据
     * @return
     */
    public static void initOss_default(Request_GetOssInfo request) {
        mRequestBody = request;
        BaseAPI.getInstance().createServices(App.getUserDTO().getBaseUrl(Constant.APIServicesTag.API_APISERVICES), OssApiService.class)
                .getOssInitInfo(request)
                .compose(RxUtils.<InitOssResponse>io_main())
                .doOnNext(new Action1<InitOssResponse>() {
                    @Override
                    public void call(InitOssResponse rep) {
                        mDefaultinitOSSResponse = rep;
                    }
                })
                .subscribe(new Action1<InitOssResponse>() {
                    @Override
                    public void call(InitOssResponse response) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    /**
     * 初始化Oss，返回一个Observable
     *
     * @param request
     * @return
     */
    public static Observable<InitOssResponse> initOss(Request_GetOssInfo request) {
        mRequestBody = request;
        return BaseAPI
                .getInstance()
                .createServices(App.getUserDTO().getBaseUrl(Constant.APIServicesTag.API_APISERVICES), OssApiService.class)
                .getOssInitInfo(request)
                .compose(RxUtils.<InitOssResponse>io_main())
                .doOnNext(new Action1<InitOssResponse>() {
                    @Override
                    public void call(InitOssResponse rep) {
                        mDefaultinitOSSResponse = rep;
                    }
                });
    }

    public static Observable<InitOssResponse> initOss_noCache(Request_GetOssInfo request) {

        return BaseAPI
                .getInstance()
                .createServices(App.getUserDTO().getBaseUrl(Constant.APIServicesTag.API_APISERVICES), OssApiService.class)
                .getOssInitInfo(request)
                .compose(RxUtils.<InitOssResponse>io_main())
                ;
    }



    public static final String DEFAULT_CROP_SIZE = "@180h_300w_1e_1c_1l";  // 默认的剪裁图片大小,短边优先,自动剪裁
    // 用户头像end point ,完整的头像url组成为  endpoint + tenantId/user_uuid.jpg
    private static final String USER_PORTRAIT_ENDPOINT = "http://krhrimg.img-cn-beijing.aliyuncs.com/user_image/";
    private static final String COMPANY_ICON_ENDPOINT = "http://krhrimg.img-cn-beijing.aliyuncs.com/";

    public static String getPortraitUrl(Context context, String userUUID) {
        int size = 20;
        return USER_PORTRAIT_ENDPOINT + userUUID + ".jpg" + "@" + size + "h_" + size + "w_1e";
    }

    public static PutObjectResult putObject_new(Context context, InitOssResponse initOSSResponse, String buckerName, String objectkey, String filePath) throws ClientException, ServiceException {
        PutObjectRequest put = new PutObjectRequest(buckerName, objectkey, filePath);

        if (mDefaultOss == null) {
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(initOSSResponse.getAccess_key_id(), initOSSResponse.getAccess_key_secret(), initOSSResponse.getSecurity_token());
            mDefaultOss = new OSSClient(context, endpoint, credentialProvider);
        }

        return mDefaultOss.putObject(put);
    }

    public static  OSSAsyncTask<PutObjectResult> putObject_new(Context context, InitOssResponse initOSSResponse, String buckerName, String objectkey, String filePath,OSSCompletedCallback<PutObjectRequest,PutObjectResult> callbac) throws ClientException, ServiceException {
        PutObjectRequest put = new PutObjectRequest(buckerName, objectkey, filePath);

        if (mDefaultOss == null) {
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(initOSSResponse.getAccess_key_id(), initOSSResponse.getAccess_key_secret(), initOSSResponse.getSecurity_token());
            mDefaultOss = new OSSClient(context, endpoint, credentialProvider);
        }

        return mDefaultOss.asyncPutObject(put,callbac);
    }

    public static PutObjectResult putObject_new(Context context, InitOssResponse initOSSResponse, String buckerName, String objectkey, byte[] filebytes) throws ClientException, ServiceException {
        PutObjectRequest put = new PutObjectRequest(buckerName, objectkey, filebytes);

        if (mDefaultOss == null) {
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(initOSSResponse.getAccess_key_id(), initOSSResponse.getAccess_key_secret(), initOSSResponse.getSecurity_token());
            mDefaultOss = new OSSClient(context, endpoint, credentialProvider);
        }

        return mDefaultOss.putObject(put);
    }


    public static String getDynamicUrl_new(Context context, InitOssResponse initOSSResponse, String bucetName,String objectKey, OSSProgressCallback<PutObjectRequest> progressCallback)  {
       try
       {
           GetObjectRequest request  = new GetObjectRequest(bucetName,objectKey);
           if (mDefaultOss == null) {
               OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(initOSSResponse.getAccess_key_id(), initOSSResponse.getAccess_key_secret(), initOSSResponse.getSecurity_token());
               mDefaultOss = new OSSClient(context, endpoint, credentialProvider);
           }
           return mDefaultOss.presignPublicObjectURL(bucetName,objectKey);
       }catch (Exception e)
       {

       }
      return null;
    }

    public static String getDynamicFile_new(Context context, InitOssResponse initOSSResponse, String bucetName,String objectKey, OSSProgressCallback<PutObjectRequest> progressCallback) throws ClientException, ServiceException {
        GetObjectRequest request  = new GetObjectRequest(bucetName,objectKey);
        if (mDefaultOss == null) {
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(initOSSResponse.getAccess_key_id(), initOSSResponse.getAccess_key_secret(), initOSSResponse.getSecurity_token());
            mDefaultOss = new OSSClient(context, endpoint, credentialProvider);
        }
        return mDefaultOss.presignPublicObjectURL(bucetName,objectKey);
    }



    /**
     * 获取公司logo的URL 暂时是截取再拼接的方式
     */
    public static String getCompanyIconUrl(String icon) {
        if (icon.contains("/krhrimg/")) {
            return COMPANY_ICON_ENDPOINT + icon.substring(9, icon.length());
        } else {
            return COMPANY_ICON_ENDPOINT + icon;
        }
    }

    /**
     * 获取联系人头像url
     *
     * @param userUUID 用户uuid
     * @param width    目标头像宽度 px
     * @param height   目标头像高度 px
     * @return 指定高度和宽度的联系人头像url, 短边优先
     */
    public static String getPortraitUrl(String userUUID, int width, int height) {
        String url = USER_PORTRAIT_ENDPOINT + userUUID + ".jpg" + "@" + width + "w_" + height + "h_1e";

        return url;
    }

    /**
     * 获取动态列表中图片的url，请注意filepath 为OssUploadImageBean中的path
     * @param context
     * @param initOSSResponse
     * @param filePath   OssUploadImageBean中的path
     * @param progressCallback
     * @return
     */
    public static String getDynamicUrl(Context context, InitOssResponse initOSSResponse, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) {
        return getImageUrl(context,initOSSResponse,filePath,DEFAULT_CROP_SIZE,progressCallback);
    }


    public static String getDynamicUrl(Context context,InitOssResponse initOSSResponse,String objectstr,boolean ispublic)
    {
        OSS oss = null;
        String url = null;
        if (oss == null) {
            BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, objectstr, new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest putObjectRequest, long l, long l1) {

                }
            }).invoke();
            oss = buildPutObjectRequest.getOss();
        }
        try
        {
            url = oss.presignPublicObjectURL(initOSSResponse.getBucket(),objectstr);
        }catch (Exception e)
        {

        }
        url = url.replaceFirst("oss", "img");
        return url;
    }

    @Safe
    private static String getImageUrl(Context context,InitOssResponse initOSSResponse, String fileNetPath,String cropoptions,OSSProgressCallback<PutObjectRequest> progressCallback)
    {
        OSS oss = null;
        String url = null;
        if (oss == null) {
            BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, fileNetPath, progressCallback).invoke();
            oss = buildPutObjectRequest.getOss();
        }
        try
        {
            url = oss.presignConstrainedObjectURL(initOSSResponse.getBucket(),fileNetPath+cropoptions, OSS_EXPIRE);
        }catch (Exception e)
        {

        }
        url = url.replaceFirst("oss", "img");
        return url;
    }

    /**
     * 获取图片的URl 带裁剪
     * @param context
     * @param initOSSResponse
     * @param filePath
     * @param width  裁剪的宽
     * @param height  裁剪的高
     * @param progressCallback
     * @return
     */
    public static String getDynamicUrl(Context context, InitOssResponse initOSSResponse, String filePath, int width, int height, OSSProgressCallback<PutObjectRequest> progressCallback) {
        return getImageUrl(context,initOSSResponse,filePath,"@" + height + "h_" + width + "w_1e",progressCallback);
    }

    /**
     * 得到图片的信息
     * @param context
     * @param initOSSResponse
     * @param filePath
     * @param progressCallback
     * @return
     */
    public static String getImageInfo(Context context, InitOssResponse initOSSResponse, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback)
    {
        OSS oss = null;
        String url = null;
        if (oss == null) {
            BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, filePath+"@infoexif", progressCallback).invoke();
            oss = buildPutObjectRequest.getOss();
        }
        try {
            url = oss.presignConstrainedObjectURL(initOSSResponse.getBucket(), filePath+"@infoexif", OSS_EXPIRE);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        url = url.replaceFirst("oss", "img");

        return url;
    }

    /**
     * 只得到图片的基础信息
     * @param context
     * @param initOSSResponse
     * @param filePath
     * @param progressCallback
     * @return
     */
    public static String getBasicImageInfo(Context context, InitOssResponse initOSSResponse, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback)
    {
        OSS oss = null;
        String url = null;
        if (oss == null) {
            BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, filePath+"@info", progressCallback).invoke();
            oss = buildPutObjectRequest.getOss();
        }
        try {
            url = oss.presignConstrainedObjectURL(initOSSResponse.getBucket(), filePath+"@info", OSS_EXPIRE);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        url = url.replaceFirst("oss", "img");

        return url;
    }
    /**
     * 获取动态列表中图片的url
     */
    public static String getMineDynamicUrl(Context context, InitOssResponse initOSSResponse, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) {
        OSS oss = null;
        String url = null;
        if (oss == null) {
            BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, filePath, progressCallback).invoke();
            oss = buildPutObjectRequest.getOss();
        }
        try {
            url = oss.presignConstrainedObjectURL(initOSSResponse.getBucket(), filePath, OSS_EXPIRE);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        url = url.replaceFirst("oss", "img");
        return url;
    }


    public static OSSAsyncTask putObjectAsync(Context context, InitOssResponse initOSSResponse, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback,
                                              OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {
        BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, filePath, progressCallback).invoke();
        OSS oss = buildPutObjectRequest.getOss();
        PutObjectRequest request = buildPutObjectRequest.getRequest();
        return oss.asyncPutObject(request, ossCompletedCallback);
    }

    public static OSSAsyncTask putObjectUseDefaultOssAsync(Context context, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback,
                                                           OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {
        if (null == mDefaultinitOSSResponse) {
            throw new IllegalStateException("请初始化Oss");
        }
        BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, mDefaultinitOSSResponse, filePath, progressCallback).invoke();
        OSS oss = buildPutObjectRequest.getOss();
        PutObjectRequest request = buildPutObjectRequest.getRequest();
        return oss.asyncPutObject(request, ossCompletedCallback);
    }

    /**
     * 上传一个文件（上传至默认位置）
     * @param context
     * @param initOSSResponse
     * @param filePath
     * @param progressCallback
     * @return
     * @throws ClientException
     * @throws ServiceException
     */
    public static PutObjectResult putObject(Context context, InitOssResponse initOSSResponse, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) throws ClientException, ServiceException {
        BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, filePath, progressCallback).invoke();
        OSS oss = buildPutObjectRequest.getOss();
        PutObjectRequest request = buildPutObjectRequest.getRequest();
        return oss.putObject(request);
    }

    /**
     * 上传一个文件（上传至 testobj 指定的位置）
     * @param context
     * @param initOSSResponse
     * @param testobj
     * @param filePath
     * @param progressCallback
     * @return
     * @throws ClientException
     * @throws ServiceException
     */

    public static PutObjectResult putObject(Context context, InitOssResponse initOSSResponse, String testobj, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) throws ClientException, ServiceException {
        BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, testobj, filePath, progressCallback).invoke();
        OSS oss = buildPutObjectRequest.getOss();
        PutObjectRequest request = buildPutObjectRequest.getRequest();
        return oss.putObject(request);
    }


    /**
     * 上传文件，使用默认的Oss
     *
     * @param context
     * @param filePath
     * @param progressCallback
     * @return
     * @throws ClientException
     * @throws ServiceException
     */
    public static PutObjectResult putObjectUseDefaultOss(Context context, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) throws ClientException, ServiceException {
        if (null == mDefaultinitOSSResponse) {
            throw new IllegalStateException("请初始化Oss");
        }
        BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, mDefaultinitOSSResponse, filePath, progressCallback).invoke();
        OSS oss = buildPutObjectRequest.getOss();
        PutObjectRequest request = buildPutObjectRequest.getRequest();
        return oss.putObject(request);
    }


    public static PutObjectResult putObject(Context context, InitOssResponse initOSSResponse, byte[] data, String fileName) throws ClientException, ServiceException {
        BuildPutObjectRequest buildPutObjectRequest = new BuildPutObjectRequest(context, initOSSResponse, fileName, data).invoke();
        OSS oss = buildPutObjectRequest.getOss();
        PutObjectRequest request = buildPutObjectRequest.getRequest();
        return oss.putObject(request);
    }



    public static class BuildPutObjectRequest {
        private Context context;
        private InitOssResponse initOSSResponse;
        private String filePath;
        private String fileName;
        private byte[] data;
        private OSSProgressCallback<PutObjectRequest> progressCallback;
        private OSS oss;
        private PutObjectRequest request;
        private String testobj;

        public BuildPutObjectRequest(Context context, InitOssResponse initOSSResponse, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) {
            this.context = context;
            this.initOSSResponse = initOSSResponse;
            this.filePath = filePath;
            this.progressCallback = progressCallback;
        }

        public BuildPutObjectRequest(Context context, InitOssResponse initOSSResponse, String fileName, byte[] data) {
            this.context = context;
            this.initOSSResponse = initOSSResponse;
            this.fileName = fileName;
            this.data = data;
        }


        public BuildPutObjectRequest(Context context, InitOssResponse initOSSResponse, String testobj, String filePath, OSSProgressCallback<PutObjectRequest> progressCallback) {
            this.context = context;
            this.testobj = testobj;
            this.initOSSResponse = initOSSResponse;
            this.filePath = filePath;

            this.progressCallback = progressCallback;
        }

        public OSS getOss() {
            return oss;
        }

        public PutObjectRequest getRequest() {
            return request;
        }

        public BuildPutObjectRequest invoke() {
            String callbackBody = initOSSResponse.getCallback_body();
            String decodedString = new String(Base64.decode(callbackBody.getBytes(), Base64.DEFAULT));
            CallBackBody callBackBody = new Gson().fromJson(decodedString, CallBackBody.class);
            // 鉴权
            //OSSCredentialProvider


            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(initOSSResponse.getAccess_key_id(), initOSSResponse.getAccess_key_secret(), initOSSResponse.getSecurity_token());
            oss = new OSSClient(context, initOSSResponse.getDomain(), credentialProvider);
            if (!TextUtils.isEmpty(filePath) && TextUtils.isEmpty(testobj)) {
                String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                final String file = initOSSResponse.getObject_path() + fileName;
                request = new PutObjectRequest(initOSSResponse.getBucket(), file, filePath);
            }
            else if (!TextUtils.isEmpty(filePath) && !TextUtils.isEmpty(testobj)) {
                request = new PutObjectRequest(initOSSResponse.getBucket(), initOSSResponse.getObject_path()+testobj, filePath);
            } else {

                request = new PutObjectRequest(initOSSResponse.getBucket(), initOSSResponse.getObject_path() + fileName, data);
            }

            Map<String, String> callbackVars = new HashMap<>();
            callbackVars.put("callbackUrl", callBackBody.callbackUrl);
            callbackVars.put("callbackHost", callBackBody.callbackHost);
            callbackVars.put("callbackBody", callBackBody.callbackBody);
            callbackVars.put("callbackBodyType", callBackBody.callbackBodyType);
            request.setCallbackParam(callbackVars);
            callbackVars = new HashMap<>();
            callbackVars.put("x:access_token", initOSSResponse.getCallback_token());
            request.setCallbackVars(callbackVars);

            // 异步上传时可以设置进度回调
            if (progressCallback != null) {
                request.setProgressCallback(progressCallback);
            }
            return this;
        }
    }
}
