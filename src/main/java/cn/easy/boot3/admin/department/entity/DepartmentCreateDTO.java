package cn.easy.boot3.admin.department.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/07/29
* @description 部门 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Department对象", description = "部门")
public class DepartmentCreateDTO {

    @NotNull(message = "父级部门不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "父级部门ID，为0则代表最上级部门")
    private Long parentId;

    @Length(min = 1, max = 20, message = "部门名称在{min}-{max}在{min}-{max}个字符之间")
    @NotNull(message = "部门名称不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "部门名称")
    private String name;

    @Range(min = 1, max = 2, message = "部门状态不正确")
    @Schema(title = "部门状态 1：正常 2：禁用")
    private Integer status;

    @Schema(title = "排序")
    private Integer sort;

    @Schema(title = "部门备注")
    private String remarks;
}
