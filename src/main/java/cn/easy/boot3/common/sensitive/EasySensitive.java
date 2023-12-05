package cn.easy.boot3.common.sensitive;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * @author zoe
 * @describe 自定义返回值脱敏注解
 * @date 2023/7/29
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = EasySensitiveJsonSerializer.class)
public @interface EasySensitive {

    //脱敏策略
    EasySensitiveStrategyEnum strategy();
}
