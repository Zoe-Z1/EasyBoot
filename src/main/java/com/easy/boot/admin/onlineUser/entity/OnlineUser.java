package com.easy.boot.admin.onlineUser.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/02
* @description 在线用户 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("online_user")
@Schema(title = "在线用户")
public class OnlineUser extends BaseEntity {

    @Schema(title = "登录用户账号")
    @TableField("username")
    private String username;

    @Schema(title = "ip地址")
    @TableField("ip")
    private String ip;

    @Schema(title = "登录浏览器")
    @TableField("browser")
    private String browser;

    @Schema(title = "操作系统")
    @TableField("os")
    private String os;

    @Schema(title = "浏览器引擎")
    @TableField("engine")
    private String engine;

    @Schema(title = "省")
    @TableField("pro")
    private String pro;

    @Schema(title = "省份编码")
    @TableField("pro_code")
    private String proCode;

    @Schema(title = "市")
    @TableField("city")
    private String city;

    @Schema(title = "市编码")
    @TableField("city_code")
    private String cityCode;

    @Schema(title = "地址")
    @TableField("addr")
    private String addr;

    @Schema(title = "登录token")
    @TableField("token")
    private String token;

}
