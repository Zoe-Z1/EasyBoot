package com.easy.boot3.exception;

import com.easy.boot3.exception.enums.SystemErrorEnum;

/**
 * @describe 代码生成异常
 * @author zoe
 *
 * @date 2023/8/19
 */
public class GeneratorException extends BaseException {

    public GeneratorException() {
        super(SystemErrorEnum.GENERATOR_ERROR.getCode(), SystemErrorEnum.GENERATOR_ERROR.getMessage());
    }

    public GeneratorException(SystemErrorEnum systemError) {
        super(systemError.getCode(), systemError.getMessage());
    }


    public GeneratorException(String message) {
        super(SystemErrorEnum.GENERATOR_ERROR.getCode(), message);
    }

    public GeneratorException(Integer code, String message) {
        super(code, message);
    }

}
