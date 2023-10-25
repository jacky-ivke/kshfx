package com.kshfx.pulse.condition.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kshfx.core.utils.JsonWrapper;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.condition.service.ConditionResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ConditionResultController
 * @Description 筛选参数管理操作
 * @Version 1.0.0
 * @Date 2023/10/19 17:32
 * @Author ZXTD
 */
@RestController
@RequestMapping("/pulse/condition/result/")
@Api(tags = "脉冲可视化-筛选管理模块接口")
public class ConditionResultController {

    @Autowired
    private ConditionResultService conditionResultService;

    @ApiOperation(value = "根据筛选ID获取筛选结果列表数据接口")
    @RequestMapping(value = "/{taskId}/{conditionId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = true),
            @ApiImplicitParam(name = "conditionId", value = "筛选ID", paramType = "query", dataType = "long", required = true),
            @ApiImplicitParam(name = "pageNo", value = "分页编号", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", paramType = "query", dataType = "int", required = false)
    })
    public JsonWrapper getConditionResultList(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "conditionId") Long conditionId,
                                              @RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "20") Long pageSize) {
        conditionResultService.initResultData(taskId, conditionId);
        IPage<ConditionParam> pageData = conditionResultService.getConditionResultList(taskId, conditionId, pageNo, pageSize);
        return JsonWrapper.success(pageData);
    }

    @ApiOperation(value = "根据ID获删除筛选结果数据接口")
    @RequestMapping(value = "/{taskId}/{conditionId}/{resultId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.DELETE})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = true),
            @ApiImplicitParam(name = "conditionId", value = "筛选ID", paramType = "query", dataType = "long", required = true),
            @ApiImplicitParam(name = "resultId", value = "结果ID", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper delConditionParamById(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "conditionId") Long conditionId, @PathVariable(name = "resultId") Long resultId) {
        Assert.notNull(resultId, "resultId can't be nul");
        conditionResultService.delConditionResultById(taskId, resultId);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "获取筛选结果数据图表数据接口")
    @RequestMapping(value = "/chart/{taskId}/{conditionId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getChart(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "conditionId") Long conditionId) {

        Assert.notNull(taskId, "taskId can't be nul");
        List<Object[]> datas = conditionResultService.getChart(taskId, conditionId);
        return JsonWrapper.success(datas);
    }
}
