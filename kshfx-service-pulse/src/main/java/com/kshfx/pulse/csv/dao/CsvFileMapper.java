package com.kshfx.pulse.csv.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kshfx.pulse.csv.bean.CsvFile;
import com.kshfx.pulse.csv.dto.CsvFileInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName CsvFileMapper
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/10/16 16:13
 * @Author ZXTD
 */
@Mapper
public interface CsvFileMapper extends BaseMapper<CsvFile> {

    CsvFileInfoDto countFileInfo(@Param("taskId") Long taskId);

    List<CsvFile> getFilesByTaskId(@Param("taskId") Long taskId);
}
