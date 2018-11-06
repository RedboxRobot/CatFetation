package com.unlimiteduniverse.cat.fetation;

import android.app.Application;

import com.unlimiteduniverse.http.HttpLibrary;

/**
 * @author Irvin
 * @time 2018/10/31 0031
 */
public class CatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLibrary.init(this);
    }
}
