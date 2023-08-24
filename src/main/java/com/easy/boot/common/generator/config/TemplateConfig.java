package com.easy.boot.common.generator.config;

import cn.hutool.core.collection.CollUtil;
import com.easy.boot.common.generator.template.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 模板配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateConfig {

    /**
     * controller模板配置
     */
    private ControllerTemplate controller;

    /**
     * service模板配置
     */
    private ServiceTemplate service;

    /**
     * entity模板配置
     */
    private EntityTemplate entity;

    /**
     * createDTO模板配置
     */
    private CreateDTOTemplate createDTO;

    /**
     * 生成导入
     */
    private Boolean enableImport;

    /**
     * 生成导出
     */
    private Boolean enableExport;

    /**
     * 增加自定义模板
     */
    private List<AbstractTemplate> addTemplate;

    /**
     * 需要生成的模板列表
     */
    @Setter(AccessLevel.NONE)
    private List<AbstractTemplate> templates;

    public ControllerTemplate getController() {
        return controller == null ? new ControllerTemplate() : controller;
    }

    public ServiceTemplate getService() {
        return service == null ? new ServiceTemplate() : service;
    }

    public EntityTemplate getEntity() {
        return entity == null ? new EntityTemplate() : entity;
    }

    public CreateDTOTemplate getCreateDTO() {
        return createDTO == null ? new CreateDTOTemplate() : createDTO;
    }

    public Boolean getEnableImport() {
        if (enableImport == null) {
            enableImport = false;
        }
        return enableImport;
    }
    public Boolean getEnableExport() {
        if (enableExport == null) {
            enableExport = false;
        }
        return enableExport;
    }

    public List<AbstractTemplate> getTemplates() {
        List<AbstractTemplate> list = new ArrayList<>();
        list.add(getController());
        list.add(getService());
        list.add(getEntity());
        list.add(getCreateDTO());
        if (CollUtil.isNotEmpty(addTemplate)) {
            list.addAll(addTemplate);
        }
        this.templates = list;
        return templates;
    }

    @Builder
    public TemplateConfig(ControllerTemplate controller, ServiceTemplate service, EntityTemplate entity, CreateDTOTemplate createDTO, Boolean enableImport, Boolean enableExport, List<AbstractTemplate> addTemplate) {
        this.controller = controller;
        this.service = service;
        this.entity = entity;
        this.createDTO = createDTO;
        this.enableImport = enableImport;
        this.enableExport = enableExport;
        this.addTemplate = addTemplate;
    }
}
