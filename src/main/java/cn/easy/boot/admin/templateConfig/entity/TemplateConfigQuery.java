package cn.easy.boot.admin.templateConfig.entity;

import cn.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板配置查询实体
 */
@ApiModel(value = "模板配置查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TemplateConfigQuery extends BasePageQuery {

    @ApiModelProperty("模板配置状态 #1：正常， 2：禁用")
    private Integer status;

}
