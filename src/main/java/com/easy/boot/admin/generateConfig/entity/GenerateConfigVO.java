package com.easy.boot.admin.generateConfig.entity;

import cn.hutool.core.util.StrUtil;
import com.easy.boot.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成参数配置返回实体
 */
@ApiModel(value = "代码生成参数配置返回实体")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateConfigVO {


    @ApiModelProperty("配置类型 1：全局配置 2：表配置")
    private Integer type;

    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("后端模块名称")
    private String moduleName;

    @ApiModelProperty("前端模块名称")
    private String uiModuleName;

    @ApiModelProperty("表描述")
    private String tableRemarks;

    @ApiModelProperty("包名")
    private String packageName;

//    @ApiModelProperty("RequestMapping 路径前缀	")
//    private String requestMappingPrefix;

    @ApiModelProperty("所属菜单")
    private Long parentMenuId;

    @ApiModelProperty("生成代码路径")
    private String outputPath;

    @ApiModelProperty("生成完代码后是否打开目录	0：打开 1：不打开")
    private Integer isOpen;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("生成导入 0：生成 1：不生成")
    private Integer enableImport;

    @ApiModelProperty("生成导出 0：生成 1：不生成")
    private Integer enableExport;

    @ApiModelProperty("生成log注解 0：生成 1：不生成")
    private Integer enableLog;

    @ApiModelProperty("开启Builder模式 0：开启 1：不开启")
    private Integer enableBuilder;

    @ApiModelProperty("生成模板json配置")
    private String templateJson;

    @ApiModelProperty("生成模板json配置转list")
    private List<Map<String, Object>> templateList;

    @ApiModelProperty("过滤表前缀 多个用,分隔")
    private String excludeTablePrefix;

    @ApiModelProperty("过滤表后缀 多个用,分隔")
    private String excludeTableSuffix;

    @ApiModelProperty("过滤实体类属性 多个用,分隔")
    private String excludeField;

    @ApiModelProperty("备注")
    private String remarks;


    public List<Map<String, Object>> getTemplateList() {
        if (StrUtil.isEmpty(templateJson)) {
            return new ArrayList<>();
        }
        Map<String, Map<String, Object>> jsonMap = JsonUtil.parse(templateJson, new TypeReference<Map<String,Map<String, Object>>>(){});
        List< Map<String, Object>> templateList = new ArrayList<>();
        for (Field field : GenerateTemplate.class.getDeclaredFields()) {
            Map<String, Object> map = jsonMap.get(field.getName());
            if (map != null) {
                map.put("key", field.getName());
                templateList.add(map);
            }
        }
        return templateList;
    }
}
