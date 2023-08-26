package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.ImportExcelError;
import com.easy.boot.common.excel.ImportVO;
import com.easy.boot.common.excel.UploadDTO;
import com.easy.boot.common.excel.handler.ImportErrorCellWriteHandler;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.config.TemplateConfig;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.exception.FileException;
import com.easy.boot.utils.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author zoe
 * @date 2023/8/15
 * @description 控制层模板配置
 */
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ControllerTemplate extends AbstractTemplate {

    private String moduleName;

    public Class<?> superClass;

    public String templateName;

    public String fileName;

    public Boolean enable;

    public Boolean isOverride;

    @Override
    protected String getModuleName() {
        if (StrUtil.isEmpty(moduleName)) {
            moduleName = GenConstant.MODULE_CONTROLLER;
        }
        return moduleName;
    }

    @Override
    public Class<?> getSuperClass() {
        if (superClass == null) {
            return BaseController.class;
        } else {
            return superClass;
        }
    }

    @Override
    public String getTemplateName() {
        return "controller.ftl";
    }

    @Override
    protected String getFileName(String javaName) {
        if (StrUtil.isNotEmpty(this.fileName)) {
            return this.fileName + GenConstant.SUFFIX;
        }
        return javaName + GenConstant.CONTROLLER + GenConstant.SUFFIX;
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
        // 构建类名
        buildClassName(buildDataMap);
        // 构建需要导入的包
        buildPkgDataMap(buildDataMap);
        return buildDataMap;
    }

    /**
     * 构建类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildClassName(DataMap buildDataMap) {
        TemplateConfig template = (TemplateConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_TEMPLATE);
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        String javaName = metaTable.getBeanName();
        String camelName = metaTable.getCamelName();
        String className = template.getController().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String serviceName = template.getService().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String serviceCamelName = template.getService().getFileName(camelName).replace(GenConstant.SUFFIX, "");
        if (serviceName.startsWith("I")) {
            serviceCamelName = template.getService().getFileName(camelName)
                    .replaceFirst("I", "").replace(GenConstant.SUFFIX, "");
        }
        String entityName = template.getEntity().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityCamelName = template.getEntity().getFileName(camelName).replace(GenConstant.SUFFIX, "");
        String createDTOName = template.getCreateDTO().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String updateDTOName = javaName + "UpdateDTO";
        String queryName = javaName + "Query";
        String voName = javaName + "VO";
        String voCamelName = camelName + "VO";
        buildDataMap.put("className", className);
        buildDataMap.put("serviceName", serviceName);
        buildDataMap.put("serviceCamelName", serviceCamelName);
        buildDataMap.put("entityName", entityName);
        buildDataMap.put("entityCamelName", entityCamelName);
        buildDataMap.put("createDTOName", createDTOName);
        buildDataMap.put("updateDTOName", updateDTOName);
        buildDataMap.put("queryName", queryName);
        buildDataMap.put("voName", voName);
        buildDataMap.put("voCamelName", voCamelName);
        if (getSuperClass() != null) {
            buildDataMap.put("superName", getSuperClass().getName());
        }
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = (GlobalConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_GLOBAL);
        AnnotationConfig annotation = (AnnotationConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_ANNOTATION);
        TemplateConfig template = (TemplateConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_TEMPLATE);
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        Set<String> pkgs = new HashSet<>();
        if (annotation.getEnableLog()) {
            pkgs.add(EasyLog.class.getName());
            pkgs.add(OperateTypeEnum.class.getName());
        }
        if (template.getEnableImport()) {
            pkgs.add(UploadDTO.class.getName());
            pkgs.add(EasyExcel.class.getName());
            pkgs.add(Assert.class.getName());
            pkgs.add(List.class.getName());
            pkgs.add(ArrayList.class.getName());
            pkgs.add(Collections.class.getName());
            pkgs.add(ImportExcelError.class.getName());
            pkgs.add(ImportErrorCellWriteHandler.class.getName());
            pkgs.add(ImportVO.class.getName());
            pkgs.add(FileUtil.class.getName());
            pkgs.add(IOException.class.getName());
            pkgs.add(FileException.class.getName());
        }
        if (template.getEnableExport()) {
            pkgs.add(EasyExcel.class.getName());
            pkgs.add(ExcelWriter.class.getName());
            pkgs.add(WriteSheet.class.getName());
            pkgs.add(FileUtil.class.getName());
            pkgs.add(IOException.class.getName());
            pkgs.add(FileException.class.getName());
        }
        if (getSuperClass() != null) {
            pkgs.add(getSuperClass().getName());
        }
        pkgs.add(Api.class.getName());
        pkgs.add(ApiOperation.class.getName());
        pkgs.add(ApiOperationSupport.class.getName());
        pkgs.add(Slf4j.class.getName());
        pkgs.add(Validated.class.getName());
        pkgs.add(Resource.class.getName());
        pkgs.add(Result.class.getName());
        pkgs.add(IPage.class.getName());
        pkgs.add("org.springframework.web.bind.annotation.*");
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
        String pkg = global.getPackageName() + "." + metaTable.getModuleName();
        pkg = pkg + "." + template.getController().getModuleName();
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKG, pkg);
    }
}
