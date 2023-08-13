package com.fast.start.admin.operationLog.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/30
* @description 操作日志 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "OperationLog对象", description = "操作日志")
public class OperationLogCreateDTO {

    @ApiModelProperty(required = false, value = "ip地址")
    private String ip;

    @ApiModelProperty(required = false, value = "调用方法路径")
    private String requestMethod;

    @ApiModelProperty(required = false, value = "请求方式")
    private String requestWay;

    @ApiModelProperty(required = false, value = "请求url")
    private String requestUrl;

    @ApiModelProperty(required = false, value = "请求参数")
    private String requestParam;

    @ApiModelProperty(required = false, value = "操作模块")
    private String operateModule;

    @ApiModelProperty(required = false, value = "操作类别")
    private String operateType;

    @ApiModelProperty(required = false, value = "操作人类别")
    private String operatorType;

    @ApiModelProperty(required = false, value = "请求开始时间/ms")
    private Long startTime;

    @ApiModelProperty(required = false, value = "请求结束时间/ms")
    private Long endTime;

    @ApiModelProperty(required = false, value = "处理时间/ms")
    private Long handleTime;

    @ApiModelProperty(required = false, value = "操作状态 SUCCESS：成功，FAIL：失败")
    private String operateStatus;

    @ApiModelProperty(required = false, value = "返回参数")
    private String responseParam;

    @ApiModelProperty(required = false, value = "错误异常")
    private String errorException;
}
