package com.easy.boot3.common.redisson;

import java.lang.annotation.*;

/**
 * @author zoe
 * @describe 自定义Redis分布式锁注解
 * @date 2023/8/1
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EasyLock {

    /**
     * 锁key 支持SpEL表达式
     * 格式: #{spEL} 表达式
     * 不设置时默认取 类名 ：方法名
     */
    String key() default "";

    // 锁持有时间/s
    long leaseTime() default 0;

    // 获取锁等待时长/s
    long waitTime() default 0;

}
