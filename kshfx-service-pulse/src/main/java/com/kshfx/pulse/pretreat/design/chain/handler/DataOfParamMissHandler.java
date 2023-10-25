package com.kshfx.pulse.pretreat.design.chain.handler;

import com.kshfx.pulse.common.constant.DataRepairEnum;
import com.kshfx.pulse.common.utils.FileLimitUtil;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.pretreat.bean.RepairPulse;
import com.kshfx.pulse.pretreat.design.AbstractPretreatHandler;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @ClassName DataOfParamMissHandler
 * @Description 删除残缺数据预处理操作
 * @Version 1.0.0
 * @Date 2023/10/16 16:11
 * @Author ZXTD
 */
public class DataOfParamMissHandler extends AbstractPretreatHandler {

    @Override
    public RepairPulse handlerData(RawPulse original, String types) {
        RepairPulse repairPulse = null;
        String type = DataRepairEnum.REPAIR_PARAM_MISS.getCode();
        if (types.indexOf(type) >= 0) {
            Double position = original.getPosition();
            Integer interceptTime = original.getInterceptTime();
            if (FileLimitUtil.isRepair(position) || FileLimitUtil.isZero(interceptTime)) {
                repairPulse = null;
            } else {
                repairPulse = new RepairPulse();
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
