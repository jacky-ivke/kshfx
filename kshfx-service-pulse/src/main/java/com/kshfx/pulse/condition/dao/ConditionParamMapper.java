package com.kshfx.pulse.condition.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kshfx.pulse.condition.bean.ConditionParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ConditionParamMapper extends BaseMapper<ConditionParam> {

    boolean checkConditionOfExists(@Param("conditionId") Long conditionId, @Param("conditionName") String conditionName);

    IPage getConditionList(@Param("taskId") Long taskId, Page<ConditionParam> page);

}
