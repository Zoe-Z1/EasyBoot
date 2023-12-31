package cn.easy.boot3.admin.role.entity;

import cn.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 角色 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "RoleQuery对象", description = "角色")
public class RoleQuery extends BasePageQuery {

    @Schema(title = "角色名称")
    private String name;

    @Schema(title = "角色编码")
    private String code;

    @Range(min = 1, max = 2, message = "角色状态不正确")
    @Schema(title = "角色状态 1：正常 2：禁用")
    private Integer status;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;
}
