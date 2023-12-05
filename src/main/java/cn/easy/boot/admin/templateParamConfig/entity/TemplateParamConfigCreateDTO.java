package cn.easy.boot.admin.templateParamConfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板参数配置创建实体
 */
@ApiModel(value = "模板参数配置创建实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class TemplateParamConfigCreateDTO {

    @ApiModelProperty(required = true, value = "模板ID")
    @NotNull(message = "模板ID不能为空")
    private Long templateId;

    @ApiModelProperty(required = true, value = "模板参数键")
    @NotBlank(message = "模板参数键不能为空")
    private String code;

    @ApiModelProperty(required = true, value = "模板参数名称")
    @NotBlank(message = "模板参数名称不能为空")
    private String name;

    @ApiModelProperty(required = false, value = "默认值")
    private String defaultValue;

    @Range(min = 1, max = 2, message = "是否必填状态不正确")
    @ApiModelProperty(required = true, value = "是否必填 # 1：必填，2：非必填")
    @NotNull(message = "是否必填不能为空")
    private Integer required;

    @ApiModelProperty(required = false, value = "校验描述")
    private String message;

    @ApiModelProperty(required = false, value = "占位符")
    private String placeholder;

    @Range(min = 1, max = 2, message = "模板参数状态不正确")
    @ApiModelProperty(required = true, value = "模板参数状态 #1：正常， 2：禁用")
    @NotNull(message = "模板参数状态不能为空")
    private Integer status;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("排序")
    private Integer sort;

}
