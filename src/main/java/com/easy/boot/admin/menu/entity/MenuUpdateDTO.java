package com.easy.boot.admin.menu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/07/30
* @description 菜单 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Menu对象", description = "菜单")
public class MenuUpdateDTO extends MenuCreateDTO {

    @NotNull(message = "菜单ID不能为空")
    @ApiModelProperty(required = true, value = "菜单ID")
    private Long id;

}
