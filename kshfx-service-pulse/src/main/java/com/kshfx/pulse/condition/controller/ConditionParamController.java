package com.kshfx.pulse.condition.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kshfx.core.utils.JsonWrapper;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.condition.controller.vo.CreateConditionVo;
import com.kshfx.pulse.condition.service.ConditionParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ConditionParamController
 * @Description 筛选参数管理操作
 * @Version 1.0.0
 * @Date 2023/10/18 17:32
 * @Author ZXTD
 */
@RestController
@RequestMapping("/pulse/condition/param/")
@Api(tags = "脉冲可视化-筛选管理模块接口")
public class ConditionParamController {

    @Autowired
    private ConditionParamService conditionParamService;

    @ApiOperation(value = "获取筛选列表数据接口")
    @RequestMapping(value = "/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页编号", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", paramType = "query", dataType = "int", required = false)
    })
    public JsonWrapper getConditionParamList(@PathVariable(name = "taskId") Long taskId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "20") Integer pageSize) {
        IPage<ConditionParam> pageData = conditionParamService.getConditionList(taskId, pageNo, pageSize);
        return JsonWrapper.success(pageData);
    }

    @ApiOperation(value = "根据ID获取筛选详情数据接口")
    @RequestMapping(value = "/{taskId}/{conditionId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getConditionParamById(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "conditionId") Long conditionId) {
        Assert.notNull(conditionId, "conditionId can't be nul");
        ConditionParam bean = conditionParamService.getConditionById(taskId, conditionId);
        return JsonWrapper.success(bean);
    }

    @ApiOperation(value = "根据ID获删除筛选数据接口")
    @RequestMapping(value = "/{taskId}/{conditionId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.DELETE})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper delConditionParamById(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "conditionId") Long conditionId) {
        Assert.notNull(conditionId, "conditionId can't be nul");
        conditionParamService.delConditionById(taskId, conditionId);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "创建筛选数据接口")
    @RequestMapping(value = "/", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "createConditionVo", value = "创建筛选", paramType = "body", dataType = "CreateConditionVo", dataTypeClass = CreateConditionVo.class, required = true)
    })
    public JsonWrapper saveConditionParam(@RequestBody CreateConditionVo createConditionVo) {
        Assert.notNull(createConditionVo, "createConditionVo can't be nul");
        boolean success = conditionParamService.checkConditionOfExists(null, createConditionVo.getConditionName());
        if (success) {
            return JsonWrapper.error("筛选名称已经存在");
        }
        conditionParamService.saveConditionParam(createConditionVo);
        return JsonWrapper.success();
    }
}
