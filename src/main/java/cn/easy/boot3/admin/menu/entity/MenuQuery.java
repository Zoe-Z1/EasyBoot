package cn.easy.boot3.admin.menu.entity;

import cn.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 菜单 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "MenuQuery对象", description = "菜单")
public class MenuQuery extends BasePageQuery {

    @NotNull(message = "上级菜单不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "父级菜单ID，为0则代表最上级菜单")
    private Long parentId;

    @Schema(title = "菜单名称")
    private String label;

    @Schema(title = "权限字符")
    private String permission;

    @Range(min = 1, max = 3, message = "菜单类型不正确")
    @Schema(title = "菜单类型 1：目录  2：菜单 3：接口")
    private Integer type;

    @Range(min = 1, max = 2, message = "菜单状态不正确")
    @Schema(title = "菜单状态 1：正常 2：禁用")
    private Integer status;

    @Range(min = 1, max = 2, message = "显示状态不正确")
    @Schema(title = "显示状态 1：显示 2：隐藏")
    private Integer showStatus;

    @Schema(title = "是否固钉 #1：固定， 2：不固定")
    private Integer affix;

    @Schema(title = "是否缓存 #1：缓存， 2：不缓存")
    private Integer cache;

    @Schema(title = "是否外链	# 1：是，2：否")
    private Integer isLink;

    @Schema(title = "高亮路由")
    private String activeMenu;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

}
