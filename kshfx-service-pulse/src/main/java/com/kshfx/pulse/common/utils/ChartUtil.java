package com.kshfx.pulse.common.utils;

import com.kshfx.pulse.common.dto.ChartDto;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChartUtil {

    public static <T> List<ChartDto> converToChartDto(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<ChartDto> chartDtos = list.stream().map(item -> {
            ChartDto dto = new ChartDto();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
        return chartDtos;
    }

    public static <T> List<Object[]> converListToArray(List<T> list) {
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<ChartDto> chartDtos = ChartUtil.converToChartDto(list);
        List<Object[]> results = chartDtos.stream().map(item -> {
            List<Object> values = new ArrayList<>();
            values.add(item.getInterceptDate());
            values.add(item.getFrequency());
            values.add(item.getAmplitude());
            values.add(item.getPosition());
            values.add(item.getPulseWidth());
            return values.toArray();
        }).collect(Collectors.toList());
        return results;
    }
}
