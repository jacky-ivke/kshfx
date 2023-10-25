package com.kshfx.pulse.analysis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kshfx.pulse.analysis.bean.AnalysisParam;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.task.bean.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AnalysisParamMapper extends BaseMapper<AnalysisParam> {

    boolean checkAnalysisOfExists(@Param("analysisId") Long analysisId, @Param("analysisName") String analysisName);

    IPage getAnalysisList(@Param("taskId") Long taskId, Page<Task> page);
}
