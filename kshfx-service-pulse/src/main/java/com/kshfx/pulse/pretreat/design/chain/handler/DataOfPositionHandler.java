package com.kshfx.pulse.pretreat.design.chain.handler;

import com.kshfx.pulse.common.constant.DataRepairEnum;
import com.kshfx.pulse.common.utils.FileLimitUtil;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.csv.service.RawPulseService;
import com.kshfx.pulse.pretreat.bean.RepairPulse;
import com.kshfx.pulse.pretreat.design.AbstractPretreatHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * @ClassName DataOfPositionHandler
 * @Description 方位参数修复数据预处理操作
 * @Version 1.0.0
 * @Date 2023/10/16 16:11
 * @Author ZXTD
 */
@Component
public class DataOfPositionHandler extends AbstractPretreatHandler {

    private static DataOfPositionHandler positionHandler;

    @Autowired
    private static RawPulseService rawPulseService;

    @PostConstruct
    public void init(){
        positionHandler = this;
    }

    @Override
    public RepairPulse handlerData(RawPulse original, String types) {
        RepairPulse repairPulse = null;
        String type = DataRepairEnum.REPAIR_POSITION.getCode();
        if (types.indexOf(type) >= 0) {
            Double position = original.getPosition();
            if (FileLimitUtil.isRepair(position)) {
                RawPulse nextVaildPulse = positionHandler.rawPulseService.getNextPosVaildPulse(original);
                repairPulse = new RepairPulse();
                original.setPosition(nextVaildPulse.getPosition());
                BeanUtils.copyProperties(original, repairPulse);
                repairPulse.setRawPulseId(original.getId());
            }
        }else{
            repairPulse = new RepairPulse();
            BeanUtils.copyProperties(original, repairPulse);
        }
        if (null != next) {
            repairPulse = this.next.handlerData(original, types);
        }
        return repairPulse;
    }
}
