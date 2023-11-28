package com.easy.boot.admin.user.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "用户查询实体")
public class AdminUserQuery extends BasePageQuery {

    @Schema(title = "部门ID")
    private Long departmentId;

    @Schema(title = "账号")
    private String username;

    @Schema(title = "昵称")
    private String name;

    @Schema(title = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @Schema(title = "账号状态 1：正常，2：禁用")
    private Integer status;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

}
