package com.easy.boot.common.generator.template;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.entity.ImportExcelError;
import com.easy.boot.common.excel.entity.ImportVO;
import com.easy.boot.common.excel.entity.UploadDTO;
import com.easy.boot.common.excel.handler.ExportExcelErrorCellWriteHandler;
import com.easy.boot.common.excel.handler.ExportExcelSelectCellWriteHandler;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
import com.easy.boot.common.generator.config.GlobalConfig;
import com.easy.boot.common.generator.config.TemplateConfig;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.easy.boot.utils.BeanUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
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

    private String remarks;

    private String moduleName;

    private Class<?> superClass;

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
            moduleName = GenConstant.MODULE_CONTROLLER;
        }
        return moduleName;
    }

    @Override
    protected Class<?> getSuperClass() {
        if (superClass == null) {
            return BaseController.class;
        } else {
            return superClass;
        }
    }

    @Override
    protected String getTemplateName() {
        return GenConstant.CONTROLLER_TEMPLATE_NAME;
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
        GlobalConfig global = buildDataMap.getGlobalConfig();
        TemplateConfig template = buildDataMap.getTemplateConfig();
        MetaTable metaTable = buildDataMap.getMetaTable();
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
        String permission = metaTable.getName().replaceAll("_", ":");
        if (StrUtil.isNotEmpty(metaTable.getUiModuleName())) {
            permission = String.join(":", metaTable.getUiModuleName(), permission);
        }
        buildDataMap.put(GenConstant.DATA_MAP_KEY_CLASS_NAME, className);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_SERVICE_NAME, serviceName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_SERVICE_CAMEL_NAME, serviceCamelName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENTITY_NAME, entityName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_ENTITY_CAMEL_NAME, entityCamelName);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PERMISSION, permission);
        if (template.getCreateDTO().isEnable()) {
            String createDTOName = template.getCreateDTO().getFileName(javaName).replace(GenConstant.SUFFIX, "");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_CREATE_DTO_NAME, createDTOName);
        }
        if (template.getUpdateDTO().isEnable()) {
            String updateDTOName = template.getUpdateDTO().getFileName(javaName).replace(GenConstant.SUFFIX, "");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_UPDATE_DTO_NAME, updateDTOName);
        }
        if (template.getQuery().isEnable()) {
            String queryName = template.getQuery().getFileName(javaName).replace(GenConstant.SUFFIX, "");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_QUERY_NAME, queryName);
        }
        if (template.getVo().isEnable()) {
            String voName = template.getVo().getFileName(javaName).replace(GenConstant.SUFFIX, "");
            buildDataMap.put(GenConstant.DATA_MAP_KEY_VO_NAME, voName);
        }
        if (getSuperClass() != null) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_SUPER_NAME, getSuperClass().getSimpleName());
        }
        if (global.getEnableImport()) {
            buildDataMap.put(GenConstant.DATA_MAP_KEY_IMPORT_EXCEL_ERROR_NAME, ImportExcelError.class.getSimpleName());
            buildDataMap.put(GenConstant.DATA_MAP_KEY_IMPORT_VO_NAME, ImportVO.class.getSimpleName());
        }
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GlobalConfig global = (GlobalConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_GLOBAL);
        AnnotationConfig annotation = (AnnotationConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_ANNOTATION);
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        Set<String> pkgs = new HashSet<>();
        if (annotation.getEnableLog()) {
            pkgs.add(EasyLog.class.getName());
            pkgs.add(OperateTypeEnum.class.getName());
        }
        if (global.getEnableImport()) {
            pkgs.add(UploadDTO.class.getName());
            pkgs.add(EasyExcel.class.getName());
            pkgs.add(Assert.class.getName());
            pkgs.add(ArrayList.class.getName());
            pkgs.add(Collections.class.getName());
            pkgs.add(ImportExcelError.class.getName());
            pkgs.add(ExportExcelErrorCellWriteHandler.class.getName());
            pkgs.add(ExportExcelSelectCellWriteHandler.class.getName());
            pkgs.add(ImportVO.class.getName());
            pkgs.add(IOException.class.getName());
            pkgs.add(ByteArrayOutputStream.class.getName());
            pkgs.add(Base64.class.getName());
        }
        if (global.getEnableExport()) {
            pkgs.add(EasyExcel.class.getName());
            pkgs.add(ExcelWriter.class.getName());
            pkgs.add(WriteSheet.class.getName());
            pkgs.add(IOException.class.getName());
            pkgs.add(ExportExcelSelectCellWriteHandler.class.getName());
        }
        if (getSuperClass() != null) {
            pkgs.add(getSuperClass().getName());
        }
        pkgs.add(EasyNoRepeatSubmit.class.getName());
        pkgs.add(SaCheckPermission.class.getName());
        pkgs.add(Api.class.getName());
        pkgs.add(ApiOperation.class.getName());
        pkgs.add(ApiOperationSupport.class.getName());
        pkgs.add(Validated.class.getName());
        pkgs.add(Resource.class.getName());
        pkgs.add(Result.class.getName());
        pkgs.add(IPage.class.getName());
        pkgs.add(List.class.getName());
        pkgs.add("lombok.extern.slf4j.Slf4j");
        pkgs.add("org.springframework.web.bind.annotation.*");
        String pkgName = String.join(".", global.getPackageName(), metaTable.getModuleName());
        String serviceName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_SERVICE_NAME);
        String entityName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_ENTITY_NAME);
        ServiceTemplate serviceTemplate = buildDataMap.getTemplateConfig().getService();
        EntityTemplate entityTemplate = buildDataMap.getTemplateConfig().getEntity();
        CreateDTOTemplate createDTOTemplate = buildDataMap.getTemplateConfig().getCreateDTO();
        UpdateDTOTemplate updateDTOTemplate = buildDataMap.getTemplateConfig().getUpdateDTO();
        QueryTemplate queryTemplate = buildDataMap.getTemplateConfig().getQuery();
        VOTemplate voTemplate = buildDataMap.getTemplateConfig().getVo();
        String servicePkgName = String.join(".", pkgName, serviceTemplate.getModuleName(), serviceName);
        String entityPkgName = String.join(".", pkgName, entityTemplate.getModuleName(), entityName);
        pkgs.add(servicePkgName);
        pkgs.add(entityPkgName);
        if (createDTOTemplate.isEnable()) {
            String createDTOName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_CREATE_DTO_NAME);
            String createDTOPkgName = String.join(".", pkgName, createDTOTemplate.getModuleName(), createDTOName);
            pkgs.add(createDTOPkgName);
        }
        if (updateDTOTemplate.isEnable()) {
            String updateDTOName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_UPDATE_DTO_NAME);
            String updateDTOPkgName = String.join(".", pkgName, updateDTOTemplate.getModuleName(), updateDTOName);
            pkgs.add(updateDTOPkgName);
        }
        if (queryTemplate.isEnable()) {
            String queryName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_QUERY_NAME);
            String queryPkgName = String.join(".", pkgName, queryTemplate.getModuleName(), queryName);
            pkgs.add(queryPkgName);
        }
        if (voTemplate.isEnable()) {
            String voName = buildDataMap.getString(GenConstant.DATA_MAP_KEY_VO_NAME);
            String voPkgName = String.join(".", pkgName, voTemplate.getModuleName(), voName);
            pkgs.add(voPkgName);
            pkgs.add(BeanUtil.class.getName());
        }
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put(GenConstant.DATA_MAP_KEY_PKGS, list);
    }
}
