package com.kshfx.pulse.task.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kshfx.core.utils.JsonWrapper;
import com.kshfx.pulse.task.bean.Task;
import com.kshfx.pulse.task.controller.vo.CreateTaskVo;
import com.kshfx.pulse.task.controller.vo.UpdateTaskVo;
import com.kshfx.pulse.task.dto.TaskDetailDto;
import com.kshfx.pulse.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TaskController
 * @Description 任务基础管理操作
 * @Version 1.0.0
 * @Date 2023/10/16 16:11
 * @Author ZXTD
 */
@RestController
@RequestMapping("/pulse/task")
@Api(tags = "脉冲可视化-任务管理模块接口")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "获取任务列表数据接口")
    @RequestMapping(value = "/", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页编号", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "keywords", value = "检索条件", paramType = "query", dataType = "String", required = false)
    })
    public JsonWrapper getTaskList(String keywords, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize) {
        IPage<Task> pageData = taskService.getTaskList(keywords, pageNo, pageSize);
        return JsonWrapper.success(pageData);
    }

    @ApiOperation(value = "根据ID获取任务详情数据接口")
    @RequestMapping(value = "/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getTaskById(@PathVariable(name = "taskId") Long taskId) {
        Assert.notNull(taskId, "taskId can't be nul");
        TaskDetailDto bean = taskService.getTaskById(taskId);
        return JsonWrapper.success(bean);
    }

    @ApiOperation(value = "根据ID获删除任务数据接口")
    @RequestMapping(value = "/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.DELETE})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper delTaskById(@PathVariable(name = "taskId") Long taskId) {
        Assert.notNull(taskId, "taskId can't be nul");
        taskService.delTaskById(taskId);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "创建任务数据接口")
    @RequestMapping(value = "/", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "createTaskVo", value = "创建任务", paramType = "body", dataType = "CreateTaskVo", dataTypeClass = CreateTaskVo.class, required = true)
    })
    public JsonWrapper saveTask(@RequestBody CreateTaskVo createTaskVo) {
        Assert.notNull(createTaskVo, "createTaskVo can't be nul");
        boolean success = taskService.checkTaskOfExists(null, createTaskVo.getTaskName());
        if (success) {
            return JsonWrapper.error("任务名称已经存在");
        }
        taskService.saveTask(createTaskVo);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "修改任务数据接口")
    @RequestMapping(value = "/", produces = "application/json;charset=UTF-8", method = {RequestMethod.PUT})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateTaskVo", value = "修改任务", paramType = "body", dataType = "UpdateTaskVo", dataTypeClass = UpdateTaskVo.class, required = true)
    })
    public JsonWrapper updateTask(@RequestBody UpdateTaskVo updateTaskVo) {
        Assert.notNull(updateTaskVo, "updateTaskVo can't be nul");
        boolean success = taskService.checkTaskOfExists(updateTaskVo.getTaskId(), updateTaskVo.getTaskName());
        if (success) {
            return JsonWrapper.error("任务名称已经存在");
        }
        taskService.updateTask(updateTaskVo);
        return JsonWrapper.success();
    }
}
