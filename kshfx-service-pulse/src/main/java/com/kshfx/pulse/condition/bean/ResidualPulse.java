package com.kshfx.pulse.condition.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "TASK_RESIDUAL_PULSE")
public class ResidualPulse implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    private Long taskId;

    /**
     * 筛选条件ID
     */
    @TableField(value = "CONDITION_ID")
    private Long conditionId;

    /**
     * 原始脉冲信号
     */
    @TableField(value = "RAW_PULSE_ID")
    private Long rawPulseId;

    /**
     * 分选信号编号
     */
    @TableField(value = "ANALYSIS_ID")
    private Long analysisId;

    /**
     * 0、界面删除, 1、分选未成功
     */
    @TableField(value = "SOURCE")
    private Integer source;
}
