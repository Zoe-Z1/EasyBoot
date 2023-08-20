package com.easy.boot.common.generator.template;

import cn.hutool.core.map.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 模板参数配置抽象类
 */
public abstract class AbstractTemplate {

    /**
     * 包路径，与模块名拼接得到最终目录
     */
    protected String getModulePath() {
        return null;
    }

    /**
     * 生成的Java类名，需要自定义时子类重写该方法即可，
     * 返回null则根据表名生成
     */
    protected String getClassName() {
        return null;
    }

    /**
     * 父类class
     */
    protected Class<?> getSuperClass() {
        return null;
    }

    /**
     * 模板文件名
     */
    protected String getTemplateName() {
        return null;
    }

    /**
     * 是否开启代码生成
     * 子类重写该方法即可实现单独控制子类模板生成
     */
    public Boolean isEnable() {
        return true;
    }

    /**
     * 生成时是否覆盖已有文件，模板单独配置的优先级大于全局
     * 返回null则使用全局配置
     * @return isOverride
     */
    protected Boolean getIsOverride() {
        return null;
    }

    /**
     * 构建模板参数，子类模板可以重写该方法自行构建参数
     * @param dataMap  自定义的参数
     * @return dataMap
     */
    public Map<String, Object> buildDataMap(Map<String, Object> dataMap) {
        // 这里需要深拷贝一个新对象才能确保不会复用到之前的map中的属性
        Map<String, Object> buildDataMap = new HashMap<>(16);
        if (MapUtil.isNotEmpty(dataMap)) {
            buildDataMap.putAll(dataMap);
        }
        buildDataMap.put("modulePath", getModulePath());
        buildDataMap.put("className", getClassName());
        buildDataMap.put("superClass", getSuperClass());
        buildDataMap.put("templateName", getTemplateName());
        buildDataMap.put("isOverride", getIsOverride());
        return buildDataMap;
    };
}