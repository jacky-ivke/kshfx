package com.kshfx.pulse.csv.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.io.Serializable;

@Data
public class CsvFileDataDto implements Serializable {

    private Integer serialNo;

    @CsvBindByName(column = "侦察站ID")
    private Long stationId;

    @CsvBindByName(column = "截获日期")
    private Integer interceptDate;

    @CsvBindByName(column = "截获时间")
    private Integer interceptTime;

    @CsvBindByName(column = "截获纳秒")
    private Integer interceptSecond;

    @CsvBindByName(column = "频率")
    private Double frequency;

    @CsvBindByName(column = "方位")
    private Double position;

    @CsvBindByName(column = "脉宽")
    private Double pulseWidth;

    @CsvBindByName(column = "幅度")
    private Double amplitude;

    @CsvBindByName(column = "脉内调制类型")
    private Integer modulationType;
}
