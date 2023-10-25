package com.kshfx.pulse.condition.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kshfx.pulse.common.constant.CommonEnum;
import com.kshfx.pulse.common.constant.DelSourceEnum;
import com.kshfx.pulse.common.utils.ChartUtil;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.condition.bean.ConditionResult;
import com.kshfx.pulse.condition.dao.ConditionResultMapper;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.csv.service.RawPulseService;
import com.kshfx.pulse.pretreat.bean.RepairPulse;
import com.kshfx.pulse.pretreat.service.RepairPulseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ConditionResultService extends ServiceImpl<ConditionResultMapper, ConditionResult> {

    @Autowired
    private ConditionResultMapper conditionResultMapper;

    @Autowired
    private ResidualPulseService residualPulseService;

    @Autowired
    private RawPulseService rawPulseService;

    @Autowired
    private RepairPulseService repairPulseService;

    @Autowired
    private ThreadPoolTaskExecutor taskModuleExecutor;

    @Autowired
    private ConditionResultService conditionResultService;

    @Autowired
    private ConditionParamService conditionParamService;

    public void initResultData(Long taskId, Long conditionId) {
        boolean exists = conditionResultMapper.checkResultDataOfExists(taskId, conditionId);
        if (exists) {
            return;
        }
        try {
            this.startFilter(taskId, conditionId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IPage getConditionResultList(Long taskId, Long conditionId, Long pageNo, Long pageSize) {
        IPage<ConditionResult> pageData = conditionResultMapper.getConditionResultList(taskId, conditionId, new Page<>(pageNo, pageSize));
        return pageData;
    }

    public void delConditionResultById(Long taskId, Long resultId) {
        ConditionResult conditionResult = conditionResultMapper.selectById(resultId);
        if (null == conditionResult) {
            return;
        }
        Long conditionId = conditionResult.getConditionId();
        Long analysisId = conditionResult.getAnalysisId();
        Long rawPulseId = conditionResult.getRawPulseId();
        Integer source = DelSourceEnum.MANUAL_DELETE.getCode();
        residualPulseService.saveResidualPulse(taskId, conditionId, analysisId, source, rawPulseId);
        conditionResultMapper.deleteById(resultId);
    }

    public void filterFromRawPulse(Long taskId, Long conditionId) throws ExecutionException, InterruptedException {
        Long pageNo = CommonEnum.DEF_PAGE_NO.getCode();
        Long totalPage = CommonEnum.DEF_PAGE_TOTALS.getCode();
        Long pageSize = CommonEnum.DEF_PAGE_SIZE.getCode();
        ConditionParam conditionParam = conditionParamService.getById(conditionId);
        while (pageNo <= totalPage) {
            IPage rawPulsePage = rawPulseService.getRawPulseList(conditionParam, taskId, pageNo, pageSize);
            pageNo++;
            totalPage = rawPulsePage.getPages();
            List<RawPulse> subList = rawPulsePage.getRecords();
            if (CollectionUtils.isEmpty(subList)) {
                return;
            }
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                List<ConditionResult> results = subList.stream().map(item -> {
                    ConditionResult result = new ConditionResult();
                    BeanUtils.copyProperties(item, result);
                    result.setId(null);
                    result.setConditionId(conditionId);
                    result.setRawPulseId(item.getId());
                    result.setTaskId(taskId);
                    return result;
                }).collect(Collectors.toList());
                conditionResultService.saveBatch(results);
            }, taskModuleExecutor);
            CompletableFuture.allOf(completableFuture).get();
        }
    }

    public void filterFromRepairPulse(Long taskId, Long conditionId) throws ExecutionException, InterruptedException {
        Long pageNo = CommonEnum.DEF_PAGE_NO.getCode();
        Long totalPage = CommonEnum.DEF_PAGE_TOTALS.getCode();
        Long pageSize = CommonEnum.DEF_PAGE_SIZE.getCode();
        ConditionParam conditionParam = conditionParamService.getById(conditionId);
        while (pageNo <= totalPage) {
            IPage repairPulsePage = repairPulseService.getRepairPulseOfParamList(conditionParam, pageNo, pageSize);
            pageNo++;
            totalPage = repairPulsePage.getPages();
            List<RepairPulse> subList = repairPulsePage.getRecords();
            if (CollectionUtils.isEmpty(subList)) {
                return;
            }
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                List<ConditionResult> results = subList.stream().map(item -> {
                    ConditionResult result = new ConditionResult();
                    BeanUtils.copyProperties(item, result);
                    result.setId(null);
                    result.setConditionId(conditionId);
                    result.setRawPulseId(item.getRawPulseId());
                    result.setTaskId(taskId);
                    return result;
                }).collect(Collectors.toList());
                conditionResultService.saveBatch(results);
            }, taskModuleExecutor);
            CompletableFuture.allOf(completableFuture).get();
        }
    }

    public void startFilter(Long taskId, Long conditionId) throws ExecutionException, InterruptedException {
        boolean exists = repairPulseService.checkRepairDataOfExists(taskId);
        if (exists) {
            //从预处理之后的数据中筛选
            this.filterFromRepairPulse(taskId, conditionId);
            return;
        }
        //从原始数据中筛选
        this.filterFromRawPulse(taskId, conditionId);
    }


    public List<Object[]> getChart(Long taskId, Long conditionId){
        IPage<ConditionResult> pageData = this.getConditionResultList(taskId, conditionId, CommonEnum.DEF_PAGE_NO.getCode(), CommonEnum.DEF_CHART_LIMIT.getCode());
        List<Object[]> datas = ChartUtil.converListToArray(pageData.getRecords());
        return datas;
    }
}
