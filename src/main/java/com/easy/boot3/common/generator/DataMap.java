package com.easy.boot3.common.generator;

import cn.hutool.core.date.DateUtil;
import com.easy.boot3.common.generator.config.*;
import com.easy.boot3.common.generator.db.MetaTable;
import com.easy.boot3.exception.GeneratorException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zoe
 * @date 2023/8/22
 * @description 代码生成数据集，不允许put重复键
 */
public class DataMap extends HashMap<String, Object> {

    @Override
    public Object put(String key, Object value) {
        if (this.containsKey(key)) {
            throw new GeneratorException("当前键 [" + key + "] 已存在");
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        if (map.size() > this.size()) {
            for (String s : this.keySet()) {
                if (map.containsKey(s)) {
                    throw new GeneratorException("当前键 [" + s + "] 已存在");
                }
            }
        } else {
            for (String s : map.keySet()) {
                if (this.containsKey(s)) {
                    throw new GeneratorException("当前键 [" + s + "] 已存在");
                }
            }
        }
        super.putAll(map);
    }

    /**
     * 获取String类型参数
     * @param key
     * @return
     */
    public String getString(String key) {
        return (String) get(key);
    }

    /**
     * 设置并返回DataMap
     * @param config 代码生成配置
     * @return
     */
    public static DataMap getAndPutDataMap(GeneratorConfig config) {
        DataMap dataMap = new DataMap();
        dataMap.put(GenConstant.DATA_MAP_KEY_ANNOTATION, config.getAnnotationConfig());
        dataMap.put(GenConstant.DATA_MAP_KEY_GLOBAL, config.getGlobalConfig());
        dataMap.put(GenConstant.DATA_MAP_KEY_TEMPLATE, config.getTemplateConfig());
        dataMap.put(GenConstant.DATA_MAP_KEY_FILTER, config.getFilterConfig());
        dataMap.put(GenConstant.DATA_MAP_KEY_DATE, DateUtil.format(new Date(), config.getGlobalConfig().getCommentDateFormat()));
        return dataMap;
    }

    /**
     * 获取全局参数配置
     * @return
     */
    public GlobalConfig getGlobalConfig() {
        return (GlobalConfig) get(GenConstant.DATA_MAP_KEY_GLOBAL);
    }

    /**
     * 获取注解配置
     * @return
     */
    public AnnotationConfig getAnnotationConfig() {
        return (AnnotationConfig) get(GenConstant.DATA_MAP_KEY_ANNOTATION);
    }

    /**
     * 获取过滤配置
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (FilterConfig) get(GenConstant.DATA_MAP_KEY_FILTER);
    }

    /**
     * 获取模板配置
     * @return
     */
    public TemplateConfig getTemplateConfig() {
        return (TemplateConfig) get(GenConstant.DATA_MAP_KEY_TEMPLATE);
    }

    /**
     * 获取表元数据信息
     * @return
     */
    public MetaTable getMetaTable() {
        return (MetaTable) get(GenConstant.DATA_MAP_KEY_TABLE);
    }
}
