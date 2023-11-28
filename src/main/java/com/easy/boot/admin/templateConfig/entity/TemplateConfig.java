package com.easy.boot.admin.templateConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板配置实体
 */
@TableName("template_config")
@Schema(title = "模板配置实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TemplateConfig extends BaseEntity {


    @Schema(title = "模板配置名称")
    @TableField("name")
    private String name;

    @Schema(title = "模板配置状态 #1：正常， 2：禁用")
    @TableField("status")
    private Integer status;

    @Schema(title = "备注")
    @TableField("remarks")
    private String remarks;

    @Schema(title = "排序")
    @TableField("sort")
    private Integer sort;
}
