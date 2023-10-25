package com.kshfx.pulse.pretreat.controller;

import com.kshfx.core.utils.JsonWrapper;
import com.kshfx.pulse.pretreat.bean.RepairConfig;
import com.kshfx.pulse.pretreat.service.RepairConfigService;
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

/**
 * @ClassName RepairConfigController
 * @Description 预处理管理操作
 * @Version 1.0.0
 * @Date 2023/10/18 17:19
 * @Author ZXTD
 */
@RestController
@RequestMapping("/pulse/repair/config")
@Api(tags = "脉冲可视化-预处理管理模块接口")
public class RepairConfigController {

    @Autowired
    private RepairConfigService repairConfigService;

    @ApiOperation(value = "提交预处理设置接口")
    @RequestMapping(value = "/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = true),
            @ApiImplicitParam(name = "types", value = "预处理类型", paramType = "query", dataType = "String", required = false),
    })
    public JsonWrapper saveRepairConfig(@PathVariable(name = "taskId") Long taskId, String types) {
        Assert.notNull(taskId, "taskId can't be null");
        repairConfigService.saveRepairConfig(taskId, types);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "根据任务ID获取预处理设置数据接口")
    @RequestMapping(value = "/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getConfigByTaskId(@PathVariable(name = "taskId") Long taskId) {
        Assert.notNull(taskId, "taskId can't be nul");
        RepairConfig bean = repairConfigService.getConfigByTaskId(taskId);
        return JsonWrapper.success(bean);
    }
}
