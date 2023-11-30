package com.easy.boot3.common.noRepeatSubmit;


import java.lang.annotation.*;

/**
 * @author zoe
 * @describe 自定义忽略防重复提交注解
 * @date 2023/9/4
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyNoRepeatIgnore {

}
