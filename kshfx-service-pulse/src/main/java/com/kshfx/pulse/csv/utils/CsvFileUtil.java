package com.kshfx.pulse.csv.utils;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class CsvFileUtil {

    /**
     * 解析csv文件并转成bean
     *
     * @param file
     * @param clazz
     * @param <T>
     * @return 泛型bean集合
     */
    public static <T> List<T> getCsvDataTitle(MultipartFile file, Class<T> clazz) {
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("读取csv文件失败！");
        }
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }

    /**
     * 解析csv文件并转成bean
     *
     * @param file
     * @param clazz
     * @param <T>
     * @return 泛型bean集合
     */
    public static <T> List<T> getCsvDataNoTitle(MultipartFile file, Class<T> clazz) {
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("读取csv文件失败！");
        }
        ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }
}
