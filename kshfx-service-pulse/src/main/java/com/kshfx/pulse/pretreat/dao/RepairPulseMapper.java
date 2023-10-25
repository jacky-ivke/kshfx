package com.kshfx.pulse.pretreat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.pretreat.bean.RepairPulse;
import com.kshfx.pulse.task.bean.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RepairPulseMapper extends BaseMapper<RepairPulse> {

    boolean checkRepairDataOfExists(@Param("taskId") Long taskId);

    IPage getRepairPulseOfParamList(@Param("conditionParam") ConditionParam conditionParam, Page<RepairPulse> page);

    IPage getRepairPulseList(@Param("taskId") Long taskId, Page<Task> page);
}
