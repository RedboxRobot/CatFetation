package com.unlimiteduniverse.http.interceptor;

import com.unlimiteduniverse.common.utils.NetworkUtils;
import com.unlimiteduniverse.http.HttpLibrary;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Irvin
 * on 2017/8/30.
 */

public class LocalCacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected(HttpLibrary.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);

        if (NetworkUtils.isConnected(HttpLibrary.getContext())) {
            /**
             * If you have problems in testing on which side is problem (server or app).
             * You can use such feature to set headers received from server.
             */
            int maxAge = 0 * 60; // 有网络时,设置缓存超时时间0个小时
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)//设置缓存超时时间
                    .build();
        } else {
            int maxStale = 60 * 60 * 24;// 无网络时，设置超时为1天
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }

}
