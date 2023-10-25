package com.kshfx.pulse.condition.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.condition.bean.ConditionResult;
import com.kshfx.pulse.condition.controller.vo.CreateConditionVo;
import com.kshfx.pulse.condition.dao.ConditionParamMapper;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.csv.service.RawPulseService;
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
public class ConditionParamService extends ServiceImpl<ConditionParamMapper, ConditionParam> {

    @Autowired
    private ConditionParamMapper conditionParamMapper;


    public boolean checkConditionOfExists(Long conditionId, String conditionName) {
        return conditionParamMapper.checkConditionOfExists(conditionId, conditionName);
    }

    public void saveConditionParam(CreateConditionVo source) {
        ConditionParam bean = new ConditionParam();
        BeanUtils.copyProperties(source, bean);
        conditionParamMapper.insert(bean);
    }



    public IPage getConditionList(Long taskId, Integer pageNo, Integer pageSize) {
        IPage<ConditionParam> pageData = conditionParamMapper.getConditionList(taskId, new Page<>(pageNo, pageSize));
        return pageData;
    }

    public ConditionParam getConditionById(Long taskId, Long conditionId) {
        ConditionParam bean = conditionParamMapper.selectById(conditionId);
        return bean;
    }

    public void delConditionById(Long taskId, Long conditionId) {
        conditionParamMapper.deleteById(conditionId);
    }
}
