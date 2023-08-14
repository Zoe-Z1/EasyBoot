package com.easy.boot.admin.loginLog.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.converter.StringStatusToStringConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/02
* @description 登录日志 实体
*/
@ColumnWidth(20)
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("login_log")
@ApiModel(value = "LoginLog对象", description = "登录日志")
public class LoginLog extends BaseEntity {

    @ExcelProperty(value = "登录用户账号")
    @ApiModelProperty("登录用户账号")
    @TableField("username")
    private String username;

    @ExcelProperty(value = "ip地址")
    @ApiModelProperty("ip地址")
    @TableField("ip")
    private String ip;

    @ExcelProperty(value = "登录浏览器")
    @ApiModelProperty("登录浏览器")
    @TableField("browser")
    private String browser;

    @ExcelProperty(value = "操作系统")
    @ApiModelProperty("操作系统")
    @TableField("os")
    private String os;

    @ExcelProperty(value = "浏览器引擎")
    @ApiModelProperty("浏览器引擎")
    @TableField("engine")
    private String engine;

    @ExcelProperty(value = "省")
    @ApiModelProperty("省")
    @TableField("pro")
    private String pro;

    @ExcelProperty(value = "省份编码")
    @ApiModelProperty("省份编码")
    @TableField("proCode")
    private String proCode;

    @ExcelProperty(value = "市")
    @ApiModelProperty("市")
    @TableField("city")
    private String city;

    @ExcelProperty(value = "市编码")
    @ApiModelProperty("市编码")
    @TableField("cityCode")
    private String cityCode;

    @ExcelProperty(value = "地址")
    @ApiModelProperty("地址")
    @TableField("addr")
    private String addr;

    @ExcelProperty(value = "登录状态", converter = StringStatusToStringConvert.class)
    @ApiModelProperty("登录状态 SUCCESS：成功 FAIL：失败")
    @TableField("status")
    private String status;

    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;
}
