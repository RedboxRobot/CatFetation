package com.unlimiteduniverse.http.download;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unlimiteduniverse.http.model.FileResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Irvin
 * on 2017/7/23
 */

public class FileDownFactory {

    private volatile static FileDownFactory INSTANCE;
    private static final String GLOBAL_BASE_URL = "";

    /**
     * 获取单例
     */
    public static FileDownFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (FileDownFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FileDownFactory();
                }
            }
        }
        return INSTANCE;
    }

    public Observable<ResponseBody> down(String url) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(8000, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse
                                .newBuilder()
                                .body(new FileResponseBody(originalResponse))//将自定义的ResposeBody设置给它
                                .build();
                    }
                })
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(GLOBAL_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        DownloadAPI api = retrofit.create(DownloadAPI.class);
        return api.down(url);
    }
}
