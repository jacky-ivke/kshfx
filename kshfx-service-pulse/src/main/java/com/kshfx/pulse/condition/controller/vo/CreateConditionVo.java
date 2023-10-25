package com.kshfx.pulse.condition.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@ApiModel(value = "CreateConditionVo", description = "新建筛选")
public class CreateConditionVo implements Serializable {

    @ApiModelProperty(value = "筛选名称" , required = true ,example = "新建筛选条件1")
    private String conditionName;

    @ApiModelProperty(value = "任务ID" , required = true ,example = "1")
    private Long taskId;

    @ApiModelProperty(value = "侦察站ID" , required = true ,example = "1")
    private Long stationId;

    @ApiModelProperty(value = "开始时间" , required = true ,example = "10")
    private Timestamp startTime;

    @ApiModelProperty(value = "结束时间" , required = true ,example = "10")
    private Timestamp endTime;

    @ApiModelProperty(value = "起始方位" , required = true ,example = "0")
    private Double startPos;

    @ApiModelProperty(value = "终止方位" , required = true ,example = "0")
    private Double endPos;

    @ApiModelProperty(value = "起始频率" , required = true ,example = "0")
    private Double startFrequency;

    @ApiModelProperty(value = "终止频率" , required = true ,example = "0")
    private Double endFrequency;

    @ApiModelProperty(value = "起始脉宽" , required = true ,example = "0")
    private Double startPulseWidth;

    @ApiModelProperty(value = "终止脉宽" , required = true ,example = "0")
    private Double endPulseWidth;

    @ApiModelProperty(value = "起始幅度" , required = true ,example = "0")
    private Double startAmplitude;

    @ApiModelProperty(value = "终止频幅度" , required = true ,example = "0")
    private Double endAmplitude;

    @ApiModelProperty(value = "筛选来源类型" , required = true ,example = "0")
    private Integer sourceType;
}
