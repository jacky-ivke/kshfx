package com.kshfx.pulse.csv.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kshfx.core.utils.JsonWrapper;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.csv.dto.CsvFileInfoDto;
import com.kshfx.pulse.csv.service.RawPulseService;
import com.kshfx.pulse.task.bean.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName CsvFileController
 * @Description CSV文件导入操作
 * @Version 1.0.0
 * @Date 2023/10/16 16:11
 * @Author ZXTD
 */
@RestController
@RequestMapping("/pulse/csv/")
@Api(tags = "脉冲可视化-导入管理模块接口")
public class RawPulseController {

    @Autowired
    private RawPulseService rawPulseService;

    @ApiOperation(value = "导入CSV数据文件接口")
    @RequestMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = {RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = false)
    })
    public JsonWrapper importData(@RequestPart(value = "files") MultipartFile[] files, Long taskId) {

        Assert.notNull(taskId, "taskId can't be nul");
        rawPulseService.importData(files, taskId);
        return JsonWrapper.success();
    }

    @ApiOperation(value = "获取原始全脉冲列表数据接口")
    @RequestMapping(value = "/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "分页编号", paramType = "query", dataType = "int", required = false),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", paramType = "query", dataType = "int", required = false)
    })
    public JsonWrapper getRawPulseList(@PathVariable(name = "taskId") Long taskId, @RequestParam(defaultValue = "1") Long pageNo, @RequestParam(defaultValue = "20") Long pageSize) {
        IPage<RawPulse> pageData = rawPulseService.getRawPulseList(taskId, pageNo, pageSize);
        return JsonWrapper.success(pageData);
    }


    @ApiOperation(value = "获取原始数据图表数据接口")
    @RequestMapping(value = "/chart/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})

    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务ID", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getChart(@PathVariable(name = "taskId") Long taskId) {

        Assert.notNull(taskId, "taskId can't be nul");
        List<Object[]> datas = rawPulseService.getChart(taskId);
        return JsonWrapper.success(datas);
    }
}
