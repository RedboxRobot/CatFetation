package com.unlimiteduniverse.cat.fetation;

import android.app.Application;

import com.unlimiteduniverse.cat.fetation.dao.DaoHelper;
import com.unlimiteduniverse.http.HttpLibrary;

/**
 * @author Irvin
 * @time 2018/10/31 0031
 */
public class CatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //网络请求模块初始化
        HttpLibrary.init(this);
        //green dao 初始化
        DaoHelper.init(this);
    }
}
