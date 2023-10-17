package com.easy.boot.admin.generateConfig.entity;

import cn.hutool.core.collection.CollUtil;
import com.easy.boot.utils.BeanUtil;
import com.easy.boot.utils.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.tomcat.util.buf.StringUtils;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成参数配置编辑实体
 */
@ApiModel(value = "代码生成参数配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateConfigUpdateDTO {

    @NotEmpty(message = "表名称不能为空")
    @ApiModelProperty(required = true, value = "表名称")
    private String tableName;

    @NotBlank(message = "后端模块名称不能为空")
    @ApiModelProperty(required = true, value = "后端模块名称")
    private String moduleName;

    @NotBlank(message = "前端模块名称不能为空")
    @ApiModelProperty(required = true, value = "前端模块名称")
    private String uiModuleName;

    @NotBlank(message = "包名不能为空")
    @ApiModelProperty(required = true, value = "包名")
    private String packageName;

    @NotBlank(message = "描述不能为空")
    @ApiModelProperty(required = true, value = "描述")
    private String tableRemarks;

    @NotBlank(message = "请求前缀不能为空")
    @ApiModelProperty(required = true, value = "RequestMapping 路径前缀")
    private String requestMappingPrefix;

    @ApiModelProperty("所属菜单")
    private Long parentMenuId;

    @ApiModelProperty("生成代码路径")
    private String outputPath;

    @ApiModelProperty("生成完代码后是否打开目录	0：打开 1：不打开")
    private Integer isOpen;

    @NotBlank(message = "作者不能为空")
    @ApiModelProperty(required = true, value = "作者")
    private String author;

    @Range(min = 0, max = 1, message = "生成导入状态不正确")
    @ApiModelProperty("生成导入 0：生成 1：不生成")
    private Integer enableImport;

    @Range(min = 0, max = 1, message = "生成导出状态不正确")
    @ApiModelProperty("生成导出 0：生成 1：不生成")
    private Integer enableExport;

    @Range(min = 0, max = 1, message = "生成log注解状态不正确")
    @ApiModelProperty("生成log注解 0：生成 1：不生成")
    private Integer enableLog;

    @Range(min = 0, max = 1, message = "开启Builder模式状态不正确")
    @ApiModelProperty("开启Builder模式 0：开启 1：不开启")
    private Integer enableBuilder;

    @NotNull(message = "生成模板配置不能为空")
    @ApiModelProperty("生成模板配置")
    private GenerateTemplate templateJson;

    @ApiModelProperty("过滤实体类属性 多个用,分隔")
    private Set<String> excludeField;

    @ApiModelProperty("备注")
    private String remarks;

    public static GenerateConfig toGenerateConfig(GenerateConfigUpdateDTO dto) {
        GenerateConfig generateConfig = BeanUtil.copyBean(dto, GenerateConfig.class);
        generateConfig.setTemplateJson(JsonUtil.toJsonStr(dto.getTemplateJson()));
        if (CollUtil.isNotEmpty(dto.getExcludeField())) {
            generateConfig.setExcludeField(StringUtils.join(dto.getExcludeField(), ','));
        }
        return generateConfig;
    }
}
