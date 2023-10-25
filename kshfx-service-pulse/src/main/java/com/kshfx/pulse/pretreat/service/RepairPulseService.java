package com.kshfx.pulse.pretreat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kshfx.pulse.common.constant.CommonEnum;
import com.kshfx.pulse.common.utils.ChartUtil;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.condition.bean.ConditionResult;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.csv.service.RawPulseService;
import com.kshfx.pulse.pretreat.bean.RepairPulse;
import com.kshfx.pulse.pretreat.dao.RepairPulseMapper;
import com.kshfx.pulse.pretreat.design.chain.handler.DataOfParamMissHandler;
import com.kshfx.pulse.pretreat.design.chain.handler.DataOfPositionHandler;
import com.kshfx.pulse.pretreat.design.chain.handler.DataOfStandardHandler;
import com.kshfx.pulse.pretreat.design.chain.handler.DataOfTimeHandler;
import com.kshfx.pulse.task.bean.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class RepairPulseService extends ServiceImpl<RepairPulseMapper, RepairPulse> {

    @Autowired
    private ThreadPoolTaskExecutor taskModuleExecutor;

    @Autowired
    private RepairPulseMapper repairPulseMapper;

    @Autowired
    private RawPulseService rawPulseService;

    public boolean checkRepairDataOfExists(Long taskId){
        return repairPulseMapper.checkRepairDataOfExists(taskId);
    }

    public void initResultData(Long taskId, String types) {
        boolean exists = this.checkRepairDataOfExists(taskId);
        if (exists) {
            return;
        }
        try {
            this.startRepair(taskId, types);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IPage getRepairPulseList(Long taskId, Long pageNo, Long pageSize) {
        IPage<RepairPulse> pageData = repairPulseMapper.getRepairPulseList(taskId, new Page<>(pageNo, pageSize));
        return pageData;
    }

    public IPage getRepairPulseOfParamList(ConditionParam conditionParam, Long pageNo, Long pageSize) {
        IPage<RepairPulse> pageData = repairPulseMapper.getRepairPulseOfParamList(conditionParam, new Page<>(pageNo, pageSize));
        return pageData;
    }

    public void startRepair(Long taskId, String types) throws ExecutionException, InterruptedException {
        Long pageNo = CommonEnum.DEF_PAGE_NO.getCode();
        Long totalPage = CommonEnum.DEF_PAGE_TOTALS.getCode();
        Long pageSize = CommonEnum.DEF_PAGE_SIZE.getCode();
        while (pageNo <= totalPage) {
            IPage rawPulsePage = rawPulseService.getRawPulseList(taskId, pageNo, pageSize);
            pageNo++;
            totalPage = rawPulsePage.getPages();
            List<RawPulse> subList = rawPulsePage.getRecords();
            if (CollectionUtils.isEmpty(subList)) {
                return;
            }
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                List<RepairPulse> results = subList.stream().map(item -> {
                    RepairPulse result = this.dataFilter(item, types);
                    return result;
                }).collect(Collectors.toList());
                results.removeIf(Objects::isNull);
                this.saveBatch(results);
            }, taskModuleExecutor);
            CompletableFuture.allOf(completableFuture).get();
        }
    }

    public RepairPulse dataFilter(RawPulse original, String types) {
        DataOfStandardHandler standardHandler = new DataOfStandardHandler();
        DataOfPositionHandler positionHandler = new DataOfPositionHandler();
        DataOfTimeHandler timeHandler = new DataOfTimeHandler();
        DataOfParamMissHandler paramMissHandler = new DataOfParamMissHandler();
        standardHandler.setNextHandler(positionHandler);
        positionHandler.setNextHandler(timeHandler);
        timeHandler.setNextHandler(paramMissHandler);
        RepairPulse repairPulse = standardHandler.handlerData(original, types);
        return repairPulse;
    }

    public List<Object[]> getChart(Long taskId){
        IPage<RepairPulse> pageData = this.getRepairPulseList(taskId, CommonEnum.DEF_PAGE_NO.getCode(), CommonEnum.DEF_CHART_LIMIT.getCode());
        List<Object[]> datas = ChartUtil.converListToArray(pageData.getRecords());
        return datas;
    }
}
