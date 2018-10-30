package com.unlimiteduniverse.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: Irvin
 * date: 2017/8/27
 * desc: GsonHelper工具类
 */

public class GsonHelper {
    private static Object obj = null;
    private static Gson gson;

    //序列化所有注解的字段
    public static Gson getInstance() {
        synchronized (GsonHelper.class) {
            if (gson == null) {
                gson = gsonWithDate();
            }
        }
        return gson;
    }

    public static Gson gsonWithDate(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Long date = json.getAsJsonPrimitive().getAsLong();
                if(date == null){
                    return null;
                }
                return new Date(json.getAsLong());
            }
        });


        return builder.create();
    }
    /**
     * @param jsonStr
     * @param clazz
     * Title: parseJson
     * Description: json 转成对象
     */
    public static Object parseJson(String jsonStr, Class clazz) {
        gson = getInstance();
        obj = gson.fromJson(jsonStr, clazz);
        return obj;
    }

    /**
     * @param jsonElement
     * @param clazz
     * Title: parseJson
     * Description: json 转成对象
     */
    public static Object parseJson(JsonElement jsonElement, Class clazz) {
        gson = getInstance();
        obj = gson.fromJson(jsonElement, clazz);
        return obj;
    }

    /**
     * @param obj
     * Title: toJson
     * Description: 对象转成json
     */
    public static String toJson(Object obj) {
        String strJson;
        gson = getInstance();
        strJson = gson.toJson(obj);
        return strJson;
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        gson = getInstance();
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        gson = getInstance();
        T t = null;
        if (gson != null) {
            try{
                t = gson.fromJson(gsonString, cls);
            }catch (JsonSyntaxException e){
                e.printStackTrace();
            }
        }
        return t;
    }

    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        gson = getInstance();
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) {
        gson = getInstance();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }





}
