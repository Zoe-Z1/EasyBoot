package cn.easy.boot.admin.templateParamConfig.entity;

import cn.easy.boot.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板参数配置实体
 */
@TableName("template_param_config")
@ApiModel(value = "模板参数配置实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TemplateParamConfig extends BaseEntity {


    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("模板ID")
    @TableField("template_id")
    private Long templateId;

    @ApiModelProperty("模板参数键")
    @TableField("code")
    private String code;

    @ApiModelProperty("模板参数名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("默认值")
    @TableField("default_value")
    private String defaultValue;

    @ApiModelProperty("是否必填 # 1：必填，2：非必填")
    @TableField("required")
    private Integer required;

    @ApiModelProperty("校验描述")
    @TableField("message")
    private String message;

    @ApiModelProperty("占位符")
    @TableField("placeholder")
    private String placeholder;

    @ApiModelProperty("模板参数状态 #1：正常， 2：禁用")
    @TableField("status")
    private Integer status;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("数据字典域ID")
    @TableField("domain_id")
    private Long domainId;

    @ApiModelProperty("操作组件 #input：文本框，textarea：文本域，select：下拉框，radio：单选框，checkbox：复选框，datetime：日期控件")
    @TableField("opt_element")
    private String optElement;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;
}
