package com.easy.boot3.common.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zoe
 * @date 2023/7/22
 * @description Redis操作Manager
 */
@Component
public class EasyRedisManager {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 获取锁
     * @param key
     * @param value
     * @param exp 锁过期时间 单位/ms
     * @return
     */
    public Boolean lock(String key, Object value, Long exp) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, exp, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取内容
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取String类型内容
     * @param key
     * @return
     */
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取过期时间
     * @param key
     * @return
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 根据集合获取
     * @param keys 要查询的key集合
     * @return
     */
    public List multiGet(Collection keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 将map写入缓存
     * @param map
     */
    public void multiSet(Map map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 根据集合删除
     * @param keys 要删除的key集合
     */
    public void multiDel(Collection keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 将值写入缓存，无过期时间
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将值写入缓存，设置过期时间，单位/s
     * @param key
     * @param value
     * @param exp
     */
    public void put(String key, Object value, Long exp) {
        redisTemplate.opsForValue().set(key, value, exp, TimeUnit.SECONDS);
    }

    /**
     * 将值写入缓存，设置过期时间，单位/ms
     * @param key
     * @param value
     * @param exp
     */
    public void putMs(String key, Object value, Long exp) {
        redisTemplate.opsForValue().set(key, value, exp, TimeUnit.MILLISECONDS);
    }

    /**
     * 删除缓存
     * @param key
     */
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除缓存
     * @param keys
     */
    public void remove(List<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 清除所有缓存
     */
    public void clear() {
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }

    /**
     * 往缓存中写入内容
     * @param key		缓存key
     * @param hashKey	缓存中hashKey
     * @param hashValue hash值
     */
    public void putHash(String key, Object hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 玩缓存中写入内容
     * @param key
     * @param map
     */
    public void putAllHash(String key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 读取缓存值
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHash(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 读取缓存值
     * @param key
     * @return
     */
    public Map<Object, Object> getHash(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    /**
     * 在原有缓存值基础上新增字符串到末尾
     * @param key
     * @param value
     */
    public void append(String key, String value) {
        redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 返回key所对应的value值得长度
     * @param key
     * @return
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return this.redisTemplate.opsForValue().getOperations().hasKey(key);
    }

    /**
     * 获取Redis连接信息
     * @return
     */
    public Properties info() {
        RedisConnection redisConnection = redisTemplate.getRequiredConnectionFactory().getConnection();
        return redisConnection.info();
    }
}
