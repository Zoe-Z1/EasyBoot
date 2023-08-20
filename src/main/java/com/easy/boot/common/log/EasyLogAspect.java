package com.easy.boot.common.log;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import com.easy.boot.admin.operationLog.entity.OperationLog;
import com.easy.boot.admin.operationLog.enums.OperateStatusEnum;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.operationLog.enums.RoleTypeEnum;
import com.easy.boot.admin.operationLog.service.IOperationLogService;
import com.easy.boot.common.properties.EasyLogFilter;
import com.easy.boot.handler.EasyMetaObjectHandler;
import com.easy.boot.utils.IpUtil;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author zoe
 *
 * @describe 操作日志记录实现
 * @date 2023/7/23
 */
@Aspect
@Component
public class EasyLogAspect {

    @Resource
    private HttpServletRequest request;

    @Resource
    private com.easy.boot.common.properties.EasyLog easyLog;

    @Resource
    private EasyLogFilter filter;

    @Resource
    private IOperationLogService logService;

    private Long startTime;

    private Object[] args;

    private OperationLog operateLog;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.easy.boot.common.log.EasyLog)")
    public void logPointCut() {
    }

    /**
     * 环绕通知
     *
     * @param point
     */
    @SneakyThrows
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;
        Throwable t = null;
        try {
            args = point.getArgs();
            // 前置操作
            beforeHandle(point);
            // 执行目标方法
            result = point.proceed(args);
            // 方法正常返回处理
            afterHandle(point, null, result);
        } catch (Throwable e) {
            t = e;
            // 方法异常返回处理
            afterHandle(point, e, null);
        } finally {
            if (t != null) {
                throw t;
            }
        }
        return result;
    }

    private void beforeHandle(ProceedingJoinPoint joinPoint) {
        // 判断日志记录注解是否开启
        if (!this.easyLog.getEnable()) {
            return;
        }
        // 获取Log注解
        EasyLog easyLog = getLog(joinPoint);
        if (easyLog == null) {
            return;
        }
        startTime = DateUtil.current();
        operateLog = OperationLog.builder()
                .startTime(startTime)
                .build();
        // 处理注解上设置的参数
        handleLogParam(easyLog);
        // 处理请求参数
        handleLogRequestParam(joinPoint);
    }

    /**
     * 操作日志注解处理
     *
     * @param joinPoint
     * @param e
     * @param result
     */
    private void afterHandle(ProceedingJoinPoint joinPoint, Throwable e, Object result) {
        // 判断日志记录注解是否开启
        if (!this.easyLog.getEnable()) {
            return;
        }
        // 获取Log注解
        EasyLog easyLog = getLog(joinPoint);
        if (easyLog == null) {
            return;
        }
        Long endTime = DateUtil.current();
        Long handleTime = endTime - startTime;
        operateLog.setEndTime(endTime);
        operateLog.setHandleTime(handleTime);
        // 处理返回参数
        handleLogResponseParam(e, result);
        // 保存数据库
        logService.asyncSaveLog(operateLog);
    }

    /**
     * 处理请求参数
     *
     * @param joinPoint
     */
    private void handleLogRequestParam(JoinPoint joinPoint) {
        // ip地址
        String ip = IpUtil.getIp(request);
        operateLog.setIp(ip);
        // 调用方法路径
        String method = getMethod(joinPoint);
        operateLog.setRequestMethod(method);
        // 请求方式
        operateLog.setRequestWay(request.getMethod());
        // 请求url
        operateLog.setRequestUrl(request.getRequestURI());
        // 请求参数
        if (args != null) {
//            String reqParam = JsonUtil.toJsonStr(args);
            String reqParam = JSON.toJSONString(args, getSimplePropertyPreFilter(filter.getFields()));
            if (reqParam.length() <= easyLog.getRequestSaveMaxLength()) {
                operateLog.setRequestParam(reqParam);
            }
        }
    }

    /**
     * 处理返回参数
     *
     * @param e
     * @param result
     */
    private void handleLogResponseParam(Throwable e, Object result) {
        operateLog.setOperateStatus(String.valueOf(OperateStatusEnum.SUCCESS));
        if (e != null) {
            operateLog.setOperateStatus(String.valueOf(OperateStatusEnum.FAIL));
            // 错误异常
            String error = e.getMessage().trim();
            if (error.length() > easyLog.getErrorSaveMaxLength()) {
                error = error.substring(0, easyLog.getErrorSaveMaxLength());
            }
            operateLog.setErrorException(error);
        } else {
            // 返回参数
            if (result == null) {
                return;
            }
//            String response = JsonUtil.toJsonStr(result);
            String response = JSON.toJSONString(result, getSimplePropertyPreFilter(filter.getFields()));
            if (response.length() <= easyLog.getResponseSaveMaxLength()) {
                operateLog.setResponseParam(response);
            }
        }
    }

    private SimplePropertyPreFilter getSimplePropertyPreFilter(String[] fields) {
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getExcludes().addAll(Arrays.asList(filter.getFields()));
        return simplePropertyPreFilter;
    }

    /**
     * 获取注解中对方法的描述信息
     *
     * @param easyLog        日志注解
     */
    public void handleLogParam(EasyLog easyLog) {
        // 操作模块
        operateLog.setOperateModule(easyLog.module());
        // 操作类别
        handleOperateType(easyLog);
        // 操作人类型
        handleOperatorType();
    }

    /**
     * 处理操作类别
     * @param easyLog
     */
    private void handleOperateType(EasyLog easyLog) {
        if (StrUtil.isNotBlank(easyLog.operateTypeStr())) {
            operateLog.setOperateType(easyLog.operateTypeStr());
            return;
        }
        if (OperateTypeEnum.UNKNOWN == easyLog.operateType()) {
            operateLog.setOperateType(OperateTypeEnum.UNKNOWN.getMsg());
        } else {
            operateLog.setOperateType(easyLog.operateType().getMsg());
        }
    }

    /**
     * 处理操作人类型
     */
    private void handleOperatorType() {
        RoleTypeEnum roleType = RoleTypeEnum.UNKNOWN;
        Long createBy = EasyMetaObjectHandler.BY;
        if (StpUtil.isLogin()) {
            createBy = Long.parseLong(String.valueOf(StpUtil.getLoginId()));
            roleType = RoleTypeEnum.valueOf(StpUtil.getLoginType());
        }
        operateLog.setCreateBy(createBy);
        operateLog.setOperatorType(roleType.getMsg());
    }

    /**
     * 获取日志注解
     */
    private EasyLog getLog(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(EasyLog.class);
    }

    /**
     * 获取调用方法路径
     *
     * @param joinPoint
     * @return
     */
    private String getMethod(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "()";
    }
}