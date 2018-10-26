package com.unlimiteduniverse.http.enums;

public enum HttpServerExceptionCodeEnum {
    REQUEST_ERROR("-1", "请求错误"),
    NOT_FOUND("404", "未找到"),
    REQUEST_TIMEOUT("408", "请求超时"),
    INTERNAL_SERVER_ERROR("500", "服务器错误"),

    PARSE_ERROR("10000", "数据解析错误"),

    ;
     private String no;
    private String des;

    private HttpServerExceptionCodeEnum(String no, String des) {
        this.no = no;
        this.des = des;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static HttpServerExceptionCodeEnum getByCode(String no) {
        if(no == null) {
            return null;
        } else {
            HttpServerExceptionCodeEnum[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                HttpServerExceptionCodeEnum m = var1[var3];
                if(m.getNo().equals(no)) {
                    return m;
                }
            }

            return null;
        }
    }
}
