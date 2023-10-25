package com.kshfx.pulse.common.constant;

public enum CommonEnum {

    DEF_CHART_LIMIT(100000L, "图标数据限制"),
    DEF_PAGE_SIZE(1000L, "分页大小"),
    DEF_PAGE_NO(1L,"默认页码"),
    DEF_PAGE_TOTALS(1L,"分选未成功");

    private final Long code;

    private final String msg;

    CommonEnum(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
