package com.easy.boot.admin.department.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
* @author zoe
* @date 2023/10/10
* @description 部门视图 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("部门视图")
public class DepartmentVO extends Department {

    @ApiModelProperty("父级部门ID集合")
    private List<Long> parentIds;

}
