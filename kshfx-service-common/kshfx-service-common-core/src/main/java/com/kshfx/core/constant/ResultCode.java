package com.kshfx.core.constant;

public enum ResultCode {

    _200(200, "请求成功"),
    _500(500, "服务错误"),
    _501(501, "服务器繁忙"),
    _502(502, "接口访问失效"),
    _503(503, "接口签名错误"),
    _504(504, "接口签名错误"),
    _505(505, "页面请求中有违反安全规则元素存在，拒绝访问"),
    _506(506, "访问过于频繁，请您稍后重试")
    ;

    public int code;

    private String message;

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
