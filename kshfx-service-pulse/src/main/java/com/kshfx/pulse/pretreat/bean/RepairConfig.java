package com.kshfx.pulse.pretreat.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "TASK_REPAIR_CONFIG")
public class RepairConfig implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 预处理名称
     */
    @TableField(value = "PEPAIR_NAME")
    private String pepairName;

    /**
     *
     */
    @TableField(value = "PEPAIR_TYPES")
    private String pepairTypes;

    /**
     * 任务ID
     */
    @TableField(value = "TASK_ID")
    private Long taskId;
}
