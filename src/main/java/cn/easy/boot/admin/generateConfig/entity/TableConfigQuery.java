package cn.easy.boot.admin.generateConfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * @author zoe
 * @date 2023/9/10
 * @description
 */
@ApiModel(value = "代码生成Table参数配置查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class TableConfigQuery {

    @NotEmpty(message = "表名称不能为空")
    @ApiModelProperty(required = true, value = "表名称")
    private String tableName;

}
