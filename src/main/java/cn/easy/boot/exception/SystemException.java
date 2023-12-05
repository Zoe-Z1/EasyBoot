package cn.easy.boot.exception;

import cn.easy.boot.exception.enums.SystemErrorEnum;

/**
 * @describe 系统异常
 * @author zoe
 *
 * @date 2023/7/21
 */
public class SystemException extends BaseException {

    public SystemException() {
        super(SystemErrorEnum.SYSTEM_ERROR.getCode(), SystemErrorEnum.SYSTEM_ERROR.getMessage());
    }

    public SystemException(SystemErrorEnum systemError) {
        super(systemError.getCode(), systemError.getMessage());
    }


    public SystemException(String message) {
        super(SystemErrorEnum.SYSTEM_ERROR.getCode(), message);
    }

    public SystemException(Integer code, String message) {
        super(code, message);
    }

}
