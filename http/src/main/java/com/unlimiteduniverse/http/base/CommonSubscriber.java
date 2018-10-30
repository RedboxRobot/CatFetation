package com.unlimiteduniverse.http.base;

import android.content.Context;

import com.unlimiteduniverse.http.utils.ExceptionHandler;


/**
 * BaseSubscriber
 * Created by Irvin on 2017-8-15.
 */
public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    public CommonSubscriber(Context context, boolean isShowProgress) {
        super(context, isShowProgress);
    }

    //特使错误处理 如果实现此方法，返回true 则表示走公共错误处理机制，false 则只实现自己的方法
    @Override
    protected  boolean onSpecialErrorHandle(ExceptionHandler.GivenMessageException gme){
        return true;
    }
}
