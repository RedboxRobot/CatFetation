package com.unlimiteduniverse.http.callback;

import android.text.TextUtils;
import android.util.Log;

import com.delicloud.app.common.utils.tool.GsonHelper;
import com.delicloud.app.http.enums.HttpServerExceptionCodeEnum;
import com.delicloud.app.http.exception.HttpServerRuntimeException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by wangjitao on 15/10/16.
 * 抽象类，用于请求成功后的回调
 */
public abstract class ResultCallback<T> implements Callback {
    private static final String TAG = ResultCallback.class.getSimpleName();
    //这是请求数据的返回类型，包含常见的（Bean，List等）
    Class<T> entityClass;

    public ResultCallback() {
        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        if (types != null && types.length > 0) {
            entityClass = (Class<T>) types[0];
        } else {
            entityClass = null;
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        onError(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            final String responseMessage = response.message();

            String body = null;
            RequestBody requestBody = call.request().body();
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    contentType.charset(Charset.forName("UTF-8"));
                }

                body = buffer.readString(charset);
            }
            final String responseBody = response.body().string();
            Log.d(TAG, "请求类型：\n" + call.request().method() +
                    "\n 请求地址：\n" + call.request().url().toString() +
                    "\n 请求头：\n" + call.request().headers().toString() +
                    "\n 请求参数 \n" + (TextUtils.isEmpty(body) ? GsonHelper.GsonString(call.request().body()) : body) +
                    "\n,返回结果：\n" + responseBody);
            if (response.code() != 200) {
                Exception exception = new Exception(response.code() + ":" + responseMessage);
                onFail(response, exception);
                return;
            }
            if (entityClass == null) {
                onSuccess(null);
                return;
            }
            T o = (T) GsonHelper.GsonToBean(responseBody, entityClass);
            if (o == null) {
                onFail(response, new HttpServerRuntimeException(HttpServerExceptionCodeEnum.PARSE_ERROR));
                return;
            }
            onSuccess(o);
        } catch (IOException | com.google.gson.JsonParseException e) {
            onError(e);
        }
    }

    /**
     * 请求失败的时候
     *
     * @param e
     */
    public abstract void onError(Exception e);

    /**
     * @param response
     */
    public abstract void onFail(Response response, Exception e);

    /**
     *
     */
    public abstract void onSuccess(T result);
}