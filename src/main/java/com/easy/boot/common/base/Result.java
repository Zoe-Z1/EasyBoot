package com.easy.boot.common.base;


import com.easy.boot.exception.BaseException;
import com.easy.boot.exception.enums.SystemErrorEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;

/**
 * 统一返回对象
 *
 * @author zoe
 *
 * @date 2023/7/20
 */
@Getter
@ApiModel("统一返回对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -8859781532099972534L;

    @ApiModelProperty("响应状态码 200为成功，其他均为失败")
    private Integer code;

    @ApiModelProperty("响应描述")
    private String message;

    @ApiModelProperty("请求路径")
    private String path;

    @ApiModelProperty("请求方式")
    private String method;

    @ApiModelProperty("响应内容")
    private T data;

    public Result(SystemErrorEnum systemError) {
        this.code = systemError.getCode();
        this.message = systemError.getMessage();
    }

    public Result(SystemErrorEnum systemError, String message) {
        this.code = systemError.getCode();
        this.message = message;
    }

    public Result(SystemErrorEnum systemError, T data) {
        this.code = systemError.getCode();
        this.message = systemError.getMessage();
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(SystemErrorEnum systemError, String message, T data) {
        this.code = systemError.getCode();
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(BaseException e, String path, String method) {
        this(e.getCode(), e.getMessage(), path, method);
    }

    public Result(Integer code, String message, String path, String method) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.method = method;
    }

    public Result(Integer code, String message, T data, String path, String method) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.path = path;
        this.method = method;
    }

    public static <T> Result<T> r(boolean flag) {
        return flag ? success() : fail();
    }

    public static <T> Result<T> success() {
        return new Result<T>(SystemErrorEnum.SUCCESS);
    }

    public static <T> Result<T> success(String message) {
        return new Result<T>(SystemErrorEnum.SUCCESS, message);
    }

    public static <T> Result<T> success(T t) {
        return new Result<T>(SystemErrorEnum.SUCCESS, t);
    }

//    public static <T> Result success(List<T> list) {
//        PageInfo pageInfo = new PageInfo(list);
//        Page page = Page.builder()
//                .pageNum(pageInfo.getPageNum())
//                .pageSize(pageInfo.getPageSize())
//                .pageCount(pageInfo.getPages())
//                .size(pageInfo.getSize())
//                .hasPreviousPage(pageInfo.isHasPreviousPage())
//                .hasNextPage(pageInfo.isHasNextPage())
//                .total(pageInfo.getTotal())
//                .list(list)
//                .build();
//        return new Result(SystemError.SUCCESS, page);
//    }

    public static <T> Result<T> success(Integer code,String message) {
        return new Result<T>(code, message);
    }

    public static <T> Result<T> success(Integer code,T t) {
        return new Result<T>(code, t);
    }

    public static <T> Result<T> success(String message, T t) {
        return new Result<T>(SystemErrorEnum.SUCCESS, message, t);
    }

    public static <T> Result<T> success(Integer code,String message, T t) {
        return new Result<T>(code, message, t);
    }

    public static <T> Result<T> fail() {
        return new Result<T>(SystemErrorEnum.FAIL);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<T>(SystemErrorEnum.FAIL, message);
    }

    public static <T> Result<T> fail(T t) {
        return new Result<T>(SystemErrorEnum.FAIL, t);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<T>(code, message);
    }

    public static <T> Result<T> fail(Integer code, T t) {
        return new Result<T>(code, t);
    }

    public static <T> Result<T> fail(String message, T t) {
        return new Result<T>(SystemErrorEnum.FAIL, message, t);
    }

    public static <T> Result<T> fail(Integer code, String message, T t) {
        return new Result<T>(code, message, t);
    }

    public static <T> Result<T> fail(SystemErrorEnum systemError) {
        return new Result<T>(systemError);
    }

    public static <T> Result<T> fail(SystemErrorEnum systemError, T t) {
        return new Result<T>(systemError, t);
    }
}
