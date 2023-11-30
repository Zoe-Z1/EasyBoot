package com.easy.boot3.admin.sysConfigDomain.entity;

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
* @description 系统配置域 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SysConfigDomain对象", description = "系统配置域")
public class SysConfigDomainUpdateDTO extends SysConfigDomainCreateDTO {

    @NotNull(message = "系统配置域ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "系统配置域ID")
    private Long id;

}
