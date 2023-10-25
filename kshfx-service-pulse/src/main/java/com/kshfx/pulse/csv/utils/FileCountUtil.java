package com.kshfx.pulse.csv.utils;

import com.kshfx.pulse.common.utils.FileLimitUtil;
import com.kshfx.pulse.csv.dto.CsvFileDataDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class FileCountUtil {


    public static Long countIntervalAnomaly(List<CsvFileDataDto> csvList) {
        Long[] total = {1L};
        IntStream.range(1, csvList.size()).forEach(i -> {
            CsvFileDataDto element1 = csvList.get(i - 1);
            CsvFileDataDto element2 = csvList.get(i);
            Integer interceptSecond1 = element1.getInterceptSecond();
            Integer interceptSecond2 = element2.getInterceptSecond();
            Integer diff = interceptSecond2 - interceptSecond1;
            if (diff < FileLimitUtil.MIN_INTERVAL || diff > FileLimitUtil.MAX_INTERVAL) {
                total[0]++;
            }
        });
        return total[0];
    }

    public static Long countPositionAnomaly(List<CsvFileDataDto> csvList) {
        Long count = csvList.stream().filter(item -> FileLimitUtil.isRepair(item.getPosition())).count();
        return count;
    }

    public static Long countInterceptTimeAnomaly(List<CsvFileDataDto> csvList) {
        Long count = csvList.stream().filter(item -> FileLimitUtil.isZero(item.getInterceptTime())).count();
        return count;
    }

    public static Double countMinFrequency(List<CsvFileDataDto> csvList) {
        Double minFrequency = csvList.stream()
                .map(CsvFileDataDto::getFrequency)
                .min(Comparator.naturalOrder())
                .orElse(0d);
        return minFrequency;
    }

    public static Double countMaxFrequency(List<CsvFileDataDto> csvList) {
        Double minFrequency = csvList.stream()
                .map(CsvFileDataDto::getFrequency)
                .max(Comparator.naturalOrder())
                .orElse(0d);
        return minFrequency;
    }


    public static Double countMinPulseWidth(List<CsvFileDataDto> csvList) {
        Double minPulseWidth = csvList.stream()
                .map(CsvFileDataDto::getPulseWidth)
                .min(Comparator.naturalOrder())
                .orElse(0d);
        return minPulseWidth;
    }

    public static Double countMaxPulseWidth(List<CsvFileDataDto> csvList) {
        Double maxPulseWidth = csvList.stream()
                .map(CsvFileDataDto::getPulseWidth)
                .max(Comparator.naturalOrder())
                .orElse(0d);
        return maxPulseWidth;
    }

    public static Double countMinPosition(List<CsvFileDataDto> csvList) {
        Double minPosition = csvList.stream()
                .map(CsvFileDataDto::getPosition)
                .min(Comparator.naturalOrder())
                .orElse(0d);
        return minPosition;
    }

    public static Double countMaxPosition(List<CsvFileDataDto> csvList) {
        Double maxPosition = csvList.stream()
                .map(CsvFileDataDto::getPosition)
                .max(Comparator.naturalOrder())
                .orElse(0d);
        return maxPosition;
    }

    public static Double countMinAmplitude(List<CsvFileDataDto> csvList) {
        Double minAmplitude = csvList.stream()
                .map(CsvFileDataDto::getAmplitude)
                .min(Comparator.naturalOrder())
                .orElse(0d);
        return minAmplitude;
    }

    public static Double countMaxAmplitude(List<CsvFileDataDto> csvList) {
        Double maxAmplitude = csvList.stream()
                .map(CsvFileDataDto::getAmplitude)
                .max(Comparator.naturalOrder())
                .orElse(0d);
        return maxAmplitude;
    }

}
