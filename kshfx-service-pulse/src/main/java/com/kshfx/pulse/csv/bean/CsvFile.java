package com.kshfx.pulse.csv.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ClassName CsvFile
 * @Description 数据文件信息表结构
 * @Version 1.0.0
 * @Date 2023/10/16 16:07
 * @Author ZXTD
 */
@Data
@TableName(value = "TASK_CSV_FILE")
public class CsvFile implements Serializable {

    /**
     * 文件编号
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    @TableField(value = "CSV_NAME")
    private String csvName;

    /**
     * 侦察站ID
     */
    @TableField(value = "STATION_ID")
    private Long stationId;

    /**
     * 文件大小
     */
    @TableField(value = "FILE_SIZE")
    private Long fileSize;

    /**
     * 采集时间，格式：xxxx年xx月xx日xx时xx分xx秒
     */
    @TableField(value = "CREATE_TIME")
    private Timestamp createTime;

    /**
     * 脉冲数量
     */
    @TableField(value = "PULSE_NUM")
    private Integer pulseNum;

    /**
     * 重频异常数
     */
    @TableField(value = "INTERVAL_NUM")
    private Long intervalNum;

    /**
     * 方位异常数
     */
    @TableField(value = "POS_ANOMALY_NUM")
    private Long posAnomalyNum;

    /**
     * 时间异常数
     */
    @TableField(value = "TIME_ANOMALY_NUM")
    private Long timeAnomalyNum;

    /**
     * 最小频率
     */
    @TableField(value = "MIN_FREQUENCY")
    private Double minFrequency;

    /**
     * 最大频率
     */
    @TableField(value = "MAX_FREQUENCY")
    private Double maxFrequency;

    /**
     * 最小脉宽
     */
    @TableField(value = "MIN_PULSE_WIDTH")
    private Double minPulseWidth;

    /**
     * 最大脉宽
     */
    @TableField(value = "MAX_PULSE_WIDTH")
    private Double maxPulseWidth;

    /**
     * 最小方位
     */
    @TableField(value = "MIN_POSITION")
    private Double minPosition;

    /**
     * 最大方位
     */
    @TableField(value = "MAX_POSITION")
    private Double maxPosition;

    /**
     * 最小幅度
     */
    @TableField(value = "MIN_AMPLITUDE")
    private Double minAmplitude;

    /**
     * 最大幅度
     */
    @TableField(value = "MAX_AMPLITUDE")
    private Double maxAmplitude;

    /**
     * 文件地址
     */
    @TableField(value = "CSV_URL")
    private String csvUrl;

    /**
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    private Long taskId;
}
