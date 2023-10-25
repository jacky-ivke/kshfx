package com.kshfx.pulse.pretreat.design;

import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.pretreat.bean.RepairPulse;

public abstract class AbstractPretreatHandler {

    public AbstractPretreatHandler next;

    public void setNextHandler(AbstractPretreatHandler next){
        this.next = next;
    }

    public abstract RepairPulse handlerData(RawPulse original, String types);
}
