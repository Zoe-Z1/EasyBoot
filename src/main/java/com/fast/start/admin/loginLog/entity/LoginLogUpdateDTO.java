package com.fast.start.admin.loginLog.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/02
* @description 登录日志 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "LoginLog对象", description = "登录日志")
public class LoginLogUpdateDTO {

    @ApiModelProperty(required = false, value = "登录用户账号")
    private String username;

    @ApiModelProperty(required = false, value = "ip地址")
    private String ip;

    @ApiModelProperty(required = false, value = "登录浏览器")
    private String browser;

    @ApiModelProperty(required = false, value = "操作系统")
    private String os;

    @ApiModelProperty(required = false, value = "省")
    private String pro;

    @ApiModelProperty(required = false, value = "省份编码")
    private String proCode;

    @ApiModelProperty(required = false, value = "市")
    private String city;

    @ApiModelProperty(required = false, value = "市编码")
    private String cityCode;

    @ApiModelProperty(required = false, value = "地址")
    private String addr;

    @ApiModelProperty(required = false, value = "登录状态 SUCCESS：成功 FAIL：失败")
    private String status;

    @ApiModelProperty(required = false, value = "备注")
    private String remarks;
}
