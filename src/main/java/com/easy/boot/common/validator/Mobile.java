package com.easy.boot.common.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zoe
 * @describe 自定义手机号码校验注解
 * @date 2023/7/22
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MobileValidator.class})
public @interface Mobile {

    // 错误提示
    String message() default "手机号码格式不正确";

    //分组校验时使用
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
