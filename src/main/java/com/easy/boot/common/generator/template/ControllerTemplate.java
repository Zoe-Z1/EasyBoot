package com.easy.boot.common.generator.template;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.excel.ImportExcelError;
import com.easy.boot.common.excel.ImportVO;
import com.easy.boot.common.excel.UploadDTO;
import com.easy.boot.common.excel.handler.ImportErrorCellWriteHandler;
import com.easy.boot.common.generator.DataMap;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.config.AnnotationConfig;
import com.easy.boot.common.generator.config.GeneratorConfig;
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
            moduleName = "controller";
        }
        return moduleName;
    }

    @Override
    public Class<?> getSuperClass() {
        return BaseController.class;
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
        dataMap = super.buildDataMap(dataMap);
        // 构建类名
        buildClassName(dataMap);
        // 构建父类名
        buildSuperClassName(dataMap);
        // 构建需要导入的包
        buildPkgDataMap(dataMap);
        return dataMap;
    }

    /**
     * 构建类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildClassName(DataMap buildDataMap) {
        GeneratorConfig generator = (GeneratorConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_CONFIG);
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        String javaName = metaTable.getBeanName();
        String camelName = metaTable.getCamelName();
        String className = generator.getTemplateConfig().getController().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String serviceName = generator.getTemplateConfig().getService().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String serviceCamelName = generator.getTemplateConfig().getService().getFileName(camelName).replace(GenConstant.SUFFIX, "");
        if (serviceName.startsWith("I")) {
            serviceCamelName = generator.getTemplateConfig().getService().getFileName(camelName)
                    .replaceFirst("I", "").replace(GenConstant.SUFFIX, "");
        }
        String entityName = generator.getTemplateConfig().getEntity().getFileName(javaName).replace(GenConstant.SUFFIX, "");
        String entityCamelName = generator.getTemplateConfig().getEntity().getFileName(camelName).replace(GenConstant.SUFFIX, "");
        String createDTOName = generator.getTemplateConfig().getCreateDTO().getFileName(javaName).replace(GenConstant.SUFFIX, "");
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
    }

    /**
     * 构建父类名称
     * @param buildDataMap 已构建过的参数
     */
    private void buildSuperClassName(DataMap buildDataMap) {
        GeneratorConfig generator = (GeneratorConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_CONFIG);
        TemplateConfig template = generator.getTemplateConfig();
        if (template.getController().superClass != null) {
            buildDataMap.put("superName", template.getController().getSuperClass().getName());
        }
    }

    /**
     * 构建代码生成需要导入的包
     * @param buildDataMap 已构建过的参数
     */
    private void buildPkgDataMap(DataMap buildDataMap) {
        GeneratorConfig generator = (GeneratorConfig) buildDataMap.get(GenConstant.DATA_MAP_KEY_CONFIG);
        GlobalConfig global = generator.getGlobalConfig();
        AnnotationConfig annotation = generator.getAnnotationConfig();
        TemplateConfig template = generator.getTemplateConfig();
        MetaTable metaTable = (MetaTable) buildDataMap.get(GenConstant.DATA_MAP_KEY_TABLE);
        String pkg = global.getPackageName() + "." + metaTable.getModuleName();
        Set<String> pkgs = new HashSet<>();
        if (annotation.getEnableLog()) {
            pkgs.add(EasyLog.class.getPackage().getName());
        }
        if (template.getEnableImport()) {
            pkgs.add(UploadDTO.class.getPackage().getName());
            pkgs.add(EasyExcel.class.getPackage().getName());
            pkgs.add(Assert.class.getPackage().getName());
            pkgs.add(ArrayList.class.getPackage().getName());
            pkgs.add(Collections.class.getPackage().getName());
            pkgs.add(ImportExcelError.class.getPackage().getName());
            pkgs.add(ImportErrorCellWriteHandler.class.getPackage().getName());
            pkgs.add(ImportVO.class.getPackage().getName());
            pkgs.add(FileUtil.class.getPackage().getName());
            pkgs.add(IOException.class.getPackage().getName());
            pkgs.add(FileException.class.getPackage().getName());
        }
        if (template.getEnableExport()) {
            pkgs.add(EasyExcel.class.getPackage().getName());
            pkgs.add(ExcelWriter.class.getPackage().getName());
            pkgs.add(WriteSheet.class.getPackage().getName());
            pkgs.add(FileUtil.class.getPackage().getName());
            pkgs.add(IOException.class.getPackage().getName());
            pkgs.add(FileException.class.getPackage().getName());
        }
        if (template.getController().superClass != null) {
            pkgs.add(template.getController().getSuperClass().getPackage().getName());
        }
        pkgs.add(Api.class.getPackage().getName());
        pkgs.add(ApiOperation.class.getPackage().getName());
        pkgs.add(ApiOperationSupport.class.getPackage().getName());
        pkgs.add(Slf4j.class.getPackage().getName());
        pkgs.add(Validated.class.getPackage().getName());
        pkgs.add(Resource.class.getPackage().getName());
        pkgs.add(IPage.class.getPackage().getName());
        pkgs.add("org.springframework.web.bind.annotation.*");
        List<String> list = new ArrayList<>(pkgs);
        Collections.sort(list);
        buildDataMap.put("pkgs", list);
        pkg = pkg + "." + template.getController().getModuleName();
        buildDataMap.put("pkg", pkg);
    }
}
