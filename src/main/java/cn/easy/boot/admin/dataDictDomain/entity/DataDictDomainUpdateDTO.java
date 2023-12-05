package cn.easy.boot.admin.dataDictDomain.entity;

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
* @description 数据字典域 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DataDictDomain对象", description = "数据字典域")
public class DataDictDomainUpdateDTO extends DataDictDomainCreateDTO {

    @NotNull(message = "字典域ID不能为空")
    @ApiModelProperty(required = true, value = "字典域ID")
    private Long id;

}
