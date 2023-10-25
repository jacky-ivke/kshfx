package com.kshfx.pulse.task.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kshfx.pulse.csv.service.CsvFileService;
import com.kshfx.pulse.task.bean.Task;
import com.kshfx.pulse.task.controller.vo.CreateTaskVo;
import com.kshfx.pulse.task.controller.vo.UpdateTaskVo;
import com.kshfx.pulse.task.dao.TaskMapper;
import com.kshfx.pulse.task.dto.TaskDetailDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @ClassName TaskService
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/10/16 16:13
 * @Author ZXTD
 */
@Service
public class TaskService extends ServiceImpl<TaskMapper, Task> {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private CsvFileService csvFileService;

    /**
     * @param taskId-任务ID, taskName-任务名称
     * @return true - 存在, false - 不存在
     * @Title checkTaskOfExists
     * @Description 校验任务名称是否重复
     * @Date 2023/10/18 12:09
     */
    public boolean checkTaskOfExists(Long taskId, String taskName) {
        return taskMapper.checkTaskOfExists(taskId, taskName);
    }

    /**
     * @param bean-任务实体
     * @return 无
     * @Title saveTask
     * @Description 保存任务信息
     * @Date 2023/10/18 12:10
     */
    public void saveTask(CreateTaskVo source) {
        Task bean = new Task();
        BeanUtils.copyProperties(source, bean);
        bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
        taskMapper.insert(bean);
    }

    /**
     * @param source-任务实体
     * @return 无
     * @Title updateTask
     * @Description 更新任务信息
     * @Date 2023/10/18 12:10
     */
    public void updateTask(UpdateTaskVo source) {
        Long taskId = source.getTaskId();
        Task bean = taskMapper.selectById(taskId);
        BeanUtils.copyProperties(source, bean);
        taskMapper.updateById(bean);
    }

    /**
     * @param taskId-任务ID
     * @return 无
     * @Title delTaskById
     * @Description 根据ID删除任务信息
     * @Date 2023/10/18 12:10
     */
    public void delTaskById(Long taskId) {
        taskMapper.deleteById(taskId);
    }

    /**
     * @param keyWords-关键字,page-页码,pageSize-分页大小
     * @return 任务集合
     * @Title getTaskList
     * @Description 根据关键字获取任务列表信息
     * @Date 2023/10/18 13:11
     */
    public IPage getTaskList(String keyWords, Integer pageNo, Integer pageSize) {
        IPage<Task> pageData = taskMapper.getTaskList(keyWords, new Page<>(pageNo, pageSize));
        return pageData;
    }

    /**
     * @param taskId-任务ID
     * @return 任务信息
     * @Title getTaskById
     * @Description 根据ID获取任务信息
     * @Date 2023/10/18 13:11
     */
    public TaskDetailDto getTaskById(Long taskId) {
        TaskDetailDto detailDto = new TaskDetailDto();
        Task bean = taskMapper.selectById(taskId);
        BeanUtils.copyProperties(bean, detailDto);
        detailDto.setFiles(csvFileService.getFilesByTaskId(taskId));
        return detailDto;
    }
}
