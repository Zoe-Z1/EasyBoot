package com.easy.boot.admin.user.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/22
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel("用户查询实体")
public class AdminUserQuery extends BasePageQuery {

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(required = false, value = "昵称")
    private String name;

    @ApiModelProperty(required = false, value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @ApiModelProperty(value = "账号状态 1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

}
