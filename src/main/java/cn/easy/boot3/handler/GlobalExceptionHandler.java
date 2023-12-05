package cn.easy.boot3.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.exception.*;
import cn.easy.boot3.exception.enums.SystemErrorEnum;
import cn.easy.boot3.exception.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zoe
 *
 * @describe 全局异常处理器
 * @date 2023/7/21
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 运行时异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result exceptionHandler(RuntimeException e, HttpServletRequest request) {
        log.error("运行时异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(new SystemException(),request.getRequestURI(),request.getMethod());
    }

    /**
     * 404找不到资源异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result exceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("找不到资源异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(new NotFountException(),request.getRequestURI(),request.getMethod());
    }

    /**
     * 文件大小超过限制
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result exceptionHandler(MaxUploadSizeExceededException e,HttpServletRequest request) {
        log.error("文件大小超过限制，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(new FileException(SystemErrorEnum.UPLOAD_MAX_SIZE_EXCEEDED_ERROR),request.getRequestURI(),request.getMethod());
    }

    /**
     * Assert断言异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result exceptionHandler(IllegalArgumentException e,HttpServletRequest request) {
        log.error("Assert断言异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("", e.getMessage()));
        return new Result(new FieldErrorException(fieldErrors),request.getRequestURI(),request.getMethod());
    }

    /**
     * 拦截未定义的异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e,HttpServletRequest request) {
        log.error("未定义异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(new SystemException(e.getMessage()),request.getRequestURI(),request.getMethod());
    }

    /**
     * 自定义基类异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result exceptionHandler(BaseException e, HttpServletRequest request) {
        log.error("自定义异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(e,request.getRequestURI(),request.getMethod());
    }

    /**
     * 自定义系统异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public Result exceptionHandler(SystemException e,HttpServletRequest request) {
        log.error("系统异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(e,request.getRequestURI(),request.getMethod());
    }

    /**
     * Json解析异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result exceptionHandler(HttpMessageNotReadableException e,HttpServletRequest request) {
        log.error("JSON解析异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(new SystemException(SystemErrorEnum.JSON_PARSE_ERROR) ,request.getRequestURI(),request.getMethod());
    }

    /**
     * 未登录异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    public Result exceptionHandler(NotLoginException e,HttpServletRequest request) {
        log.error("未登录异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(new SystemException(SystemErrorEnum.AUTHENTICATION_FAILURE) ,request.getRequestURI(),request.getMethod());
    }

    /**
     * 无菜单权限异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NotPermissionException.class)
    public Result exceptionHandler(NotPermissionException e,HttpServletRequest request) {
        log.error("无菜单权限异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(new SystemException(SystemErrorEnum.NO_ACCESS_PERMISSION) ,request.getRequestURI(),request.getMethod());
    }

    /**
     * 无角色权限异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NotRoleException.class)
    public Result exceptionHandler(NotRoleException e,HttpServletRequest request) {
        log.error("无角色权限异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(new SystemException(SystemErrorEnum.NO_ACCESS_PERMISSION) ,request.getRequestURI(),request.getMethod());
    }

    /**
     * 自定义业务异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result exceptionHandler(BusinessException e,HttpServletRequest request) {
        log.error("业务异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        return new Result(e,request.getRequestURI(),request.getMethod());
    }

    /**
     * 字段校验异常 --> 简单效验异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result exceptionHandler(MethodArgumentNotValidException e,HttpServletRequest request) {
        BindingResult result = e.getBindingResult();
        return validationResult(result,request);
    }

    /**
     * 字段校验异常 --> 复杂效验异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result exceptionHandler(BindException e,HttpServletRequest request) {
        BindingResult result = e.getBindingResult();
        return validationResult(result,request);
    }

    /**
     * 单个参数校验异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result exceptionHandler(ConstraintViolationException e,HttpServletRequest request) {
        log.error("字段效验异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod(),e);
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        List<FieldError> fieldErrors = new ArrayList<>();
        set.forEach(error -> {
            String field = error.getPropertyPath().toString();
            fieldErrors.add(new FieldError(field.substring(field.indexOf(".")),error.getMessage()));
        });
        return new Result(new FieldErrorException(fieldErrors),request.getRequestURI(),request.getMethod());
    }

    /**
     * 封装简单和复杂效验的公共方法
     * @param result
     * @param request
     * @return
     */
    private Result validationResult(BindingResult result,HttpServletRequest request){
        log.error("字段效验异常，异常路径： {} ，请求方式： {} ",request.getRequestURI(),request.getMethod());
        List<FieldError> fieldErrors = new ArrayList<>();
        result.getFieldErrors().forEach(error -> fieldErrors.add(new FieldError(error.getField(),error.getDefaultMessage())));
        return new Result(new FieldErrorException(fieldErrors),request.getRequestURI(),request.getMethod());
    }

    @Schema(title = "字段效验错误视图")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public
    class FieldError implements Serializable {

        private static final long serialVersionUID = 1910555358396027723L;

        @Schema(title = "字段名")
        private String field;

        @Schema(title = "错误原因")
        private String message;
    }
}
