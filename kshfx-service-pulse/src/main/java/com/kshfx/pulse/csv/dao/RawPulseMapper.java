package com.kshfx.pulse.csv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.csv.bean.RawPulse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName CsvFileMapper
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/10/16 16:13
 * @Author ZXTD
 */
@Mapper
public interface RawPulseMapper extends BaseMapper<RawPulse> {

    IPage getRawPulseList(@Param("taskId") Long taskId, Page<RawPulse> page);

    IPage getRawPulseOfParamList(@Param("conditionParam") ConditionParam conditionParam, @Param("taskId") Long taskId, Page<RawPulse> page);

    RawPulse getNextPosVaildPulse(@Param("taskId") Long taskId, @Param("serialNo") Integer serialNo);

    RawPulse getNextTimeVaildPulse(@Param("taskId") Long taskId, @Param("serialNo") Integer serialNo);
}
