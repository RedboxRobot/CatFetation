package com.unlimiteduniverse.http.base;

import android.util.Log;

import com.unlimiteduniverse.common.utils.GsonHelper;
import com.unlimiteduniverse.http.callback.ResultCallback;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class BaseOkHttpService {
    private static final String TAG = BaseOkHttpService.class.getSimpleName();
    /**
     * 打印机端口
     */
    public static int READ_TIMEOUT = 60;
    public static int WRITE_TIMEOUT = 30;
    public static int CONNECT_TIMEOUT = 30;

    private static OkHttpClient client;

    public static OkHttpClient getInstance() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间  ;
                    .build();
        }
        return client;
    }

    public static void getAsyn(String url, Map<String, String> params, ResultCallback resultCallback) {
        String result = "";
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            if (url.indexOf("?") != -1) {
                url += "&" + getRequestParam(params);
            } else {
                url += "?" + getRequestParam(params);
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            getInstance().newCall(request).enqueue(resultCallback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void getAsyn(String url, Map<String, String> params, Map<String, String> headers, ResultCallback resultCallback) {
        String result = "";
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            if (url.indexOf("?") != -1) {
                url += "&" + getRequestParam(params);
            } else {
                url += "?" + getRequestParam(params);
            }
        }
        Request.Builder build = new Request.Builder();

        build.headers(setHeaders(headers))
             .url(url)
             .get();
        Request request = build.build();
        try {
            getInstance().newCall(request).enqueue(resultCallback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static Headers setHeaders(Map<String, String> headersParams) {
        Headers headers;
        Headers.Builder headersBuilder = new Headers.Builder();

        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                headersBuilder.add(key, headersParams.get(key));
                Log.d("get http", "get_headers ==》 " + key + "==》" + headersParams.get(key));
            }
        }
        headers = headersBuilder.build();

        return headers;
    }

    public static void postAsync(String url, Map<String, String> params, ResultCallback resultCallback) {
        RequestBody originBody = new FormBody.Builder().build();

        if (params != null) {
            originBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                    GsonHelper.toJson(params));
        }

        Request request = new Request.Builder()
                .url(url)
                .post(originBody)
                .build();

        try {
            getInstance().newCall(request).enqueue(resultCallback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void postAsync(String url, Map<String, String> params, Map<String, String> headers, ResultCallback resultCallback) {
        RequestBody originBody = new FormBody.Builder().build();

        if (params != null) {
            originBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),
                    GsonHelper.toJson(params));
        }

        Request.Builder build = new Request.Builder();
        if (headers != null) {
            build.headers(setHeaders(headers));
        }

        Request request = build.url(url)
                .post(originBody)
                .build();

        try {
            getInstance().newCall(request).enqueue(resultCallback);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static String get(String url, Map<String, String> params) throws Exception {
        String result = "";
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            if (url.indexOf("?") != -1) {
                url += "&" + getRequestParam(params);
            } else {
                url += "?" + getRequestParam(params);
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = null;
        try {
            response = getInstance().newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
        return result;
    }


    public static String post(String url, Map<String, String> params) throws Exception {
        String result = "";
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        Response response = null;
        try {
            response = getInstance().newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            throw e;
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
        return result;
    }

    /**
     * Map转请求参数
     *
     * @param params
     * @return
     */
    public static String getRequestParam(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        String symbol = null;
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (symbol != null) {
                    sb.append(symbol);
                } else {
                    symbol = "&";
                }
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return sb.toString();
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}