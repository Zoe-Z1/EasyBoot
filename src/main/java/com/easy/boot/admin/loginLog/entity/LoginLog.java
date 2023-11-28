package com.easy.boot.admin.loginLog.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.converter.StringStatusToStringConvert;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "LoginLog对象", description = "登录日志")
public class LoginLog extends BaseEntity {

    @ExcelProperty(value = "登录用户账号")
    @Schema(title = "登录用户账号")
    @TableField("username")
    private String username;

    @ExcelProperty(value = "ip地址")
    @Schema(title = "ip地址")
    @TableField("ip")
    private String ip;

    @ExcelProperty(value = "登录浏览器")
    @Schema(title = "登录浏览器")
    @TableField("browser")
    private String browser;

    @ExcelProperty(value = "操作系统")
    @Schema(title = "操作系统")
    @TableField("os")
    private String os;

    @ExcelProperty(value = "浏览器引擎")
    @Schema(title = "浏览器引擎")
    @TableField("engine")
    private String engine;

    @ExcelProperty(value = "省")
    @Schema(title = "省")
    @TableField("pro")
    private String pro;

    @ExcelProperty(value = "省份编码")
    @Schema(title = "省份编码")
    @TableField("pro_code")
    private String proCode;

    @ExcelProperty(value = "市")
    @Schema(title = "市")
    @TableField("city")
    private String city;

    @ExcelProperty(value = "市编码")
    @Schema(title = "市编码")
    @TableField("city_code")
    private String cityCode;

    @ExcelProperty(value = "地址")
    @Schema(title = "地址")
    @TableField("addr")
    private String addr;

    @ExcelProperty(value = "登录状态", converter = StringStatusToStringConvert.class)
    @Schema(title = "登录状态 SUCCESS：成功 FAIL：失败")
    @TableField("status")
    private String status;

    @ExcelProperty(value = "备注")
    @Schema(title = "备注")
    @TableField("remarks")
    private String remarks;
}
