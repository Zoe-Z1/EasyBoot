package com.easy.boot.admin.dataDictDomain.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/01
* @description 数据字典域 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DataDictDomain对象", description = "数据字典域")
public class DataDictDomainUpdateDTO extends DataDictDomainCreateDTO {

    @NotNull(message = "字典域ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "字典域ID")
    private Long id;

}
