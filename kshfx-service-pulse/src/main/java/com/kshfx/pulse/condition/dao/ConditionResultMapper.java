package com.kshfx.pulse.condition.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kshfx.pulse.condition.bean.ConditionResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ConditionResultMapper extends BaseMapper<ConditionResult> {


    boolean checkResultDataOfExists(@Param("taskId") Long taskId, @Param("conditionId") Long conditionId);


    IPage getConditionResultList(@Param("taskId") Long taskId, @Param("conditionId") Long conditionId, Page<ConditionResult> page);
}
