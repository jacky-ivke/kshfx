package com.kshfx.pulse.analysis.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kshfx.pulse.analysis.bean.AnalysisParam;
import com.kshfx.pulse.analysis.controller.vo.CreateAnalysisVo;
import com.kshfx.pulse.analysis.dao.AnalysisParamMapper;
import com.kshfx.pulse.common.constant.CommonEnum;
import com.kshfx.pulse.common.utils.ChartUtil;
import com.kshfx.pulse.condition.controller.vo.CreateConditionVo;
import com.kshfx.pulse.pretreat.bean.RepairPulse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisParamService extends ServiceImpl<AnalysisParamMapper, AnalysisParam> {

    @Autowired
    private AnalysisParamMapper analysisParamMapper;

    public boolean checkAnalysisOfExists(Long analysisId, String analysisName) {
        return analysisParamMapper.checkAnalysisOfExists(analysisId, analysisName);
    }

    public void saveAnalysisParam(CreateAnalysisVo source) {
        AnalysisParam bean = new AnalysisParam();
        BeanUtils.copyProperties(source, bean);
        analysisParamMapper.insert(bean);
    }

    public IPage getAnalysisList(Long taskId, Long pageNo, Long pageSize) {
        IPage<AnalysisParam> pageData = analysisParamMapper.getAnalysisList(taskId, new Page<>(pageNo, pageSize));
        return pageData;
    }

    public AnalysisParam getAnalysisById(Long taskId, Long analysisId) {
        AnalysisParam bean = analysisParamMapper.selectById(analysisId);
        return bean;
    }

    public void delAnalysisById(Long taskId, Long analysisId) {
        analysisParamMapper.deleteById(analysisId);
    }

    public void autoAnalysis(Long taskId, Long analysisId){

        //外部接口调用
    }

    public List<Object[]> getChart(Long taskId){
        IPage<RepairPulse> pageData = this.getAnalysisList(taskId, CommonEnum.DEF_PAGE_NO.getCode(), CommonEnum.DEF_CHART_LIMIT.getCode());
        List<Object[]> datas = ChartUtil.converListToArray(pageData.getRecords());
        return datas;
    }
}
