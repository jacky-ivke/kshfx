package com.kshfx.pulse.analysis.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "TASK_ANALYSIS_PARAM")
public class AnalysisParam implements Serializable {

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @TableField(value = "ANALYSIS_NAME")
    private String analysisName;

    /**
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    private Long taskId;

    /**
     * 方位容差
     */
    @TableField(value = "POSITION_ALW")
    private Double positionAlw;

    /**
     * 频率容差
     */
    @TableField(value = "FREQUENCY_ALW")
    private Double frequencyAlw;

    /**
     * PRI容差
     */
    @TableField(value = "PRI_ALW")
    private Double priAlw;

    /**
     * 快分选门限
     */
    @TableField(value = "FAST_THRESHOLD")
    private Double fastThreshold;

    /**
     * 慢分选门限
     */
    @TableField(value = "SLOW_THRESHOLD")
    private Double slowThreshold;

    /**
     * PRI参差门限
     */
    @TableField(value = "PRI_THRESHOLD")
    private Double priThreshold;

    /**
     * PRI组参差门限
     */
    @TableField(value = "PRI_GROUP_THRESHOLD")
    private Double priGoupThreshold;

    /**
     * PRI滑变门限
     */
    @TableField(value = "PRI_SLIDE_THRESHOLD")
    private Double priSlideThreshold;

    /**
     * PRI抖动门限
     */
    @TableField(value = "PRI_SHAKE_THRESHOLD")
    private Double priShakeThreshold;

    /**
     * 信号最小脉冲数
     */
    @TableField(value = "SIGNAL_MIN_PULSE")
    private Integer signalMinPulse;

    /**
     * 分选最小脉冲数
     */
    @TableField(value = "SORT_MIN_PULSE")
    private Integer sortMinPulse;
}
