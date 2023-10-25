package com.kshfx.pulse.common.constant;

public enum DelSourceEnum {

    MANUAL_DELETE(0,"界面删除"),
    ANALYSIS_FAILED(1,"分选未成功");

    private final Integer code;

    private final String msg;

    DelSourceEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
