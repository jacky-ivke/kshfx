package com.kshfx.pulse.common.constant;

public enum DataRepairEnum {

    REPAIR_STANDARD("1", "数据标准化"),
    REPAIR_POSITION("2", "方位参数修复"),
    REPAIR_TIME("3", "时间参数修复"),
    REPAIR_PARAM_MISS("4", "删除残缺数据");

    private final String code;

    private final String msg;

    DataRepairEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
