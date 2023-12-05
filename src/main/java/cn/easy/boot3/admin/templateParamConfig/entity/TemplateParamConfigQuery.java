package cn.easy.boot3.admin.templateParamConfig.entity;

import cn.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/11/09
 * @description 模板参数配置查询实体
 */
@Schema(title = "模板参数配置查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TemplateParamConfigQuery extends BasePageQuery {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "模板ID")
    @NotNull(message = "模板ID不能为空")
    private Long templateId;

    @Schema(title = "模板参数键")
    private String code;

    @Schema(title = "模板参数名称")
    private String name;

    @Schema(title = "是否必填 # 1：必填，2：非必填")
    private Integer required;

    @Schema(title = "模板配置状态 #1：正常， 2：禁用")
    private Integer status;

}
