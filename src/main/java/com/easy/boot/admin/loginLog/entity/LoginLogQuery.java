package com.easy.boot.admin.loginLog.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/08/02
 * @description 登录日志 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "LoginLogQuery对象", description = "登录日志")
public class LoginLogQuery extends BasePageQuery {

    @Schema(title = "登录浏览器")
    private String browser;

    @Schema(title = "操作系统")
    private String os;

    @Schema(title = "登录状态 SUCCESS：成功 FAIL：失败")
    private String status;

    @Schema(title = "在线状态 # 0：在线 1：不在线")
    private Integer isOnline;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;
}
