package com.kshfx.pulse.pretreat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kshfx.pulse.pretreat.bean.RepairConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RepairConfigMapper extends BaseMapper<RepairConfig> {

    RepairConfig getConfigByTaskId(@Param("taskId") Long taskId);
}
