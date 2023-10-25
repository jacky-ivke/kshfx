package com.kshfx.pulse.pretreat.design.chain.handler;

import com.kshfx.pulse.common.constant.DataRepairEnum;
import com.kshfx.pulse.common.utils.FileLimitUtil;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.csv.service.RawPulseService;
import com.kshfx.pulse.csv.utils.FileCountUtil;
import com.kshfx.pulse.pretreat.bean.RepairPulse;
import com.kshfx.pulse.pretreat.design.AbstractPretreatHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @ClassName DataOfPositionHandler
 * @Description 时间参数修复数据预处理操作
 * @Version 1.0.0
 * @Date 2023/10/16 16:11
 * @Author ZXTD
 */
public class DataOfTimeHandler extends AbstractPretreatHandler {

    private static DataOfTimeHandler timeHandler;

    @Autowired
    private static RawPulseService rawPulseService;

    @PostConstruct
    public void init() {
        timeHandler = this;
    }

    @Override
    public RepairPulse handlerData(RawPulse original, String types) {
        RepairPulse repairPulse = null;
        String type = DataRepairEnum.REPAIR_TIME.getCode();
        if (types.indexOf(type) >= 0) {
            Integer interceptTime = original.getInterceptTime();
            if (FileLimitUtil.isZero(interceptTime)) {
                RawPulse nextVaildPulse = timeHandler.rawPulseService.getNextTimeVaildPulse(original);
                repairPulse = new RepairPulse();
                original.setInterceptTime(nextVaildPulse.getInterceptTime());
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
