package com.easy.boot.admin.sysConfigDomain.entity;

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
* @date 2023/07/29
* @description 系统配置域 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysConfigDomain对象", description = "系统配置域")
public class SysConfigDomainUpdateDTO extends SysConfigDomainCreateDTO {

    @NotNull(message = "系统配置域ID不能为空")
    @ApiModelProperty(required = true, value = "系统配置域ID")
    private Long id;

}
