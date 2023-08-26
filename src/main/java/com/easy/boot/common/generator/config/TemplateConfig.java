package com.easy.boot.common.generator.config;

import cn.hutool.core.collection.CollUtil;
import com.easy.boot.common.generator.template.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 模板配置
 */
@Setter
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
     * mapper模板配置
     */
    private MapperTemplate mapper;

    /**
     * entity模板配置
     */
    private EntityTemplate entity;

    /**
     * createDTO模板配置
     */
    private CreateDTOTemplate createDTO;

    /**
     * updateDTO模板配置
     */
    private UpdateDTOTemplate updateDTO;

    /**
     * updateDTO模板配置
     */
    private QueryTemplate query;

    /**
     * 生成导入
     */
    private Boolean enableImport;

    /**
     * 生成导出
     */
    private Boolean enableExport;

    /**
     * 需要生成的模板列表
     */
    private List<AbstractTemplate> templates;

    public ControllerTemplate getController() {
        return controller == null ? new ControllerTemplate() : controller;
    }

    public ServiceTemplate getService() {
        return service == null ? new ServiceTemplate() : service;
    }

    public MapperTemplate getMapper() {
        return mapper == null ? new MapperTemplate() : mapper;
    }

    public EntityTemplate getEntity() {
        return entity == null ? new EntityTemplate() : entity;
    }

    public CreateDTOTemplate getCreateDTO() {
        return createDTO == null ? new CreateDTOTemplate() : createDTO;
    }

    public UpdateDTOTemplate getUpdateDTO() {
        return updateDTO == null ? new UpdateDTOTemplate() : updateDTO;
    }

    public QueryTemplate getQuery() {
        return query == null ? new QueryTemplate() : query;
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
        list.add(getMapper());
        list.add(getEntity());
        list.add(getCreateDTO());
        list.add(getUpdateDTO());
        list.add(getQuery());
        if (CollUtil.isNotEmpty(templates)) {
            list.addAll(templates);
        }
        this.templates = list;
        return templates;
    }

    public static TemplateConfig.TemplateConfigBuilder builder() {
        return new TemplateConfig.TemplateConfigBuilder();
    }

    @ToString
    public static class TemplateConfigBuilder {
        private ControllerTemplate controller;
        private ServiceTemplate service;
        private MapperTemplate mapper;
        private EntityTemplate entity;
        private CreateDTOTemplate createDTO;
        private UpdateDTOTemplate updateDTO;
        private QueryTemplate query;
        private Boolean enableImport;
        private Boolean enableExport;
        private List<AbstractTemplate> templates;

        TemplateConfigBuilder() {
        }

        public TemplateConfig.TemplateConfigBuilder addTemplate(AbstractTemplate... templates) {
            this.templates = Arrays.asList(templates);
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder controller(final ControllerTemplate controller) {
            this.controller = controller;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder service(final ServiceTemplate service) {
            this.service = service;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder mapper(final MapperTemplate mapper) {
            this.mapper = mapper;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder entity(final EntityTemplate entity) {
            this.entity = entity;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder createDTO(final CreateDTOTemplate createDTO) {
            this.createDTO = createDTO;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder updateDTO(final UpdateDTOTemplate updateDTO) {
            this.updateDTO = updateDTO;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder query(final QueryTemplate query) {
            this.query = query;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder enableImport(final Boolean enableImport) {
            this.enableImport = enableImport;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder enableExport(final Boolean enableExport) {
            this.enableExport = enableExport;
            return this;
        }

        public TemplateConfig build() {
            return new TemplateConfig(this.controller, this.service, this.mapper, this.entity, this.createDTO, this.updateDTO, this.query, this.enableImport, this.enableExport, this.templates);
        }
    }
}
