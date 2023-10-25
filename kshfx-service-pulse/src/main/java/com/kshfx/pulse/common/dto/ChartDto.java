package com.kshfx.pulse.common.dto;

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
public class ChartDto implements Serializable {


    /**
     * 截获日期
     */
    private Integer interceptDate;

    /**
     * 截获时间
     */
    private Integer interceptTime;

    /**
     * 截获纳秒
     */
    private Integer interceptSecond;

    /**
     * 方位
     */
    private Double position;

    /**
     * 频率
     */
    private Double frequency;

    /**
     * 脉宽
     */
    private Double pulseWidth;

    /**
     * 幅度
     */
    private Double amplitude;
}
