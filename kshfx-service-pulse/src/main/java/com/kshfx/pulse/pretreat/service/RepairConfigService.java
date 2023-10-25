package com.kshfx.pulse.pretreat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kshfx.pulse.pretreat.bean.RepairConfig;
import com.kshfx.pulse.pretreat.dao.RepairConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairConfigService extends ServiceImpl<RepairConfigMapper, RepairConfig> {

    @Autowired
    private RepairConfigMapper repairConfigMapper;

    @Autowired
    private RepairPulseService repairPulseService;

    public void saveRepairConfig(Long taskId, String types) {
        RepairConfig config = this.getConfigByTaskId(taskId);
        config = (null == config)? new RepairConfig() : config;
        config.setTaskId(taskId);
        config.setPepairTypes(types);
        this.saveOrUpdate(config);
        repairPulseService.initResultData(taskId, types);
    }

    public RepairConfig getConfigByTaskId(Long taskId){
        return repairConfigMapper.getConfigByTaskId(taskId);
    }
}
