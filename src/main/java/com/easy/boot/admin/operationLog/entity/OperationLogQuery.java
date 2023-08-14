package com.easy.boot.admin.operationLog.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 操作日志 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "OperationLogQuery对象", description = "操作日志")
public class OperationLogQuery extends BasePageQuery {

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("请求方式")
    private String requestWay;

    @ApiModelProperty("请求url")
    private String requestUrl;

    @ApiModelProperty("操作模块")
    private String operateModule;

    @ApiModelProperty("操作类别")
    private String operateType;

    @ApiModelProperty("操作人类别")
    private String operatorType;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

    @ApiModelProperty("处理时间/ms")
    private Long handleTime;

    @Range(min = 1, max = 2, message = "处理时间操作符不正确")
    @ApiModelProperty("处理时间操作符 1：大于等于 2：小于等于")
    private Integer handleTimeOperator;

    @ApiModelProperty("操作状态 SUCCESS：成功，FAIL：失败")
    private String operateStatus;

}
