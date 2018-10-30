package com.unlimiteduniverse.http.utils;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by Irvin
 * on 2017/8/29 0029.
 */

public class ExceptionHandler {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORK_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ResponseThrowable(e, ERROR.HOST_ERROR);
            ex.message = "无法解析主机名";
            return ex;
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            ex.message = "请求超时";
            return ex;
        }
    }

    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
        /**
         * 无法解析主机名
         */
        public static final int HOST_ERROR = 1006;

    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
            switch (this.code){
                case ERROR.PARSE_ERROR:
                    message = "解析错误";
                    break;
                case ERROR.NETWORK_ERROR:
                    message = "连接错误";
                    break;
                case ERROR.HTTP_ERROR:
                    message = "网络出错";
                    break;
                case ERROR.SSL_ERROR:
                    message = "证书验证失败";
                    break;
                case ERROR.UNKNOWN:
                    message = "请求超时";
                    break;
                case ERROR.HOST_ERROR:
                    message = "网络不佳";
                    break;
                default:
                    message = "网络错误";
                    break;

            }
        }

    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }

    public static class GivenMessageException extends RuntimeException {
        private String code;
        private String message;
        private String exMessage;

        public GivenMessageException(String code, String message, String exMessage) {
            this.code = code;
            this.message = message;
            this.exMessage = exMessage;
        }

        public GivenMessageException(ResponseThrowable rt) {
            this.code = String.valueOf(rt.code);
            this.message = rt.message;
            this.exMessage = rt.message;
        }

        public String getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public String getExMessage() {
            return exMessage;
        }
    }
}
