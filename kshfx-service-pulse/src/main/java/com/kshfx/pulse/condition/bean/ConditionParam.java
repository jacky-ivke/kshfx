package com.kshfx.pulse.condition.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "TASK_CONDITION_PARAM")
public class ConditionParam implements Serializable {

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField(value = "CONDITION_NAME")
    private String conditionName;

    @TableField(value = "TASK_ID")
    private Long taskId;

    /**
     * 侦察站ID
     */
    @TableField(value = "STATION_ID")
    private Long stationId;

    @TableField(value = "START_TIME")
    private Integer startTime;

    @TableField(value = "END_TIME")
    private Integer endTime;

    @TableField(value = "START_POS")
    private Double startPos;

    @TableField(value = "END_POS")
    private Double endPos;

    @TableField(value = "START_FREQUENCY")
    private Double startFrequency;

    @TableField(value = "END_FREQUENCY")
    private Double endFrequency;

    @TableField(value = "START_PULSE_WIDTH")
    private Double startPulseWidth;

    @TableField(value = "END_PULSE_WIDTH")
    private Double endPulseWidth;

    @TableField(value = "START_AMPLITUDE")
    private Double startAmplitude;

    @TableField(value = "END_AMPLITUDE")
    private Double endAmplitude;

    @TableField(value = "SOURCE_TYPE")
    private Integer sourceType;
}
