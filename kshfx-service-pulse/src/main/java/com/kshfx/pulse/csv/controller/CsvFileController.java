package com.kshfx.pulse.csv.controller;


import com.kshfx.core.utils.JsonWrapper;
import com.kshfx.pulse.csv.dto.CsvFileInfoDto;
import com.kshfx.pulse.csv.service.CsvFileService;
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
 * @ClassName CsvFileController
 * @Description CSV文件导入操作
 * @Version 1.0.0
 * @Date 2023/10/16 16:11
 * @Author ZXTD
 */
@RestController
@RequestMapping("/pulse/csv")
@Api(tags = "脉冲可视化-导入管理模块接口")
public class CsvFileController {

    @Autowired
    private CsvFileService csvFileService;

    @ApiOperation(value = "获取CSV数据文件信息接口")
    @RequestMapping(value = "/stat/{taskId}", produces = "application/json;charset=UTF-8", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务编号", paramType = "query", dataType = "long", required = true)
    })
    public JsonWrapper getCsvInfo(@PathVariable(name = "taskId") Long taskId) {
        Assert.notNull(taskId, "taskId can't be nul");
        CsvFileInfoDto csvFileInfoDto = csvFileService.getFileInfoByTaskId(taskId);
        return JsonWrapper.success(csvFileInfoDto);
    }
}
