package com.kshfx.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class JsonUtil {

    private final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static ObjectMapper mapper;

    private final static TypeFactory typeFactory = TypeFactory.defaultInstance();

    //序列化日期格式
    public static class DateDeserializer extends StdDeserializer<Date> {

        private static final long serialVersionUID = 1L;

        /**
         * 构造方法
         */
        public DateDeserializer() {
            this(null);
        }

        /**
         * 构造方法
         *
         * @param vc Class
         */
        public DateDeserializer(Class<?> vc) {
            super(vc);
        }


        @Override
        public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return DatePatternUtil.getPatternDate(p.getValueAsString());
        }
    }

    static {
        mapper = new ObjectMapper();
        //序列化时，序列对象所有属性
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化时，如果多了其它属性，不抛异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象，不抛异常
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转换格式，默认是时间戳
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat(DATE_PATTERN));
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new DateDeserializer());
        module.addDeserializer(java.sql.Timestamp.class, new DateDeserializers.TimestampDeserializer());
        module.addDeserializer(java.sql.Date.class, new DateDeserializers.SqlDateDeserializer());
        mapper.registerModule(module);
    }

    private JsonUtil() {

    }

    public static String object2String(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, obj);
        } catch (Exception ex) {
            log.error("【工具类】,将object对象转为json字符串发生异常：{}", ex.getMessage());
        }
        return writer.toString();
    }

    public static JSONObject object2Json(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        try {
            String str = JsonUtil.object2String(obj);
            JSONObject json = JSONObject.parseObject(str);
            return json;
        } catch (Exception ex) {
            log.error("【工具类】,将object对象转为json对象发生异常：{}", ex.getMessage());
        }
        return null;
    }

    public static JSONArray object2JsonArr(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        try {
            String str = JsonUtil.object2String(obj);
            JSONArray arr = JSONArray.parseArray(str);
            return arr;
        } catch (Exception ex) {
            log.error("【工具类】,将object对象转为json数组发生异常：{}", ex.getMessage());
        }
        return null;
    }

    public static String map2String(Map<?, ?> map) {
        StringWriter writer = new StringWriter();
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        try {
            mapper.writeValue(writer, map);
        } catch (Exception ex) {
            log.error("【工具类】,将map对象转为json字符串发生异常：{}", ex.getMessage());
        }
        return writer.toString();
    }

    public static SortedMap<String, Object> string2Map(String content) {
        if (StringUtils.isEmpty(content) || "[]".equals(content) || "{}".equals(content)) {
            return null;
        }
        try {
            JavaType type = typeFactory.constructMapType(TreeMap.class, String.class, Object.class);
            return mapper.readValue(content, type);
        } catch (Exception ex) {
            log.error("【工具类】,将String对象转为map集合发生异常：{}", ex.getMessage());
        }
        return null;
    }

    public static Map<String, Object> object2Map(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        String content = JsonUtil.object2String(obj);
        SortedMap<String, Object> map = JsonUtil.string2Map(content);
        return map;
    }

    public static <T> T[] string2Array(String content, Class<T> tClass) {
        JavaType type = mapper.getTypeFactory().constructArrayType(tClass);
        try {
            return mapper.readValue(content, type);
        } catch (Exception ex) {
            log.error("【工具类】,将String对象转为数组对象发生异常：{}", ex.getMessage());
        }
        return null;
    }

    public static <T> List<T> string2List(String content, Class<T> tClass) {
        JavaType type = mapper.getTypeFactory().constructCollectionLikeType(ArrayList.class, tClass);
        try {
            return mapper.readValue(content, type);
        } catch (Exception ex) {
            log.error("【工具类】,将String对象转为List对象发生异常：{}", ex.getMessage());
        }
        return null;
    }

    public static <T> T map2Object(Map<String, Object> map, Class<T> tClass) {
        try {
            String str = JsonUtil.map2String(map);
            return JsonUtil.string2Object(str, tClass);
        } catch (Exception ex) {
            log.error("【工具类】,将map集合转为Object对象发生异常：{}", ex.getMessage());
        }
        return null;
    }

    public static <T> T string2Object(String content, Class<T> tClass) {
        JavaType type = typeFactory.constructType(tClass);
        try {
            return mapper.readValue(content, type);
        } catch (Exception ex) {
            log.error("【工具类】,将字符串转为Object对象发生异常：{}", ex.getMessage());
        }
        return null;
    }
}
