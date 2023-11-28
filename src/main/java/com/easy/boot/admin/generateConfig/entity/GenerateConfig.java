package com.easy.boot.admin.generateConfig.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.utils.JsonUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成参数配置实体
 */
@TableName("generate_config")
@Schema(title = "代码生成参数配置实体")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GenerateConfig extends BaseEntity {


    @Schema(title = "配置类型 1：全局配置 2：表配置")
    @TableField("type")
    @ExcelProperty(value = "配置类型 1：全局配置 2：表配置")
    private Integer type;

    @Schema(title = "表名称")
    @TableField("table_name")
    @ExcelProperty(value = "表名称")
    private String tableName;

    @Schema(title = "后端模块名称")
    @TableField("module_name")
    @ExcelProperty(value = "模块名称")
    private String moduleName;

    @Schema(title = "前端模块名称")
    @TableField("ui_module_name")
    @ExcelProperty(value = "前端模块名称")
    private String uiModuleName;

    @Schema(title = "表描述")
    @TableField("table_remarks")
    @ExcelProperty(value = "表描述")
    private String tableRemarks;

    @Schema(title = "包名")
    @TableField("package_name")
    @ExcelProperty(value = "包名")
    private String packageName;

    @Schema(title = "RequestMapping 路径前缀	")
    @TableField("request_mapping_prefix")
    @ExcelProperty(value = "RequestMapping 路径前缀	")
    private String requestMappingPrefix;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "所属菜单")
    @TableField("parent_menu_id")
    @ExcelProperty(value = "所属菜单")
    private Long parentMenuId;

    @Schema(title = "生成代码路径")
    @TableField("output_path")
    @ExcelProperty(value = "生成代码路径")
    private String outputPath;

    @Schema(title = "生成完代码后是否打开目录	0：打开 1：不打开")
    @TableField("is_open")
    @ExcelProperty(value = "生成完代码后是否打开目录	0：打开 1：不打开")
    private Integer isOpen;

    @Schema(title = "作者")
    @TableField("author")
    @ExcelProperty(value = "作者")
    private String author;

    @Schema(title = "表单中占用栅格的列数 24为默认值不显示")
    @TableField("col_span")
    private Integer colSpan;

    @Schema(title = "开启路由缓存 #0：开启，1：不开启")
    @TableField("enable_cache")
    private Integer enableCache;

    @Schema(title = "生成导入 0：生成 1：不生成")
    @TableField("enable_import")
    @ExcelProperty(value = "生成导入 0：生成 1：不生成")
    private Integer enableImport;

    @Schema(title = "生成导出 0：生成 1：不生成")
    @TableField("enable_export")
    @ExcelProperty(value = "生成导出 0：生成 1：不生成")
    private Integer enableExport;

    @Schema(title = "生成log注解 0：生成 1：不生成")
    @TableField("enable_log")
    @ExcelProperty(value = "生成log注解 0：生成 1：不生成")
    private Integer enableLog;

    @Schema(title = "开启Builder模式 0：开启 1：不开启")
    @TableField("enable_builder")
    @ExcelProperty(value = "开启Builder模式 0：开启 1：不开启")
    private Integer enableBuilder;

    @Schema(title = "生成模板json配置")
    @TableField("template_json")
    @ExcelProperty(value = "生成模板json配置")
    private String templateJson;

    @Schema(title = "过滤表前缀 多个用,分隔")
    @TableField("exclude_table_prefix")
    @ExcelProperty(value = "过滤表前缀 多个用,分隔")
    private String excludeTablePrefix;

    @Schema(title = "过滤表后缀 多个用,分隔")
    @TableField("exclude_table_suffix")
    @ExcelProperty(value = "过滤表后缀 多个用,分隔")
    private String excludeTableSuffix;

    @Schema(title = "过滤实体类属性 多个用,分隔")
    @TableField("exclude_field")
    @ExcelProperty(value = "过滤实体类属性 多个用,分隔")
    private String excludeField;

    @Schema(title = "备注")
    private String remarks;

    public static GenerateConfig defaultGlobalBuild() {
        String excludeField = "updateBy,updateUsername,updateTime,isDel";
        return GenerateConfig.builder()
                .type(1)
                .tableName("")
                .tableRemarks("")
                .uiModuleName(GenConstant.UI_MODULE_NAME)
                .packageName(GenConstant.DEFAULT_PACKAGE_NAME)
                .requestMappingPrefix(GenConstant.DEFAULT_REQUEST_MAPPING_PREFIX)
                .outputPath(GenConstant.DEFAULT_OUTPUT_PATH)
                .isOpen(0)
                .author(GenConstant.DEFAULT_AUTHOR)
                .colSpan(24)
                .enableCache(1)
                .enableImport(0)
                .enableExport(0)
                .enableLog(0)
                .enableBuilder(0)
                .templateJson(JsonUtil.toJsonStr(GenerateTemplate.defaultBuild()))
                .excludeField(excludeField)
                .build();
    }
}
