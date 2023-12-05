package cn.easy.boot.admin.dataDict.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @author zoe
 * @date 2023/8/1
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DataDictQuery对象", description = "数据字典")
public class ListQuery {

    @NotBlank(message = "字典域编码不能为空")
    @ApiModelProperty(required = true, value = "字典域编码")
    private String code;
}
