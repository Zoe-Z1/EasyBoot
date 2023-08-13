package com.fast.start.common.log;

import com.fast.start.admin.operationLog.enums.OperateTypeEnum;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;

/**
 * @author zoe
 * @describe 自定义操作日志记录注解
 * @date 2023/7/22
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FastLog {

    /**
     * 操作模块
     */
    String module() default "UNKNOWN";

    /**
     * 操作类别
     */
    OperateTypeEnum operateType() default OperateTypeEnum.UNKNOWN;

    /**
     * 自定义操作类别，优先级高于枚举
     */
    String operateTypeStr() default "";
}
