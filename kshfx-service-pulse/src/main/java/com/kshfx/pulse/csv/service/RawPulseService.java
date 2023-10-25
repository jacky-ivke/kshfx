package com.kshfx.pulse.csv.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.kshfx.pulse.common.constant.CommonEnum;
import com.kshfx.pulse.common.dto.ChartDto;
import com.kshfx.pulse.common.utils.ChartUtil;
import com.kshfx.pulse.condition.bean.ConditionParam;
import com.kshfx.pulse.csv.bean.RawPulse;
import com.kshfx.pulse.csv.dao.RawPulseMapper;
import com.kshfx.pulse.csv.dto.CsvFileDataDto;
import com.kshfx.pulse.csv.utils.CsvFileUtil;
import com.kshfx.pulse.csv.utils.PaginationUtil;
import com.kshfx.pulse.task.bean.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.AbstractMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RawPulseService extends ServiceImpl<RawPulseMapper, RawPulse> {

    private static final int PAGE_SIZE = 1000;

    @Autowired
    private ThreadPoolTaskExecutor taskModuleExecutor;

    @Autowired
    private RawPulseMapper rawPulseMapper;

    @Autowired
    private CsvFileService csvFileService;

    public void importData(MultipartFile[] files, Long taskId) {
        for (MultipartFile file : files) {
            //解析CSV文件数据
            List<CsvFileDataDto> csvList = CsvFileUtil.getCsvDataTitle(file, CsvFileDataDto.class);
            if (CollectionUtils.isEmpty(csvList)) {
                continue;
            }
            try {
                csvList = this.setFileSequenceNo(csvList);
                Long csvId = csvFileService.uploadFile(file, csvList, taskId);
                batchHandlerCsvData(csvId, csvList, taskId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected List<CsvFileDataDto> setFileSequenceNo(List<CsvFileDataDto> csvList) {
        Integer[] arr = {1};
        return csvList.stream().peek(e -> {
            e.setSerialNo(arr[0]++);
        }).collect(Collectors.toList());
    }

    @Async
    public void batchHandlerCsvData(Long csvId, List<CsvFileDataDto> csvList, Long taskId) throws ExecutionException, InterruptedException {
        if (CollectionUtils.isEmpty(csvList)) {
            return;
        }
        //总页数
        long totalPages = PaginationUtil.getTotalPages(csvList, PAGE_SIZE);
        for (Integer page = 1; page <= totalPages; page++) {
            List<CsvFileDataDto> subList = PaginationUtil.paginate(csvList, page, PAGE_SIZE);
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                List<RawPulse> list = this.build(csvId, subList, taskId);
                //原始数据批量入库
                this.saveBatch(list);
            }, taskModuleExecutor);
            CompletableFuture.allOf(completableFuture).get();
        }
    }

    public List<RawPulse> build(Long csvId, final List<CsvFileDataDto> subList, Long taskId) {
        List<RawPulse> list = subList.stream().map(v -> {
            RawPulse bean = new RawPulse();
            BeanUtils.copyProperties(v, bean);
            bean.setCsvId(csvId);
            bean.setTaskId(taskId);
            return bean;
        }).collect(Collectors.toList());
        return list;
    }

    public IPage getRawPulseList(Long taskId, Long pageNo, Long pageSize) {
        IPage<RawPulse> pageData = rawPulseMapper.getRawPulseList(taskId, new Page<>(pageNo, pageSize));
        return pageData;
    }

    public IPage getRawPulseList(ConditionParam conditionParam, Long taskId, Long pageNo, Long pageSize) {
        IPage<RawPulse> pageData = rawPulseMapper.getRawPulseOfParamList(conditionParam, taskId, new Page<>(pageNo, pageSize));
        return pageData;
    }

    public RawPulse getNextPosVaildPulse(RawPulse abnormal) {
        RawPulse nextVaildPulse = rawPulseMapper.getNextPosVaildPulse(abnormal.getTaskId(), abnormal.getSerialNo());
        if (null == nextVaildPulse) {
            return abnormal;
        }
        return nextVaildPulse;
    }

    public RawPulse getNextTimeVaildPulse(RawPulse abnormal) {
        RawPulse nextVaildPulse = rawPulseMapper.getNextTimeVaildPulse(abnormal.getTaskId(), abnormal.getSerialNo());
        if (null == nextVaildPulse) {
            return abnormal;
        }
        return nextVaildPulse;
    }

    public List<Object[]> getChart(Long taskId){
        IPage<RawPulse> pageData = this.getRawPulseList(taskId, CommonEnum.DEF_PAGE_NO.getCode(), CommonEnum.DEF_CHART_LIMIT.getCode());
        List<Object[]> datas = ChartUtil.converListToArray(pageData.getRecords());
        return datas;
    }
}

