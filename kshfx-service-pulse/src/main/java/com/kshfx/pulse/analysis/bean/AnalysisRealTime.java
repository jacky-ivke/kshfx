package com.kshfx.pulse.analysis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "TASK_ANALYSIS_REALTIME")
public class AnalysisRealTime implements Serializable {

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
     * 截获时间
     */
    @TableField(value = "INTERCEPT_TIME")
    private Long interceptTime;

    /**
     * 截获时间
     */
    @TableField(value = "RESIDENCY_TIME")
    private Double residencyTime;

    /**
     * 截获方位
     */
    @TableField(value = "INTERCEPT_POS")
    private Long interceptPos;


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
     * 幅度
     */
    @TableField(value = "AMPLITUDE")
    private Double amplitude;

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
     * 分选结论ID
     */
    @TableField(value = "ANALYSIS_RESULT_ID")
    private Long analysisResultId;

}

