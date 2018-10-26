package com.unlimiteduniverse.http;

import android.text.TextUtils;
import android.util.Log;

import com.delicloud.app.common.utils.tool.GsonHelper;
import com.delicloud.app.http.interceptor.HttpLoggingInterceptor;
import com.delicloud.app.http.interceptor.LocalCacheInterceptor;
import com.delicloud.app.http.interceptor.RequestInterceptor;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Irvin
 * on 2017/7/23
 */

public class RetrofitFactory {
    private static final String GLOBAL_BASE_URL = "http://192.168.0.202:9001/v1.0/";

    public static String getGlobalBaseUrl() {
        return GLOBAL_BASE_URL;
    }

    private static Map<Class, Retrofit> sRetrofitCache = new HashMap<>();
    private static Map<Class, Retrofit> sRetrofitCacheMP = new HashMap<>();
    private static Map<Class, String> sCachedUrl = new HashMap<>();

    private static class SingleOnHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    public static RetrofitFactory getInstance() {
        return SingleOnHolder.INSTANCE;
    }

    /**
     * 会优先读取每个Service中配置的BASE_URL
     * 若没有配置则使用全局的BASE_URL
     *
     * @param serviceClass
     * @param isMultiPart
     * @param <S>
     * @return
     */
    public <S> S createService(Class<S> serviceClass, boolean isMultiPart) {
        Retrofit retrofit = getRetrofitFromCache(serviceClass, isMultiPart);
        String baseUrl = "";
        try {
            Field field = serviceClass.getField("BASE_URL");
            try {
                baseUrl = (String) field.get(field);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = GLOBAL_BASE_URL;
        }
        sCachedUrl.put(serviceClass, baseUrl);

        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                retrofit = getRetrofitFromCache(serviceClass, isMultiPart);
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .client(getOkHttp(isMultiPart))
                            .addConverterFactory(GsonConverterFactory.create(GsonHelper.gsonWithDate()))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
            if (isMultiPart) {
                sRetrofitCacheMP.put(serviceClass, retrofit);
            } else {
                sRetrofitCache.put(serviceClass, retrofit);
            }
        }
        return retrofit.create(serviceClass);
    }

    /**
     * 把每一个Service对应的Retrofit放入缓存,避免频繁创建
     *
     * @param serviceClass 需要缓存的Service
     * @param isMultiPart  是否是MultiPart请求
     * @return Retrofit
     */
    private Retrofit getRetrofitFromCache(Class serviceClass, boolean isMultiPart) {
        Retrofit retrofit = null;
        if (isMultiPart) {
            if (sRetrofitCacheMP.containsKey(serviceClass)) {
                retrofit = sRetrofitCacheMP.get(serviceClass);
            }
        } else {
            if (sRetrofitCache.containsKey(serviceClass)) {
                retrofit = sRetrofitCache.get(serviceClass);
            }
        }
        return retrofit;
    }

    public static String getCachedUrl(Class serviceClass) {
        String result = sCachedUrl.get(serviceClass);
        return result == null ? GLOBAL_BASE_URL : result;
    }

    /**
     * 默认超时时间 单位/秒
     */
    private final static long DEFAULT_TIMEOUT = 30;

    //设置缓存目录
    private static File cacheDirectory = new File(HttpLibrary.getContext()
            .getCacheDir().getAbsolutePath(), "DeliAssistantCache");
    private static Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);

    private OkHttpClient getOkHttp(boolean isMultiPart) {
        OkHttpClient.Builder okHttp = new OkHttpClient.Builder();
        okHttp.addInterceptor(new RequestInterceptor(isMultiPart));
        okHttp.addInterceptor(
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i("OkHttp", message);
                    }
                }
                ).setLevel(HttpLoggingInterceptor.Level.HEADERS)
                        .setLevel(HttpLoggingInterceptor.Level.BODY));
        //离线缓存
        okHttp.addInterceptor(new LocalCacheInterceptor());
        okHttp.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttp.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttp.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttp.cache(cache);
        return okHttp.build();
    }
}
