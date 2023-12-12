package cn.easy.boot3.admin.templateParamConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板参数配置创建实体
 */
@Schema(title = "模板参数配置创建实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class TemplateParamConfigCreateDTO {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "模板ID")
    @NotNull(message = "模板ID不能为空")
    private Long templateId;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "模板参数键")
    @NotBlank(message = "模板参数键不能为空")
    private String code;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "模板参数名称")
    @NotBlank(message = "模板参数名称不能为空")
    private String name;

    @Schema(title = "默认值")
    private String defaultValue;

    @Range(min = 1, max = 2, message = "是否必填状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "是否必填 # 1：必填，2：非必填")
    @NotNull(message = "是否必填不能为空")
    private Integer required;

    @Schema(title = "校验描述")
    private String message;

    @Schema(title = "占位符")
    private String placeholder;

    @Range(min = 1, max = 2, message = "模板参数状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "模板参数状态 #1：正常， 2：禁用")
    @NotNull(message = "模板参数状态不能为空")
    private Integer status;

    @Schema(title = "数据字典域ID")
    private Long domainId;

    @NotEmpty(message = "操作组件不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "操作组件 #input：文本框，textarea：文本域，select：下拉框，radio：单选框，checkbox：复选框，datetime：日期控件")
    private String optElement;

    @Schema(title = "备注")
    private String remarks;

    @Schema(title = "排序")
    private Integer sort;

}
