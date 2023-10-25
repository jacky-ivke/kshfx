package com.kshfx.pulse.csv.utils;

import java.util.List;
import java.util.stream.Collectors;

public class PaginationUtil<T> {

    public static<T> List<T> paginate(List<T> list, int page, int pageSize) {
        int totalItems = list.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);
        if (startIndex > endIndex) {
            startIndex = endIndex;
        }
        List<T> subList = list.stream()
                .skip(startIndex) //方法用于跳过前n条数据
                .limit(pageSize) //方法用于返回前n条数据，
                .collect(Collectors.toList());
        return subList;
    }

    public static <T> int getTotalPages(List<T> list, int pageSize) {
        int totalItems = list.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        return totalPages;
    }
}
