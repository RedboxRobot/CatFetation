package com.unlimiteduniverse.http.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author ChengXinPing
 * @time 2018/1/22 14:51
 */

public interface DownloadAPI {

    /**
     * 下载文件
     *
     * @param url url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> down(@Url String url);
}
