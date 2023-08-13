package com.fast.start.common.redisson;

import cn.hutool.core.util.StrUtil;
import com.fast.start.common.redis.RedisKeyEnum;
import com.fast.start.exception.LockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
public class FastLockAspect {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private com.fast.start.common.properties.FastLock globalFastLock;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.fast.start.common.redisson.FastLock)")
    public void lockPointCut() {
    }

    /**
     * 环绕通知
     */
    @Around("lockPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = this.getMethod(point);
        FastLock annotation = method.getAnnotation(FastLock.class);
        if (annotation == null) {
            return point.proceed(point.getArgs());
        }
        Object[] args = point.getArgs();
        com.fast.start.common.properties.FastLock lock = this.getLock(annotation, method, args);
        RLock rLock = redissonClient.getLock(lock.getKey());
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
     * @param fastLock
     * @param method
     * @param args
     * @return
     */
    private com.fast.start.common.properties.FastLock getLock(FastLock fastLock, Method method, Object[] args) {
        String key = fastLock.key();
        if (StrUtil.isEmpty(key)) {
            key = globalFastLock.getKey();
        } else {
            if (key.startsWith("#")) {
                key = this.parseSpel(key, method, args);
                if (StrUtil.isNotEmpty(key)) {
                    key = RedisKeyEnum.LOCK.getKey(key);
                }
            }
        }
        long leaseTime = fastLock.leaseTime();
        if (leaseTime <= 0) {
            leaseTime = globalFastLock.getLeaseTime();
        }
        long waitTime = fastLock.waitTime();
        if (waitTime <= 0) {
            waitTime = globalFastLock.getWaitTime();
        }
        return com.fast.start.common.properties.FastLock.builder()
                .key(key)
                .leaseTime(leaseTime)
                .waitTime(waitTime)
                .build();
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
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
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
