package com.unlimiteduniverse.http;

import android.content.Context;

/**
 * Created by Irvin
 * on 2018/1/5 0005.
 */

public class HttpLibrary {

    // context
    private static Context context;

    public static Context getContext() {
        return context;
    }

    /*
 * ****************************** 初始化 ******************************
 */
    public static void init(Context context) {
        HttpLibrary.context = context.getApplicationContext();
    }
}
