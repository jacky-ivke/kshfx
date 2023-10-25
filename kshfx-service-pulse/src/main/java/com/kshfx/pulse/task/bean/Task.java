package com.kshfx.pulse.task.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ClassName Task
 * @Description 任务信息表结构
 * @Version 1.0.0
 * @Date 2023/10/16 16:07
 * @Author ZXTD
 */
@Data
@TableName(value = "TASK")
public class Task implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称，范围:0~20字
     */
    @TableField(value = "TASK_NAME")
    private String taskName;

    /**
     * 任务描述
     */
    @TableField(value = "TASK_DESC")
    private String taskDesc;

    /**
     * 用户ID
     */
    @TableField(value = "CREATE_BY")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Timestamp createTime;

    /**
     * 文件集合编号
     */
    @TableField(value = "FILES")
    private String files;
}
