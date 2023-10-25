package com.kshfx.pulse.task.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kshfx.pulse.csv.bean.CsvFile;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class TaskDetailDto implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 任务名称，范围:0~20字
     */
    private String taskName;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 用户ID
     */
    private Long createBy;

    /**
     * 创建时间，格式：XXXX年XX月XX日XX时XX分XX秒
     */
    private Timestamp createTime;

    /**
     * 文件集合编号
     */
    private List<CsvFile> files;
}
