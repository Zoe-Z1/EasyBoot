package com.easy.boot3.common.redisson;

import cn.hutool.core.util.StrUtil;
import com.easy.boot3.common.redis.RedisKeyEnum;
import com.easy.boot3.exception.LockException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zoe
 *
 * @describe 操作日志记录实现
 * @date 2023/8/2
 */
@Slf4j
@Aspect
@Component
public class EasyLockAspect {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private com.easy.boot3.common.properties.EasyLock globalEasyLock;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.easy.boot3.common.redisson.EasyLock)")
    public void lockPointCut() {
    }

    /**
     * 环绕通知
     */
    @Around("lockPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = this.getMethod(point);
        EasyLock annotation = method.getAnnotation(EasyLock.class);
        if (annotation == null) {
            return point.proceed(point.getArgs());
        }
        Object[] args = point.getArgs();
        com.easy.boot3.common.properties.EasyLock lock = this.getLock(annotation, method, args);
        String key = this.getKey(annotation.key(), point, method, args);
        RLock rLock = redissonClient.getLock(key);
        boolean isLock = rLock.tryLock(lock.getWaitTime(), lock.getLeaseTime(), TimeUnit.SECONDS);
        Object result = null;
        if (isLock) {
            try{
                result = point.proceed(args);
            } finally{
                if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                    rLock.unlock();
                }
            }
            return result;
        } else {
            log.error("try lock fail lock ---->>> {} ", lock);
            throw new LockException();
        }
    }

    /**
     * 获取日志注解
     */
    private Method getMethod(JoinPoint point) {
        MethodSignature sign = (MethodSignature) point.getSignature();
        return sign.getMethod();
    }

    /**
     * 获取注解的参数
     * @param easyLock
     * @param method
     * @param args
     * @return
     */
    private com.easy.boot3.common.properties.EasyLock getLock(EasyLock easyLock, Method method, Object[] args) {
        long leaseTime = easyLock.leaseTime();
        if (leaseTime <= 0) {
            leaseTime = globalEasyLock.getLeaseTime();
        }
        long waitTime = easyLock.waitTime();
        if (waitTime <= 0) {
            waitTime = globalEasyLock.getWaitTime();
        }
        return com.easy.boot3.common.properties.EasyLock.builder()
                .leaseTime(leaseTime)
                .waitTime(waitTime)
                .build();
    }

    /**
     * 获取分布式锁的key
     * @param key
     * @param point
     * @param method
     * @param args
     * @return
     */
    private String getKey(String key, ProceedingJoinPoint point, Method method, Object[] args) {
        if (StrUtil.isEmpty(key)) {
            String className = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
            key = RedisKeyEnum.LOCK.getKey(className + ":" + methodName);
        } else {
            if (key.startsWith("#")) {
                key = this.parseSpel(key, method, args);
                if (StrUtil.isNotEmpty(key)) {
                    key = RedisKeyEnum.LOCK.getKey(key);
                }
            }
        }
        return key;
    }

    /**
     * 解析SpEL表达式
     * @param key
     * @param method
     * @param args
     * @return
     */
    public String parseSpel(String key, Method method, Object[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
        String[] paramNames = discoverer.getParameterNames(method);
        if (Objects.isNull(paramNames)) {
            return key;
        }
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        Expression exp = parser.parseExpression(key);
        return exp.getValue(context, String.class);
    }

}
