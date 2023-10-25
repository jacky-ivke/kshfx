package com.kshfx.pulse.condition.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "TASK_CONDITION_RESULT")
public class ConditionResult implements Serializable {

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
     * 原始脉冲信号
     */
    @TableField(value = "RAW_PULSE_ID")
    private Long rawPulseId;

    /**
     * 筛选条件ID
     */
    @TableField(value = "CONDITION_ID")
    private Long conditionId;

    /**
     * 截获日期
     */
    @TableField(value = "INTERCEPT_DATE")
    private Integer interceptDate;

    /**
     * 截获时间
     */
    @TableField(value = "INTERCEPT_TIME")
    private Integer interceptTime;

    /**
     * 截获纳秒
     */
    @TableField(value = "INTERCEPT_SECOND")
    private Integer interceptSecond;

    /**
     * 方位
     */
    @TableField(value = "POSITION")
    private Double position;

    /**
     * 频率
     */
    @TableField(value = "FREQUENCY")
    private Double frequency;

    /**
     * 脉宽
     */
    @TableField(value = "PULSE_WIDTH")
    private Double pulseWidth;

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
     * 分选信号编号
     */
    @TableField(value = "ANALYSIS_ID")
    private Long analysisId;
}
