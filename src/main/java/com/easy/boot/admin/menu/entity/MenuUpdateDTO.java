package com.easy.boot.admin.menu.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

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
@Schema(title = "Menu对象", description = "菜单")
public class MenuUpdateDTO extends MenuCreateDTO {

    @NotNull(message = "菜单ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "菜单ID")
    private Long id;

}
