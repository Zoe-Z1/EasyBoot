package com.easy.boot.admin.dataDict.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/01
* @description 数据字典 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DataDict对象", description = "数据字典")
public class DataDictCreateDTO {

    @NotNull(message = "字典域ID不能为空")
    @ApiModelProperty(required = true, value = "字典域ID")
    private Long domainId;

    @Length(min = 1, max = 50, message = "字典键在{min}-{max}个字符之间")
    @NotBlank(message = "字典键不能为空")
    @ApiModelProperty(required = true, value = "字典键")
    private String code;

    @Length(min = 1, max = 20, message = "字典标签在{min}-{max}个字符之间")
    @NotBlank(message = "字典标签不能为空")
    @ApiModelProperty(required = true, value = "字典标签")
    private String label;

    @NotNull(message = "字典状态不能为空")
    @Range(min = 1, max = 2, message = "字典状态不正确")
    @ApiModelProperty(required = true, value = "字典状态 1：正常 2：禁用")
    private Integer status;

    @ApiModelProperty(required = false, value = "备注")
    private String remarks;

    @ApiModelProperty(required = false, value = "排序")
    private Integer sort;
}
