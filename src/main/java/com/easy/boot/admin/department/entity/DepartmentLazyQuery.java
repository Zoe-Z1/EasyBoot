package com.easy.boot.admin.department.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

/**
 * @author zoe
 * @date 2023/09/04
 * @description 部门异步 Query
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DepartmentLazyQuery对象", description = "部门异步加载")
public class DepartmentLazyQuery {

    @Schema(title = "关键词")
    private String keyword;

    @Schema(title = "部门ID")
    private Long id;

    @Schema(title = "父级部门ID，为0则代表最上级部门")
    private Long parentId;

    @Range(min = 1, max = 2, message = "部门状态不正确")
    @Schema(title = "部门状态 1：正常 2：禁用")
    private Integer status;

}
