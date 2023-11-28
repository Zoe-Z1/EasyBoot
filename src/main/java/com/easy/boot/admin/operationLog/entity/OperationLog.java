package com.easy.boot.admin.operationLog.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.converter.LongTimeToStingTimeConvert;
import com.easy.boot.common.excel.converter.StringStatusToStringConvert;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/22
 * @description 操作日志
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("operation_log")
@ToString(callSuper = true)
@ColumnWidth(25)
public class OperationLog extends BaseEntity {

    @ExcelProperty(value = "ip地址")
    @Schema(title = "ip地址")
    @TableField("ip")
    private String ip;

    @ExcelProperty(value = "调用方法路径")
    @Schema(title = "调用方法路径")
    @TableField("request_method")
    private String requestMethod;

    @ExcelProperty(value = "请求方式")
    @Schema(title = "请求方式")
    @TableField("request_way")
    private String requestWay;

    @ExcelProperty(value = "请求url")
    @Schema(title = "请求url")
    @TableField("request_url")
    private String requestUrl;

    @ExcelProperty(value = "请求参数")
    @Schema(title = "请求参数")
    @TableField("request_param")
    private String requestParam;

    @ExcelProperty(value = "操作模块")
    @Schema(title = "操作模块")
    @TableField("operate_module")
    private String operateModule;

    @ExcelProperty(value = "操作类别")
    @Schema(title = "操作类别")
    @TableField("operate_type")
    private String operateType;

    @ExcelProperty(value = "操作人类别")
    @Schema(title = "操作人类别")
    @TableField("operator_type")
    private String operatorType;

    @ExcelProperty(value = "请求开始时间", converter = LongTimeToStingTimeConvert.class)
    @Schema(title = "请求开始时间/ms")
    @TableField("start_time")
    private Long startTime;

    @ExcelProperty(value = "请求结束时间", converter = LongTimeToStingTimeConvert.class)
    @Schema(title = "请求结束时间/ms")
    @TableField("end_time")
    private Long endTime;

    @ExcelProperty(value = "处理时间/ms")
    @Schema(title = "处理时间/ms")
    @TableField("handle_time")
    private Long handleTime;

    @ExcelProperty(value = "操作状态", converter = StringStatusToStringConvert.class)
    @Schema(title = "操作状态 SUCCESS：成功，FAIL：失败")
    @TableField("operate_status")
    private String operateStatus;

    @ExcelIgnore
    @Schema(title = "返回参数")
    @TableField("response_param")
    private String responseParam;

    @ExcelIgnore
    @Schema(title = "错误异常")
    @TableField("error_exception")
    private String errorException;
}
