package com.kshfx.pulse.condition.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kshfx.pulse.common.constant.CommonEnum;
import com.kshfx.pulse.common.utils.ChartUtil;
import com.kshfx.pulse.condition.bean.ConditionResult;
import com.kshfx.pulse.condition.bean.ResidualPulse;
import com.kshfx.pulse.condition.dao.ResidualPulseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidualPulseService extends ServiceImpl<ResidualPulseMapper, ResidualPulse> {

    @Autowired
    private ResidualPulseMapper residualPulseMapper;

    public IPage getResidualPulseList(Long taskId, Long conditionId, Long pageNo, Long pageSize) {
        IPage<ResidualPulse> pageData = residualPulseMapper.getResidualPulseList(taskId, conditionId, new Page<>(pageNo, pageSize));
        return pageData;
    }

    public void saveResidualPulse(Long taskId, Long conditionId, Long analysisId, Integer source, Long rawPulseId){
        ResidualPulse residualPulse = new ResidualPulse();
        residualPulse.setRawPulseId(rawPulseId);
        residualPulse.setAnalysisId(analysisId);
        residualPulse.setConditionId(conditionId);
        residualPulse.setTaskId(taskId);
        residualPulse.setSource(source);
        residualPulseMapper.insert(residualPulse);
    }

    public List<Object[]> getChart(Long taskId, Long conditionId){
        IPage<ResidualPulse> pageData = this.getResidualPulseList(taskId, conditionId, CommonEnum.DEF_PAGE_NO.getCode(), CommonEnum.DEF_CHART_LIMIT.getCode());
        List<Object[]> datas = ChartUtil.converListToArray(pageData.getRecords());
        return datas;
    }

}
