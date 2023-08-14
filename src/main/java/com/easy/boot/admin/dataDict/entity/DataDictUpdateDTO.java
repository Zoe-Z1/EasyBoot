package com.easy.boot.admin.dataDict.entity;

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
* @date 2023/08/01
* @description 数据字典 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DataDict对象", description = "数据字典")
public class DataDictUpdateDTO extends DataDictCreateDTO {

    @NotNull(message = "字典ID不能为空")
    @ApiModelProperty(required = true, value = "字典ID")
    private Long id;

}
