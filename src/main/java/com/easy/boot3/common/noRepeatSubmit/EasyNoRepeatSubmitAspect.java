package com.easy.boot3.common.noRepeatSubmit;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.boot3.common.redis.EasyRedisManager;
import com.easy.boot3.common.redis.RedisKeyEnum;
import com.easy.boot3.common.saToken.UserContext;
import com.easy.boot3.exception.BusinessException;
import com.easy.boot3.exception.enums.SystemErrorEnum;
import com.easy.boot3.utils.IpUtil;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author zoe
 * @describe 自定义防重复提交注解实现
 * @date 2023/7/22
 */
@Aspect
@Component
public class EasyNoRepeatSubmitAspect {

    @Resource
    private EasyRedisManager easyRedisManager;

    @Resource
    private com.easy.boot3.common.properties.EasyNoRepeatSubmit easyNoRepeatSubmit;

    @Resource
    private HttpServletRequest request;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit)")
    public void noRepeatSubmitPointCut() {
    }

    /**
     * 配置织入点
     */
    @Pointcut("execution(public * com.easy.boot3.*.*.controller..*(..))")
    public void noRepeatSubmitClassPointCut() {
    }

    /**
     * 前置通知
     */
    @Before("noRepeatSubmitPointCut() || noRepeatSubmitClassPointCut()")
    public void before(JoinPoint joinPoint) {
        // 判断注解是否开启
        if (!this.easyNoRepeatSubmit.getEnable()){
            return;
        }
        // 获取注解
        EasyNoRepeatSubmit easyNoRepeatSubmit = getNoRepeatSubmit(joinPoint);
        if (easyNoRepeatSubmit == null){
            return;
        }
        EasyNoRepeatIgnore easyNoRepeatIgnore = getNoRepeatIgnore(joinPoint);
        if (easyNoRepeatIgnore != null) {
            return;
        }
        // 获取key
        String key = getKey(joinPoint);
        // 重复提交判定时间
        long timeout = easyNoRepeatSubmit.timeout();
        if (timeout == 0){
            timeout = this.easyNoRepeatSubmit.getTimeout();
        }
        String message = easyNoRepeatSubmit.message();
        // 获取锁
        Boolean lock = easyRedisManager.lock(key, "", timeout);
        // 锁未获取成功则代表重复提交
        if (!lock) {
            throw new BusinessException(SystemErrorEnum.FORBID_REPEAT_SUBMIT.getCode(), message);
        }
    }

    /**
     * 获取key
     * @param joinPoint
     * @return
     */
    private String getKey(JoinPoint joinPoint){
        // 获取当前调用的方法路径
        String method = getMethod(joinPoint);
        String ip = IpUtil.getIp(request);
        if (StrUtil.isEmpty(ip) || NetUtil.isUnknown(ip)) {
            throw new BusinessException(SystemErrorEnum.IP_UNKNOWN);
        }
        String value = ip + ":" + method + ":" + Arrays.toString(joinPoint.getArgs());
        if (UserContext.isLogin()) {
            value = UserContext.getId() + ":" + value;
        }
        String key = RedisKeyEnum.NO_REPEAT_SUBMIT.getKey(value);
        return key;
    }

    /**
     * 获取调用方法路径
     * @param joinPoint
     * @return
     */
    private String getMethod(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    }

    /**
     * 获取注解
     */
    private EasyNoRepeatSubmit getNoRepeatSubmit(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        EasyNoRepeatSubmit easyNoRepeatSubmit = method.getAnnotation(EasyNoRepeatSubmit.class);
        if (easyNoRepeatSubmit == null) {
            easyNoRepeatSubmit = joinPoint.getTarget().getClass().getAnnotation(EasyNoRepeatSubmit.class);
        }
        return easyNoRepeatSubmit;
    }

    /**
     * 获取注解
     */
    private EasyNoRepeatIgnore getNoRepeatIgnore(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        EasyNoRepeatIgnore easyNoRepeatIgnore = method.getAnnotation(EasyNoRepeatIgnore.class);
        if (easyNoRepeatIgnore == null) {
            easyNoRepeatIgnore = joinPoint.getTarget().getClass().getAnnotation(EasyNoRepeatIgnore.class);
        }
        return easyNoRepeatIgnore;
    }
}
