package com.unlimiteduniverse.http.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.unlimiteduniverse.common.sysutils.PackagesUtils;
import com.unlimiteduniverse.common.sysutils.SHAUtils;
import com.unlimiteduniverse.common.sysutils.SysInfoUtil;
import com.unlimiteduniverse.http.HttpLibrary;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * BaseInterceptor
 * Created by Irvin on 2017-8-15.
 */
public class RequestInterceptor implements Interceptor {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String SELF_HEADER = "Dauth";
    private static final String DUAGENT = "Duagent";
    private static final String DUAGENT_VALUE = "_app";
    private static final String USER_AGENT = "User-Agent";

    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String JSON_DATA = "application/json;charset=UTF-8";
    private static final String MEDIA_TYPE_PNG = "image/png";

    private static final Charset UTF8 = Charset.forName("UTF-8");


    private boolean isMultiPartRequest;

    public RequestInterceptor(boolean isMultiPartRequest) {
        this.isMultiPartRequest = isMultiPartRequest;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        //获取请求方法
        String method = original.method();
//                                    // try the request
//                                    Response originalResponse = chain.proceed(original);

//                                    ResponseBody responseBody = originalResponse.body();
//                                    BufferedSource source = responseBody.source();
//                                    source.request(Long.MAX_VALUE); // Buffer the entire body.
//                                    Buffer buffer = source.buffer();
//                                    Charset charset = UTF8;
//                                    MediaType contentType = responseBody.contentType();
//                                    if (contentType != null) {
//                                        charset = contentType.charset(UTF8);
//                                    }
//                                    String bodyString = buffer.clone().readString(charset);
        Map<String, String> headersParams = new HashMap<>();
        headersParams.put(CONTENT_TYPE, "application/json;charset=UTF-8");
        /**
         *解决后台很久再打开连不上网的坑
         */
        headersParams.put("Connection", "close");

        Request request;
        switch (method) {
            case "POST":
//                headersParams.put(SELF_HEADER, addDauthHeader(original.body(),
//                        getServiceToken(), getServiceUser()));

                Log.e("Irvin", "isMultiPartRequest:" + isMultiPartRequest);
                if (isMultiPartRequest) {
                    headersParams.put(SELF_HEADER, addDauthHeader("",
                            getServiceToken(), getServiceUser(), String.valueOf(System.currentTimeMillis())));
                    headersParams.put(DUAGENT, DUAGENT_VALUE);
                    headersParams.put("Connection", "close");
                    headersParams.put(USER_AGENT, HttpLibrary.getContext().getString(com.unlimiteduniverse.common.R.string.english_name)
                            + "/" + PackagesUtils.getInstance(HttpLibrary.getContext()).getVersionName() + " (" + SysInfoUtil.getPhoneMode() + ";Android" + SysInfoUtil.getOsInfo() + ");");
                    request = original.newBuilder()
                            //设置requestBody的编码格式为json
                            .headers(setHeaders(headersParams))
                            .post(original.body())
                            .build();
                } else {
                    headersParams.put(SELF_HEADER, addDauthHeader(original.body(),
                            getServiceToken(), getServiceUser(), String.valueOf(System.currentTimeMillis())));
                    headersParams.put(DUAGENT, DUAGENT_VALUE);
                    headersParams.put("Connection", "close");
                    headersParams.put(USER_AGENT, HttpLibrary.getContext().getString(com.unlimiteduniverse.common.R.string.english_name)
                            + "/" + PackagesUtils.getInstance(HttpLibrary.getContext()).getVersionName() + " (" + SysInfoUtil.getPhoneMode() + ";Android" + SysInfoUtil.getOsInfo() + ");");
                    request = original.newBuilder()
                            //设置requestBody的编码格式为json
                            .headers(setHeaders(headersParams))
                            .post(RequestBody.create(MediaType.parse(JSON_DATA),
                                    bodyToString(original.body())))
                            .build();
                }
                break;
            case "GET":
                //获取到请求地址api
                HttpUrl httpUrl = original.url();
                //通过请求地址(最初始的请求地址)获取到参数列表
                Set<String> parameterNames = httpUrl.queryParameterNames();
                Log.e("Irvin", "parameterNames size:" + parameterNames.size());
                StringBuilder resultParameter = new StringBuilder();
                for (String key : parameterNames) {
                    Log.e("Irvin", "key:" + key);
                    resultParameter.append(key);
                }
                Log.e("Irvin", "resultParameter:" + resultParameter.toString());

                headersParams.put(SELF_HEADER, addDauthHeader(resultParameter.toString(),
                        getServiceToken(), getServiceUser(), String.valueOf(System.currentTimeMillis())));
                headersParams.put(DUAGENT, DUAGENT_VALUE);
                /**
                 *解决后台很久再打开连不上网的坑
                 */
                headersParams.put("Connection", "close");
                headersParams.put(USER_AGENT, HttpLibrary.getContext().getString(com.unlimiteduniverse.common.R.string.english_name)
                        + "/" + PackagesUtils.getInstance(HttpLibrary.getContext()).getVersionName() + " (" + SysInfoUtil.getPhoneMode() + ";Android" + SysInfoUtil.getOsInfo() + ");");
                request = original.newBuilder()
                        .headers(setHeaders(headersParams))
                        .build();
                break;
            default:
                request = original.newBuilder()
                        .headers(setHeaders(headersParams))
                        .build();
                break;
        }
        return chain.proceed(request);
    }

    /**
     * 设置请求头
     *
     * @param headersParams
     * @return
     */
    private Headers setHeaders(Map<String, String> headersParams) {
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

    /**
     * post请求参数
     *
     * @param BodyParams
     * @return
     */
    private RequestBody setRequestBody(Map<String, String> BodyParams) {
        RequestBody body;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                formEncodingBuilder.add(key, BodyParams.get(key));
                Log.d("post http", "post_Params===" + key + "====" + BodyParams.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;

    }

    /**
     * Post上传图片的参数
     *
     * @param BodyParams
     * @param fileParams
     * @return
     */
    private RequestBody setFileRequestBody(Map<String, String> BodyParams, Map<String, String> fileParams) {
        //带文件的Post参数
        RequestBody body;
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        RequestBody fileBody;

        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                multipartBodyBuilder.addFormDataPart(key, BodyParams.get(key));
                Log.d("post http", "post_Params===" + key + "====" + BodyParams.get(key));
            }
        }

        if (fileParams != null) {
            Iterator<String> iterator = fileParams.keySet().iterator();
            String key;
            int i = 0;
            while (iterator.hasNext()) {
                key = iterator.next();
                i++;
                multipartBodyBuilder.addFormDataPart(key, fileParams.get(key));
                Log.d("post http", "post_Params===" + key + "====" + fileParams.get(key));
                fileBody = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), new File(fileParams.get(key)));
                multipartBodyBuilder.addFormDataPart(key, i + ".png", fileBody);
            }
        }

        body = multipartBodyBuilder.build();
        return body;

    }

    private static String getServiceToken() {
        return "";
    }

    private static String getServiceUser() {
        return "";
    }

/*    private static String addDauthHeader(final RequestBody request, String token, String userId) {
        String encodeStr = //getRequestBodyStr(request) +
                (userId == null ? "" : userId)
                + (token == null ? "" : token);
        Log.e("Irvin", "encodeStr:" + encodeStr);
        encodeStr = encodeStr.replaceAll(" ", "");
        if (TextUtils.isEmpty(encodeStr)) {
            return SHAUtils.SHA256(String.valueOf(System.currentTimeMillis()));
        }
        String hash = SHAUtils.SHA256(encodeStr);
        hash =  hash.length() > 32 ? hash.substring(0, 32) : hash;

        if (TextUtils.isEmpty(userId)) {
            return System.currentTimeMillis() + " " + hash;
        }
        return userId + " " + System.currentTimeMillis() + " " + hash;
    }*/

    private static String addDauthHeader(final RequestBody request, String token, String userId, String timeStamp) {
        String encodeStr = //getRequestBodyStr(request) +
                (userId == null ? "" : userId)
                        + (token == null ? "" : token) + timeStamp;
        Log.e("Irvin", "encodeStr:" + encodeStr);
        encodeStr = encodeStr.replaceAll(" ", "");
        String hash = SHAUtils.SHA256(encodeStr);
        hash = hash.length() > 32 ? hash.substring(0, 32) : hash;

        if (TextUtils.isEmpty(userId)) {
            return timeStamp + " " + hash;
        }
        return userId + " " + timeStamp + " " + hash;
    }

/*    private static String addDauthHeader(String requestParameter, String token, String userId) {
        String encodeStr = //requestParameter +
                (userId == null ? "" : userId)
                + (token == null ? "" : token);
        Log.e("Irvin", "encodeStr:" + encodeStr);
        encodeStr = encodeStr.replaceAll(" ", "");
        if (TextUtils.isEmpty(encodeStr)) {
            return String.valueOf(System.currentTimeMillis());
        }
        String hash = SHAUtils.SHA256(encodeStr);
        hash =  hash.length() > 32 ? hash.substring(0, 32) : hash;

        if (TextUtils.isEmpty(userId)) {
            return System.currentTimeMillis() + " " + hash;
        }
        return userId + " " + System.currentTimeMillis() + " " + hash;
    }*/

    private static String addDauthHeader(String requestParameter, String token, String userId, String timeStamp) {
        String encodeStr = //requestParameter +
                (userId == null ? "" : userId)
                        + (token == null ? "" : token) + timeStamp;
        Log.e("Irvin", "encodeStr:" + encodeStr);
        encodeStr = encodeStr.replaceAll(" ", "");

        String hash = SHAUtils.SHA256(encodeStr);
        hash = hash.length() > 32 ? hash.substring(0, 32) : hash;

        if (TextUtils.isEmpty(userId)) {
            return timeStamp + " " + hash;
        }
        return userId + " " + timeStamp + " " + hash;
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

    private static String getRequestBodyStr(final RequestBody requestBody) {
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Charset charset = UTF8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            contentType.charset(UTF8);
        }

        return buffer.readString(charset);

    }
}
