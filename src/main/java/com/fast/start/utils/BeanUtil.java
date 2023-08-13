package com.fast.start.utils;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @describe Bean 工具类
 * @author zoe
 *
 * @date 2023/7/29
 */
public class BeanUtil extends BeanUtils {

    /** 工具类不能实例化 */
    private BeanUtil(){}

    /**
     * 拷贝一个bean实体
     * @param sourceEntity 来源实体
     * @param targetClass 目标类
     * @return 目标实体
     */
    public static <T,V> V copyBean(T sourceEntity, Class<V> targetClass) {
        if (sourceEntity == null) {
            return null;
        }
        V target = null;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (target == null) {
            return null;
        }
        copyProperties(sourceEntity, target);
        return target;
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
        return sourceEntity.stream().map(item -> copyBean(item, targetClass)).collect(Collectors.toList());
    }
}
