package com.easy.boot3.common.generator.db;

import cn.hutool.core.util.StrUtil;
import com.easy.boot3.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot3.common.generator.GenConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 表元数据信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaTable {

    /**
     * 表名称
     */
    private String name;

    /**
     * JavaBean名称
     */
    private String beanName;

    /**
     * Java驼峰名称
     */
    private String camelName;

    /**
     * 后端模块名
     */
    private String moduleName;

    /**
     * 前端模块名
     */
    private String uiModuleName;

    /**
     * 所属菜单ID
     */
    private Long parentMenuId;

    /**
     * 表类型
     */
    private String type;

    /**
     * 表注释
     */
    private String remarks;

    /**
     * 表字段信息
     */
    private List<GenerateColumn> columns;

    public String getUiModuleName() {
        if (StrUtil.isEmpty(uiModuleName)) {
            uiModuleName = GenConstant.UI_MODULE_NAME;
        }
        return uiModuleName;
    }
}
