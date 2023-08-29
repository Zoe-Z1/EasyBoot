package com.easy.boot.common.generator.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.generator.template.*;
import com.easy.boot.exception.GeneratorException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
     * serviceImpl模板配置
     */
    private ServiceImplTemplate serviceImpl;

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
     * vo模板配置
     */
    private VOTemplate vo;

    /**
     * 模板引擎根路径
     */
    private String templateRootPath;

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

    public ServiceImplTemplate getServiceImpl() {
        return serviceImpl == null ? new ServiceImplTemplate() : serviceImpl;
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

    public VOTemplate getVo() {
        return vo == null ? new VOTemplate() : vo;
    }

    public String getTemplateRootPath() {
        if (StrUtil.isEmpty(templateRootPath)) {
            throw new GeneratorException("模板引擎根路径不能为空");
        }
        return templateRootPath;
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
        list.add(getServiceImpl());
        list.add(getMapper());
        list.add(getEntity());
        list.add(getCreateDTO());
        list.add(getUpdateDTO());
        list.add(getQuery());
        list.add(getVo());
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
        private ServiceImplTemplate serviceImpl;
        private MapperTemplate mapper;
        private EntityTemplate entity;
        private CreateDTOTemplate createDTO;
        private UpdateDTOTemplate updateDTO;
        private QueryTemplate query;
        private VOTemplate vo;
        private String templateRootPath;
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

        public TemplateConfig.TemplateConfigBuilder serviceImpl(final ServiceImplTemplate serviceImpl) {
            this.serviceImpl = serviceImpl;
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

        public TemplateConfig.TemplateConfigBuilder vo(final VOTemplate vo) {
            this.vo = vo;
            return this;
        }

        public TemplateConfig.TemplateConfigBuilder templateRootPath(final String templateRootPath) {
            this.templateRootPath = templateRootPath;
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
            return new TemplateConfig(this.controller, this.service, this.serviceImpl, this.mapper, this.entity, this.createDTO, this.updateDTO, this.query, this.vo, this.templateRootPath, this.enableImport, this.enableExport, this.templates);
        }
    }
}
