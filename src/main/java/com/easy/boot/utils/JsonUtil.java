package com.easy.boot.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.sysConfig.entity.SysConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zoe
 * @date 2023/8/10
 * @description Jackson 转换工具类
 */
@Slf4j
public class JsonUtil {

    private JsonUtil() {
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 日期格式化
     */
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        //设置空值不转换
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public static void main(String[] args) {
        List<LoginDTO> list = new ArrayList<>();
        LoginDTO loginDTO = LoginDTO.builder()
                .username("1111")
                .password("aaaa")
                .build();
        list.add(loginDTO);

        String str = toJsonStr(list, "");
        String exStr = toJsonStr(loginDTO, "password");
        System.out.println("exStr = " + exStr);
        System.out.println("str = " + str);
        List<SysConfig> sysConfigs = toList(str, SysConfig.class);
        System.out.println("sysConfigs = " + sysConfigs);
    }

    /**
     * 对象 => json字符串
     *
     * @param obj 源对象
     */
    public static <T> String toJsonStr(T obj) {
        String json = null;
        if (obj != null) {
            try {
                json = OBJECT_MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                log.error("Json转换异常 e -> ", e);
            }
        }
        return json;
    }

    /**
     * 对象 => json字符串
     *
     * @param obj 源对象
     * @param filters 要忽略的属性
     */
    public static <T> String toJsonStr(T obj, String... filters) {
        String json = null;
        if (obj != null) {
            try {
                SimpleFilterProvider filterProvider = new SimpleFilterProvider();
                filterProvider.addFilter("EasyLogFilter", SimpleBeanPropertyFilter.serializeAllExcept(filters));
                OBJECT_MAPPER.setFilterProvider(filterProvider);
                json = OBJECT_MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                log.error("Json转换异常 e -> ", e);
            }
        }
        return json;
    }

    /**
     * json字符串 => 对象
     *
     * @param json  源json串
     * @param clazz 对象类
     * @param <T>   泛型
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        T obj = null;
        if (StrUtil.isNotEmpty(json) && clazz != null) {
            try {
                obj = OBJECT_MAPPER.readValue(json, clazz);
            } catch (IOException e) {
                log.error("Json转换异常 e -> ", e);
            }
        }
        return obj;
    }

    /**
     * json字符串 => List
     *
     * @param json  源json串
     * @param clazz 对象类
     * @param <T>   泛型
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (StrUtil.isEmpty(json) || clazz == null) {
            return null;
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            log.error("Json转换异常 e -> ", e);
            return null;
        }
    }

    /**
     * 拷贝一个List
     * @param sourceEntity 来源List
     * @param targetClass 要返回的List泛型
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T,V> List<V> copyList(List<T> sourceEntity, Class<V> targetClass) {
        if (CollUtil.isEmpty(sourceEntity)) {
            return new ArrayList<>();
        }
        String json = toJsonStr(sourceEntity);
        return toList(json, targetClass);
    }

}
