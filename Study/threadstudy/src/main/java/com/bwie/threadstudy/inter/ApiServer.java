package com.bwie.threadstudy.inter;

import com.bwie.threadstudy.bean.VersionBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/11/16
 */

public interface ApiServer {
    /*
    https://www.zhaoapi.cn/version/getVersion
     */
    @GET("getVersion")
    Flowable<VersionBean> getVersionData(@Query("type") String type);
}
