package com.kshfx.pulse.csv.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class CsvFileInfoDto implements Serializable {

    /**
     * 文件大小
     */
    private Long fileSize = 0L;

    /**
     * 脉冲数量
     */
    private Integer pulseNum;

    /**
     * 重频异常数
     */
    private Long intervalNum;

    /**
     * 方位异常数
     */
    private Long posAnomalyNum;

    /**
     * 时间异常数
     */
    private Long timeAnomalyNum;

    /**
     * 最小频率
     */
    private Double minFrequency;

    /**
     * 最大频率
     */
    private Double maxFrequency;

    /**
     * 最小脉宽
     */
    private Double minPulseWidth;

    /**
     * 最大脉宽
     */
    private Double maxPulseWidth;

    /**
     * 最小方位
     */
    private Double minPosition;

    /**
     * 最大方位
     */
    private Double maxPosition;

    /**
     * 最小幅度
     */
    private Double minAmplitude;

    /**
     * 最大幅度
     */
    private Double maxAmplitude;
}



