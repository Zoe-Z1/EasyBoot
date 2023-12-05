package cn.easy.boot3.admin.role.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 角色 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Role对象", description = "角色")
public class RoleCreateDTO {

    @Schema(title = "菜单ID集合")
    private List<Long> menuIds;

    @NotBlank(message = "角色名称不能为空")
    @Length(min = 1, max = 20, message = "角色名称在{min}-{max}个字符之间")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "角色名称")
    private String name;

    @NotBlank(message = "角色编码不能为空")
    @Length(min = 1, max = 20, message = "角色编码在{min}-{max}个字符之间")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "角色编码")
    private String code;

    @NotNull(message = "角色状态不能为空")
    @Range(min = 1, max = 2, message = "角色状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "角色状态 1：正常 2：禁用")
    private Integer status;

    @Schema(title = "排序")
    private Integer sort;

    @Schema(title = "备注")
    private String remarks;
}
