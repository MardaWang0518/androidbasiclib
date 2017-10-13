package com.hxh.component.business.utils.oss.api;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import com.hxh.component.business.utils.oss.request.Request_GetOssInfo;
import com.hxh.component.business.utils.oss.response.InitOssResponse;

/**
 * Created by hxh on 2017/5/26.
 */
public interface OssApiService {
    /**
     * 获取OSs初始化信息
     * @return
     */

    @POST("/filemeta/v1/inits")
    Observable<InitOssResponse> getOssInitInfo(@Body Request_GetOssInfo body);
}
