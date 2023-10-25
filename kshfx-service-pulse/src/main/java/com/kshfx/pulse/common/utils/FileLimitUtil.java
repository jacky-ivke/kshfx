package com.kshfx.pulse.common.utils;

import com.kshfx.pulse.pretreat.design.chain.handler.DataOfStandardHandler;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FileLimitUtil {

    /**
     * 修复截获时间为0的脉冲
     */
    public static  Integer INTERCEPT_TIME_ANOMALY_VALUE = 0;

    /**
     * 修复截获方位为400的脉冲
     */
    public static  BigDecimal POSITIO_NANOMALY_VALUE = new BigDecimal("400");

    /**
     * 重频异常临界值
     */
    public static final int MAX_INTERVAL = 10000;

    /**
     * 重频异常临界值
     */
    public static final int MIN_INTERVAL = 10;

    /**
     * 精度格式化
     */
    public static DecimalFormat df = new DecimalFormat("#.0");

    /**
     * 方位是否需要修复
     */
    public static boolean isRepair(Double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.compareTo(FileLimitUtil.POSITIO_NANOMALY_VALUE) == 0;
    }

    /**
     * 时间是否需要修复
     */
    public static boolean isZero(Integer value) {
        return (null == value || value == FileLimitUtil.INTERCEPT_TIME_ANOMALY_VALUE);
    }

    /**
     * 数据精度格式化
     */
    public static Double format(Double value) {
        String str = FileLimitUtil.df.format(value);
        return Double.parseDouble(str);
    }
}
