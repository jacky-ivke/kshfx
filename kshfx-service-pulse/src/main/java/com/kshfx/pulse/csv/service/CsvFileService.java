package com.kshfx.pulse.csv.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kshfx.pulse.csv.bean.CsvFile;
import com.kshfx.pulse.csv.dao.CsvFileMapper;
import com.kshfx.pulse.csv.dto.CsvFileDataDto;
import com.kshfx.pulse.csv.dto.CsvFileInfoDto;
import com.kshfx.pulse.csv.utils.FileCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class CsvFileService extends ServiceImpl<CsvFileMapper, CsvFile> {


    @Autowired
    private CsvFileMapper csvFileMapper;

    public Long uploadFile(MultipartFile file, List<CsvFileDataDto> csvList, Long taskId) {
        CsvFile csvFile = new CsvFile();
        csvFile.setCsvName(file.getOriginalFilename());
        csvFile.setFileSize(file.getSize());
        csvFile.setCreateTime(new Timestamp(System.currentTimeMillis()));
        csvFile.setPulseNum(csvList.size());
        csvFile.setCsvUrl(this.uploadFileToServer(file));
        csvFile.setTaskId(taskId);
        csvFile.setIntervalNum(FileCountUtil.countIntervalAnomaly(csvList));
        csvFile.setPosAnomalyNum(FileCountUtil.countPositionAnomaly(csvList));
        csvFile.setTimeAnomalyNum(FileCountUtil.countInterceptTimeAnomaly(csvList));
        csvFile.setMinFrequency(FileCountUtil.countMinFrequency(csvList));
        csvFile.setMaxFrequency(FileCountUtil.countMaxFrequency(csvList));
        csvFile.setMinPosition(FileCountUtil.countMinPosition(csvList));
        csvFile.setMaxPosition(FileCountUtil.countMaxPosition(csvList));
        csvFile.setMinAmplitude(FileCountUtil.countMinAmplitude(csvList));
        csvFile.setMaxAmplitude(FileCountUtil.countMaxAmplitude(csvList));
        csvFile.setMinPulseWidth(FileCountUtil.countMinPulseWidth(csvList));
        csvFile.setMaxPulseWidth(FileCountUtil.countMaxPulseWidth(csvList));
        csvFileMapper.insert(csvFile);
        return csvFile.getId();
    }

    public String uploadFileToServer(MultipartFile file) {
        //上传至文件服务器，返回文件下载地址；
        String download = "";
        //具体操作，待定

        download = file.getOriginalFilename();
        return download;
    }

    public CsvFileInfoDto getFileInfoByTaskId(Long taskId) {
        CsvFileInfoDto dto = csvFileMapper.countFileInfo(taskId);
        return dto;
    }

    public List<CsvFile> getFilesByTaskId(Long taskId) {
        List<CsvFile> files = csvFileMapper.getFilesByTaskId(taskId);
        return files;
    }
}
