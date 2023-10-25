package com.kshfx.pulse.analysis.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kshfx.core.utils.JsonWrapper;
import com.kshfx.pulse.analysis.bean.AnalysisParam;
import com.kshfx.pulse.analysis.controller.vo.CreateAnalysisVo;
import com.kshfx.pulse.analysis.service.AnalysisParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName AnalysisParamController
 * @Description 分选参数管理操作
 * @Version 1.0.0
 * @Date 2023/10/18 17:32
 * @Author ZXTD
 */
@RestController
@RequestMapping("/pulse/analysis/")
@Api(tags = "脉冲可视化-分选管理模块接口")
public class AnalysisParamController {

    @Autowired
    private AnalysisParamService analysisParamService;

    @ApiOperation(value = "获取分选列表数据接口")
    @RequestMapping(value = "/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页编号", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", paramType = "query", dataType = "int", required = false)
    })
    public JsonWrapper getAnalysisList(@PathVariable(name = "taskId") Long taskId, @RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "20") Long pageSize) {
        IPage<AnalysisParam> pageData = analysisParamService.getAnalysisList(taskId, pageNo, pageSize);
        return JsonWrapper.success(pageData);
    }

    @ApiOperation(value = "根据ID获取分选详情数据接口")
    @RequestMapping(value = "/{taskId}/{analysisId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getAnalysisById(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "analysisId") Long analysisId) {
        Assert.notNull(analysisId, "analysisId can't be nul");
        AnalysisParam bean = analysisParamService.getAnalysisById(taskId, analysisId);
        return JsonWrapper.success(bean);
    }

    @ApiOperation(value = "根据ID获删除筛选数据接口")
    @RequestMapping(value = "/{taskId}/{analysisId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.DELETE})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper delAnalysisById(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "analysisId") Long analysisId) {
        Assert.notNull(analysisId, "analysisId can't be nul");
        analysisParamService.delAnalysisById(taskId, analysisId);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "创建分选数据接口")
    @RequestMapping(value = "/", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "createAnalysisVo", value = "创建分选", paramType = "body", dataType = "CreateAnalysisVo", dataTypeClass = CreateAnalysisVo.class, required = true)
    })
    public JsonWrapper saveAnalysisParam(@RequestBody CreateAnalysisVo createAnalysisVo) {
        Assert.notNull(createAnalysisVo, "createAnalysisVo can't be nul");
        boolean success = analysisParamService.checkAnalysisOfExists(null, createAnalysisVo.getAnalysisName());
        if (success) {
            return JsonWrapper.error("分选参数门限名称已经存在");
        }
        analysisParamService.saveAnalysisParam(createAnalysisVo);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "自动分选数据接口")
    @RequestMapping(value = "/{taskId}/{analysisId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true),
            @ApiImplicitParam(name = "analysisId", value = "分选编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper autoAnalysis(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "analysisId") Long analysisId) {
        Assert.notNull(analysisId, "analysisId can't be nul");
        analysisParamService.autoAnalysis(taskId, analysisId);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "获取分选结果数据图表数据接口")
    @RequestMapping(value = "/chart/{taskId}/{analysisId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页编号", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = true),
            @ApiImplicitParam(name = "analysisId", value = "分选编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getChart(@PathVariable(name = "taskId") Long taskId, @PathVariable(name = "analysisId") Long analysisId) {

        Assert.notNull(taskId, "taskId can't be nul");
        List<Object[]> datas = analysisParamService.getChart(taskId);
        return JsonWrapper.success(datas);
    }
}
