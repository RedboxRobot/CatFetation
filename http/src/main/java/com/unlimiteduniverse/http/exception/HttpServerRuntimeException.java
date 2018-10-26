package com.unlimiteduniverse.http.exception;

import com.unlimiteduniverse.http.enums.HttpServerExceptionCodeEnum;

public class HttpServerRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 2111168707647502165L;
    // 已捕获异常
    private HttpServerExceptionCodeEnum exceptionCodeEnum;

    // 第三方异常代码
    private String no;

    // 第三方des
    private String des;

    public HttpServerRuntimeException(HttpServerExceptionCodeEnum exceptionCodeEnum) {
        super(exceptionCodeEnum.getDes());
        this.exceptionCodeEnum = exceptionCodeEnum;
    }

    public HttpServerRuntimeException(HttpServerExceptionCodeEnum exceptionCodeEnum, Throwable e) {
        super(exceptionCodeEnum.getDes(),e);
        this.exceptionCodeEnum = exceptionCodeEnum;
    }

    public HttpServerRuntimeException(String no, String des) {
        super(des);
        this.no = no;
        this.des = des;
    }
    public HttpServerRuntimeException(String no, String des, Throwable e) {
        super(des,e);
        this.no = no;
        this.des = des;
    }

    public HttpServerExceptionCodeEnum getExceptionCodeEnum() {
        return exceptionCodeEnum;
    }

    public String getNo() {
        return no;
    }

    public String getDes() {
        return des;
    }
}