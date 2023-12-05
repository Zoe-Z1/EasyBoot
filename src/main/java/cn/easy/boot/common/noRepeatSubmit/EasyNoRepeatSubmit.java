package cn.easy.boot.common.noRepeatSubmit;


import java.lang.annotation.*;

/**
 * @author zoe
 * @describe 自定义防重复提交注解
 *           校验机制为 账户+IP+调用方法+参数
 * @date 2023/7/22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyNoRepeatSubmit {

    // 重复提交判定时间
    long timeout() default 0L;

    // 错误提示
    String message() default "请求过于频繁";
}
