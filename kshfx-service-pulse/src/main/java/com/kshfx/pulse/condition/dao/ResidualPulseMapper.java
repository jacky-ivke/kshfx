package com.kshfx.pulse.condition.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kshfx.pulse.condition.bean.ResidualPulse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ResidualPulseMapper extends BaseMapper<ResidualPulse> {


    IPage getResidualPulseList(@Param("taskId") Long taskId, @Param("conditionId") Long conditionId, Page<ResidualPulse> page);
}
