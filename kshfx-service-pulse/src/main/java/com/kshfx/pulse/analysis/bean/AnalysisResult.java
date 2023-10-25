package com.kshfx.pulse.analysis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "TASK_ANALYSIS_RESULT")
public class AnalysisResult implements Serializable {

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    private Long taskId;

    /**
     * 侦察站ID
     */
    @TableField(value = "STATION_ID")
    private Long stationId;

    /**
     * 筛选事件ID
     */
    @TableField(value = "STATION_ID")
    private Long conditionId;

    /**
     * 分选门限ID
     */
    @TableField(value = "ANALYSIS_ID")
    private Long analysisId;

    /**
     * 目标批号
     */
    @TableField(value = "TARGE_ID")
    private Long targetId;

    /**
     * 信号截获次数
     */
    @TableField(value = "SIGNAL_INTERCEPT_NUM")
    private Integer signalInterceptNun;

    /**
     * 信号批次
     */
    @TableField(value = "SIGNAL_ID")
    private Long signalId;

    /**
     * 脉冲截获次数
     */
    @TableField(value = "PULSE_INTERCEPT_NUM")
    private Integer pulseInterceptNum;

    /**
     * 首次截获时间
     */
    @TableField(value = "FIRST_INTERCEPT_TIME")
    private Timestamp firstInterceptTime;

    /**
     * 末次截获时间
     */
    @TableField(value = "LAST_INTERCEPT_TIME")
    private Timestamp lastInterceptTime;

    /**
     * 最小截获方位
     */
    @TableField(value = "MIN_INTERCEPT_POS")
    private Double minInterceptPos;

    /**
     * 最大截获方位
     */
    @TableField(value = "MAX_INTERCEPT_POS")
    private Double maxInterceptPos;

    /**
     * 重频类型
     */
    @TableField(value = "REP_FREQUENCY_TYPE")
    private Integer repFrequencyType;

    /**
     * 重频数量
     */
    @TableField(value = "REP_FREQUENCY_NUM")
    private Integer repFrequencyNum;

    /**
     * 重频参数
     */
    @TableField(value = "REP_FREQUENCY_PARAM")
    private String repFrequencyParam;

    /**
     * 频率类型
     */
    @TableField(value = "FREQUENCY_TYPE")
    private Integer frequencyType;

    /**
     * 频率数量
     */
    @TableField(value = "FREQUENCY_NUM")
    private Integer frequencyNum;

    /**
     * 频率参数
     */
    @TableField(value = "FREQUENCY_PARAM")
    private String frequencyParam;

    /**
     * 脉宽类型
     */
    @TableField(value = "PULSE_WIDTH_TYPE")
    private Integer pulseWidthType;

    /**
     * 脉宽数量
     */
    @TableField(value = "PULSE_WIDTH_TYPE")
    private Integer pulseWidthNum;

    /**
     * 脉宽类型
     */
    @TableField(value = "PULSE_WIDTH_PARAM")
    private String pulseWidthParam;

    /**
     * 脉内调制类型
     */
    @TableField(value = "MODULATION_TYPE")
    private Integer modulationType;

    /**
     * 天线扫描方式
     */
    @TableField(value = "ANTENNA_SCAN_METHOD")
    private Integer antennaScanMethod;

    /**
     * 天线扫描周期
     */
    @TableField(value = "ANTENNA_SCAN_METHOD")
    private Double antennaScanCycle;

    /**
     * 天线扫描范围
     */
    @TableField(value = "ANTENNA_SCAN_RANGE")
    private Double antennaScanRange;

    /**
     * 状态
     */
    @TableField(value = "STATUS")
    private Integer status;
}

