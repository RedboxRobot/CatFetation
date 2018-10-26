package com.unlimiteduniverse.http.base;

import java.io.Serializable;

/**
 * 回调信息统一封装类
 * Created by Irvin on 2017/8/21 0021.
 */

public class BaseResponse<T> implements Serializable {
    private static final String OK = "0";
    /**
     * 判断标示
     * 200 成功
     * 其他失敗
     **/
    public String code;

    //    提示信息
    public String msg;

    // log信息
    public String ex_msg;

    //显示数据（用户需要关心的数据）
    public T data;

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse() {
    }

    public BaseResponse(String code, String msg, String ex_msg) {
        this.code = code;
        this.msg = msg;
        this.ex_msg = ex_msg;
    }

    public boolean isSuccess() {
        return code.equals(OK);
    }
}
