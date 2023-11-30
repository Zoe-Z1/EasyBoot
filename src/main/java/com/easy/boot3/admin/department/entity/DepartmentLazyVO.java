package com.easy.boot3.admin.department.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/29
* @description 部门 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DepartmentLazy对象", description = "部门懒加载")
public class DepartmentLazyVO extends Department {

    @Schema(title = "是否为叶子节点")
    private Boolean isLeaf;

}
