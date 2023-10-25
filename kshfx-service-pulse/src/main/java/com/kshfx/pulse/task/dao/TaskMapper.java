package com.kshfx.pulse.task.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kshfx.pulse.task.bean.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName TaskMapper
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/10/16 16:13
 * @Author ZXTD
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    boolean checkTaskOfExists(@Param("taskId") Long taskId, @Param("taskName") String taskName);

    IPage getTaskList(@Param("taskName") String taskName, Page<Task> page);
}
