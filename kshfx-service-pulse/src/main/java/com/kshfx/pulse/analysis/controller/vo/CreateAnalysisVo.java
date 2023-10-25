package com.kshfx.pulse.analysis.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "CreateAnalysisVo", description = "新建分选参数门限")
public class CreateAnalysisVo implements Serializable {

    @ApiModelProperty(value = "分选名称", required = true, example = "新建分选条件1")
    private String analysisName;

    @ApiModelProperty(value = "任务ID", required = true, example = "1")
    private Long taskId;

    @ApiModelProperty(value = "方位容差", required = true, example = "1")
    private Double positionAlw;

    @ApiModelProperty(value = "频率容差", required = true, example = "1")
    private Double frequencyAlw;

    @ApiModelProperty(value = "PRI容差", required = true, example = "1")
    private Double priAlw;

    @ApiModelProperty(value = "快分选门限", required = true, example = "1")
    private Double fastThreshold;

    @ApiModelProperty(value = "慢分选门限", required = true, example = "1")
    private Double slowThreshold;

    @ApiModelProperty(value = "PRI分选门限", required = true, example = "1")
    private Double priThreshold;

    @ApiModelProperty(value = "PRI组分选门限", required = true, example = "1")
    private Double priGoupThreshold;

    @ApiModelProperty(value = "PRI滑动门限", required = true, example = "1")
    private Double priSlideThreshold;

    @ApiModelProperty(value = "PRI抖动门限", required = true, example = "1")
    private Double priShakeThreshold;

    @ApiModelProperty(value = "信号最小脉冲数", required = true, example = "1")
    private Integer signalMinPulse;

    @ApiModelProperty(value = "分选最小脉冲数", required = true, example = "1")
    private Integer sortMinPulse;
}
