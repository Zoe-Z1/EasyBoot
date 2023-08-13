package com.fast.start.common.validator;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zoe
 * @describe 自定义手机号码校验注解实现
 * @date 2023/7/22
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

//    private Pattern pattern = Pattern.compile("^0?(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$");
    private Pattern pattern = Pattern.compile("^1[3-9]\\d{9}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 空值不效验
        if (StrUtil.isBlank(value)){
            return true;
        }
        Matcher m = pattern.matcher(value);
        return m.matches();
    }
}
