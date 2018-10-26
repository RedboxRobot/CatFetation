package com.unlimiteduniverse.http.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Irvin
 * on 2017/11/20 0020.
 */

public class MultiPartUtils {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String MEDIA_TYPE_PNG = "image/png";

    public static Map<String, RequestBody> putRequestBodyMap(Map<String, String> map) {
        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(map != null){
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            String key;
            String value;
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                key = entry.getKey();
                value =  entry.getValue();
                putRequestBodyMap(bodyMap, key, createPartFromString(value));
            }
        }
        return bodyMap;
    }

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        if (descriptionString == null) {
            descriptionString = "";
        }
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    public static void putRequestBodyMap(Map<String, RequestBody> map, String key, RequestBody body) {
        if (!TextUtils.isEmpty(key) && body != null) {
            map.put(key, body);
        }
    }

    public static MultipartBody.Part prepareFilePart(String partName, String fileUri) {
        File file = new File(fileUri);
        // 为file建立RequestBody实例
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        // MultipartBody.Part借助文件名完成最终的上传
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
