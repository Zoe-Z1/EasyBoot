package cn.easy.boot.admin.templateConfig.entity;

import cn.easy.boot.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板配置实体
 */
@TableName("template_config")
@ApiModel(value = "模板配置实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TemplateConfig extends BaseEntity {


    @ApiModelProperty("模板配置名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("模板配置状态 #1：正常， 2：禁用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;
}
