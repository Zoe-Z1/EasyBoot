package cn.easy.boot3.admin.templateConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板配置创建实体
 */
@Schema(title = "模板配置创建实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class TemplateConfigCreateDTO {


    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "模板配置名称")
    @NotBlank(message = "模板配置名称不能为空")
    private String name;

    @Range(min = 1, max = 2, message = "模板配置状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "模板配置状态 #1：正常， 2：禁用")
    @NotNull(message = "模板配置状态不能为空")
    private Integer status;

    @Schema(title = "备注")
    private String remarks;

    @Schema(title = "排序")
    private Integer sort;

}
