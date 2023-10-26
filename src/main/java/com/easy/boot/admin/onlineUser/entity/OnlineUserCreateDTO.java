package com.easy.boot.admin.onlineUser.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/02
* @description 在线用户 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("在线用户新增DTO")
public class OnlineUserCreateDTO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户代理信息")
    private String userAgent;

    @ApiModelProperty("登录用户账号")
    private String username;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("登录状态 SUCCESS：成功 FAIL：失败")
    private String status;

    @ApiModelProperty("在线状态 # 0：在线 1：不在线")
    private Integer isOnline;

    @ApiModelProperty("登录token")
    private String token;

    @ApiModelProperty(required = true, value = "备注")
    private String remarks;
}
