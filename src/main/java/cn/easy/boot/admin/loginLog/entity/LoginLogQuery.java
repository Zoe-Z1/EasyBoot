package cn.easy.boot.admin.loginLog.entity;

import cn.easy.boot.common.base.BasePageQuery;
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

    @ApiModelProperty("登录浏览器")
    private String browser;

    @ApiModelProperty("操作系统")
    private String os;

    @ApiModelProperty("登录状态 SUCCESS：成功 FAIL：失败")
    private String status;

    @ApiModelProperty("在线状态 # 0：在线 1：不在线")
    private Integer isOnline;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;
}
