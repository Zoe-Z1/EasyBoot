package com.easy.boot.admin.department.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/07/29
* @description 部门 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Department对象", description = "部门")
public class DepartmentUpdateDTO extends DepartmentCreateDTO {

    @NotNull(message = "部门ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "部门ID")
    private Long id;

}
