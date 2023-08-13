package com.fast.start.admin.loginLog.entity;

import com.fast.start.common.base.BasePageQuery;
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
 * @description 登录日志 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "LoginLogQuery对象", description = "登录日志")
public class LoginLogQuery extends BasePageQuery {

    @ApiModelProperty("登录用户账号")
    private String username;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("登录浏览器")
    private String browser;

    @ApiModelProperty("操作系统")
    private String os;

    @ApiModelProperty("省")
    private String pro;

    @ApiModelProperty("省份编码")
    private String proCode;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("地址")
    private String addr;

    @ApiModelProperty("登录状态 SUCCESS：成功 FAIL：失败")
    private String status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;
}
