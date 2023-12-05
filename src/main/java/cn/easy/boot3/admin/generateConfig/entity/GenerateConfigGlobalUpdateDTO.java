package cn.easy.boot3.admin.generateConfig.entity;

import cn.easy.boot3.utils.BeanUtil;
import cn.easy.boot3.utils.JsonUtil;
import cn.hutool.core.collection.CollUtil;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.tomcat.util.buf.StringUtils;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成参数配置编辑实体
 */
@Schema(title = "代码生成全局参数配置编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateConfigGlobalUpdateDTO {


    @NotBlank(message = "前端模块名称不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "前端模块名称")
    private String uiModuleName;

    @NotBlank(message = "包名不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "包名")
    private String packageName;

    @NotBlank(message = "请求前缀不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "RequestMapping 路径前缀")
    private String requestMappingPrefix;

    @Schema(title = "生成代码路径")
    private String outputPath;

    @Schema(title = "生成完代码后是否打开目录	0：打开 1：不打开")
    private Integer isOpen;

    @NotBlank(message = "作者不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "作者")
    private String author;

    @Schema(title = "表单中占用栅格的列数")
    private Integer colSpan;

    @Schema(title = "开启路由缓存 #0：开启，1：不开启")
    private Integer enableCache;

    @Range(min = 0, max = 1, message = "生成导入状态不正确")
    @Schema(title = "生成导入 0：生成 1：不生成")
    private Integer enableImport;

    @Range(min = 0, max = 1, message = "生成导出状态不正确")
    @Schema(title = "生成导出 0：生成 1：不生成")
    private Integer enableExport;

    @Range(min = 0, max = 1, message = "生成log注解状态不正确")
    @Schema(title = "生成log注解 0：生成 1：不生成")
    private Integer enableLog;

    @Range(min = 0, max = 1, message = "开启Builder模式状态不正确")
    @Schema(title = "开启Builder模式 0：开启 1：不开启")
    private Integer enableBuilder;

    @NotNull(message = "生成模板配置不能为空")
    @Schema(title = "生成模板配置")
    private GenerateTemplate templateJson;

    @Schema(title = "过滤表前缀")
    private Set<String> excludeTablePrefix;

    @Schema(title = "过滤表后缀")
    private Set<String> excludeTableSuffix;

    @Schema(title = "过滤实体类属性")
    private Set<String> excludeField;

    public static GenerateConfig toGenerateConfig(GenerateConfigGlobalUpdateDTO dto) {
        GenerateConfig generateConfig = BeanUtil.copyBean(dto, GenerateConfig.class);
        generateConfig.setTemplateJson(JsonUtil.toJsonStr(dto.getTemplateJson()));
        if (CollUtil.isNotEmpty(dto.getExcludeTablePrefix())) {
            generateConfig.setExcludeTablePrefix(StringUtils.join(dto.getExcludeTablePrefix(), ','));
        }
        if (CollUtil.isNotEmpty(dto.getExcludeTableSuffix())) {
            generateConfig.setExcludeTableSuffix(StringUtils.join(dto.getExcludeTableSuffix(), ','));
        }
        if (CollUtil.isNotEmpty(dto.getExcludeField())) {
            generateConfig.setExcludeField(StringUtils.join(dto.getExcludeField(), ','));
        }
        return generateConfig;
    }

}
