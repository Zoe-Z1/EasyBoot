package cn.easy.boot.admin.dataDict.entity;

import cn.easy.boot.common.base.BasePageQuery;
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
 * @description 数据字典 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DataDictQuery对象", description = "数据字典")
public class DataDictQuery extends BasePageQuery {

    @NotNull(message = "字典域ID不能为空")
    @ApiModelProperty(required = true, value = "字典域ID")
    private Long domainId;

    @ApiModelProperty("字典键")
    private String code;

    @ApiModelProperty("字典标签")
    private String label;

    @ApiModelProperty("字典状态 1：正常 2：禁用")
    private Integer status;

}
