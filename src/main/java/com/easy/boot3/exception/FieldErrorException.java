package com.easy.boot3.exception;


import com.easy.boot3.exception.enums.SystemErrorEnum;
import com.easy.boot3.handler.GlobalExceptionHandler;

import java.util.List;

/**
 * @describe 字段效验错误异常
 * @author zoe
 *
 * @date 2023/7/23
 */
public class FieldErrorException extends BaseException {

    public FieldErrorException(List<GlobalExceptionHandler.FieldError> errors) {
        super(SystemErrorEnum.FIELD_ERROR.getCode(), errors.get(0).getMessage());
    }
}
