package com.kshfx.pulse.csv.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName RawPulse
 * @Description 原始脉冲数据表结构
 * @Version 1.0.0
 * @Date 2023/10/16 16:07
 * @Author ZXTD
 */
@Data
@TableName(value = "TASK_RAW_PULSE")
public class RawPulse implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 文件编号
     */
    @TableField(value = "CSV_ID")
    private Long csvId;

    /**
     * 脉冲序号
     */
    @TableField(value = "SERIAL_NO")
    private Integer serialNo;

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
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    private Long taskId;
}
