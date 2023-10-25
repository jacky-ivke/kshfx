package com.kshfx.pulse.task.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "CreateTaskVo", description = "创建任务信息")
public class CreateTaskVo implements Serializable {

    @ApiModelProperty(value = "任务名称" , required = true ,example = "测试任务AA")
    private String taskName;

    @ApiModelProperty(value = "任务描述" , required = false ,example = "任务说明描述信息XXXXXXXXXXXXXXXXXX")
    private String taskDesc;
}
