package com.easy.boot.common.generator;

import com.easy.boot.exception.GeneratorException;

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
}
