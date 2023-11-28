package com.easy.boot.admin.loginLog.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "LoginLog对象", description = "登录日志")
public class LoginLogCreateDTO {

    @Schema(title = "用户ID")
    private Long userId;

    @Schema(title = "用户代理信息")
    private String userAgent;

    @Schema(title = "登录用户账号")
    private String username;

    @Schema(title = "ip地址")
    private String ip;

    @Schema(title = "登录状态 SUCCESS：成功 FAIL：失败")
    private String status;

    @Schema(title = "登录token")
    private String token;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "备注")
    private String remarks;
}
