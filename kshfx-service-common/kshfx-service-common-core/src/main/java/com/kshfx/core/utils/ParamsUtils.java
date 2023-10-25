package com.kshfx.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.*;
import java.util.Map.Entry;


@Slf4j
public class ParamsUtils {

    /**
     * 文件上传类型
     */
    private static final String CONTENT_TYPE = "multipart/form-data";

    public static SortedMap<String, Object> getParameterMap(HttpServletRequest request) {
        SortedMap<String, Object> params = new TreeMap<String, Object>();

        if (request.getContentType() != null && request.getContentType().contains(CONTENT_TYPE)) {
            return params;
        }
        Map<String, Object> formMap = ParamsUtils.getApplicationUrlencodedData(request);
        Map<String, Object> bodyMap = ParamsUtils.getApplicationJsonData(request);
        Map<String, Object> urlMap = ParamsUtils.getUrlParameters(request);
        if (!CollectionUtils.isEmpty(urlMap)) {
            params.putAll(urlMap);
        }
        if (!CollectionUtils.isEmpty(formMap)) {
            params.putAll(formMap);
        }
        if (!CollectionUtils.isEmpty(bodyMap)) {
            params.putAll(bodyMap);
        }
        return params;
    }

    public static String getAllParams(HttpServletRequest request) {
        StringBuffer paramsStr = new StringBuffer();
        SortedMap<String, Object> params = ParamsUtils.getParameterMap(request);
        params.remove("t");
        String result = "";
        if (!CollectionUtils.isEmpty(params)) {
            Iterator<Entry<String, Object>> entries = params.entrySet().iterator();
            while (entries.hasNext()) {
                Entry<String, Object> entry = entries.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if (!ObjectUtils.isEmpty(key)) {
                    paramsStr = paramsStr.append(key).append("=").append(value).append("&");
                }
            }
            result = paramsStr.substring(0, paramsStr.length() - 1);
        }
        return result;
    }

    private static Map<String, Object> getApplicationUrlencodedData(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            Map<String, String[]> formDatas = request.getParameterMap();
            if (!CollectionUtils.isEmpty(formDatas)) {
                Iterator<Entry<String, String[]>> entries = formDatas.entrySet().iterator();
                while (entries.hasNext()) {
                    Entry<String, String[]> entry = entries.next();
                    String key = entry.getKey();
                    String[] values = entry.getValue();
                    params.put(key, ObjectUtils.isEmpty(values) ? null : values[0]);
                }
            }
            return params;
        } catch (Exception e) {
            log.error("获取getApplicationUrlencodedData参数异常,参数：{}, 异常信息:{}", params, e.getMessage());
        }
        return params;
    }


    public static Map<String, Object> getUrlParameters(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            Enumeration<String> enumertaion = request.getParameterNames();
            while (enumertaion.hasMoreElements()) {
                String paramName = enumertaion.nextElement();
                String paramValue = request.getParameter(paramName);
                params.put(paramName, paramValue);
            }
        } catch (Exception e) {
            log.error("获取getUrlParameters参数异常,参数：{}, 异常信息:{}", params, e.getMessage());
        }
        return params;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getApplicationJsonData(HttpServletRequest request) {
        Map<String, Object> params = null;
        String str = "";
        String wholeStr = "";
        try {
            BufferedReader br = request.getReader();
            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            if (!StringUtils.isEmpty(wholeStr) && ParamsUtils.checkJsonFormat(wholeStr)) {
                params = JSON.parseObject(wholeStr, Map.class);
            }
        } catch (Exception e) {
            params = null;
            log.error("获取getApplicationJsonData参数异常,参数：{}, 异常信息:{}", wholeStr, e.getMessage());
        }
        return params;
    }

    private static boolean checkJsonFormat(String wholeStr) {
        boolean success = true;
        try {
            JSONObject.parseObject(wholeStr);
        } catch (Exception ex) {
            success = false;
        }
        return success;
    }
}
