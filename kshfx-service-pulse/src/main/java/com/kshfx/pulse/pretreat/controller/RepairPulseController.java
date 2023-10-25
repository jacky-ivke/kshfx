package com.kshfx.pulse.pretreat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kshfx.core.utils.JsonWrapper;
import com.kshfx.pulse.pretreat.bean.RepairPulse;
import com.kshfx.pulse.pretreat.service.RepairPulseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName RepairPulseController
 * @Description 预处理管理操作
 * @Version 1.0.0
 * @Date 2023/10/18 17:19
 * @Author ZXTD
 */
@RestController
@RequestMapping("/pulse/repair")
@Api(tags = "脉冲可视化-预处理管理模块接口")
public class RepairPulseController {

    @Autowired
    private RepairPulseService repairPulseService;

    @ApiOperation(value = "获取修复全脉冲列表数据接口")
    @RequestMapping(value = "/", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页编号", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getRepairPulseList(Long taskId, Long pageNo, Long pageSize) {
        IPage<RepairPulse> pageData = repairPulseService.getRepairPulseList(taskId, pageNo, pageSize);
        return JsonWrapper.success(pageData);
    }

    @ApiOperation(value = "获取修复数据图表数据接口")
    @RequestMapping(value = "/chart/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getChart(@PathVariable(name = "taskId") Long taskId) {

        Assert.notNull(taskId, "taskId can't be nul");
        List<Object[]> datas = repairPulseService.getChart(taskId);
        return JsonWrapper.success(datas);
    }
}
