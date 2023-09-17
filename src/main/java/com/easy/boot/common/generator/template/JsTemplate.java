package com.easy.boot.common.generator.template;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
import com.easy.boot.common.generator.config.FilterConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.utils.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/9/17
 * @description Js模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JsTemplate extends AbstractTemplate {

    private String remarks;

    private String moduleName;

    private String templateName;

    private String fileName;

    private Boolean enable;

    private Boolean isOverride;

    @Override
    protected String getRemarks(String tableRemarks) {
        if (StrUtil.isNotEmpty(remarks)) {
            return remarks;
        }
        return tableRemarks;
    }

    @Override
    protected String getModuleName() {
        if (StrUtil.isEmpty(moduleName)) {
            moduleName = GenConstant.MODULE_VUE;
        }
        return moduleName;
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.JS_TEMPLATE_NAME;
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.JS_SUFFIX;
        }
        String name = NamingCase.toKebabCase(javaName);
        return name + GenConstant.JS_SUFFIX;
    }

    @Override
    public Boolean isEnable() {
        if (enable == null) {
            enable = true;
        }
        return enable;
    }

    @Override
    protected Boolean getIsOverride() {
        return isOverride;
    }

    @Override
    public DataMap buildDataMap(DataMap dataMap) {
        DataMap buildDataMap = super.buildDataMap(dataMap);
        return buildDataMap;
    }

}
