package cn.easy.boot3.exception;


import cn.easy.boot3.exception.enums.SystemErrorEnum;

/**
 * @describe 找不到资源，请求路径不存在
 * @author zoe
 *
 * @date 2023/7/21
 */
public class NotFountException extends SystemException {

    public NotFountException() {
        super(SystemErrorEnum.NOT_FOUND.getCode(), SystemErrorEnum.NOT_FOUND.getMessage());
    }

    public NotFountException(String message) {
        super(SystemErrorEnum.NOT_FOUND.getCode(), message);
    }
}
