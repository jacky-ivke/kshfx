package com.kshfx.core.utils;

import com.kshfx.core.constant.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class JsonWrapper implements Serializable {

    private int code;

    private boolean success;

    private String msg;

    private Object data;

    public JsonWrapper(boolean success, int code, String msg, Object data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonWrapper success() {
        JsonWrapper wrapper = new JsonWrapper(Boolean.TRUE, ResultCode._200.getCode(), "成功", null);
        return wrapper;
    }

    public static <T> JsonWrapper success(String msg) {
        JsonWrapper wrapper = new JsonWrapper(Boolean.TRUE, ResultCode._200.getCode(), msg, null);
        return wrapper;
    }

    public static <T> JsonWrapper success(Object data) {
        JsonWrapper wrapper = new JsonWrapper(Boolean.TRUE, ResultCode._200.getCode(), "成功", data);
        return wrapper;
    }

    public static <T> JsonWrapper success(String msg, Object data) {
        JsonWrapper wrapper = new JsonWrapper(Boolean.TRUE, ResultCode._200.getCode(), msg, data);
        return wrapper;
    }

    public static <T> JsonWrapper error(String msg) {
        JsonWrapper wrapper = new JsonWrapper(Boolean.FALSE, ResultCode._500.getCode(), msg, null);
        return wrapper;
    }

    public static <T> JsonWrapper error(int code) {
        JsonWrapper wrapper = new JsonWrapper(Boolean.FALSE, code, "操作失败", null);
        return wrapper;
    }

    public static <T> JsonWrapper error(int code, String msg) {
        JsonWrapper wrapper = new JsonWrapper(Boolean.FALSE, code, msg, null);
        return wrapper;
    }
}
