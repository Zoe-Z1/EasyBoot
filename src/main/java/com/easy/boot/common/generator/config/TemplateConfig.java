package com.easy.boot.common.generator.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.generator.template.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 模板配置
 */
@Setter
@SuperBuilder
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
     * mapper.xml模板配置
     */
    private MapperXmlTemplate xml;

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
     * query模板配置
     */
    private QueryTemplate query;

    /**
     * vo模板配置
     */
    private VOTemplate vo;

    /**
     * js模板配置
     */
    private JsTemplate js;

    /**
     * index.vue模板配置
     */
    private IndexVueTemplate indexVue;

    /**
     * 模板引擎根路径
     */
    private String templateRootPath;

    /**
     * 需要额外生成的模板列表
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

    public MapperXmlTemplate getXml() {
        return xml == null ? new MapperXmlTemplate() : xml;
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

    public JsTemplate getJs() {
        return js == null ? new JsTemplate() : js;
    }

    public IndexVueTemplate getIndexVue() {
        return indexVue == null ? new IndexVueTemplate() : indexVue;
    }

    public String getTemplateRootPath() {
        if (StrUtil.isEmpty(templateRootPath)) {
            templateRootPath = System.getProperty("user.dir") + "/src/main/resources/templates";
        }
        return templateRootPath;
    }

    public List<AbstractTemplate> getTemplates() {
        List<AbstractTemplate> list = new ArrayList<>();
        list.add(getController());
        list.add(getService());
        list.add(getServiceImpl());
        list.add(getMapper());
        list.add(getXml());
        list.add(getEntity());
        list.add(getCreateDTO());
        list.add(getUpdateDTO());
        list.add(getQuery());
        list.add(getVo());
        list.add(getJs());
        list.add(getIndexVue());
        if (CollUtil.isNotEmpty(templates)) {
            list.addAll(templates);
        }
        this.templates = list;
        return templates;
    }

//    public static TemplateConfig.TemplateConfigBuilder builder() {
//        return new TemplateConfig.TemplateConfigBuilder();
//    }
//
//    @ToString
//    public static class TemplateConfigBuilder {
//        private ControllerTemplate controller;
//        private ServiceTemplate service;
//        private ServiceImplTemplate serviceImpl;
//        private MapperTemplate mapper;
//        private MapperXmlTemplate xml;
//        private EntityTemplate entity;
//        private CreateDTOTemplate createDTO;
//        private UpdateDTOTemplate updateDTO;
//        private QueryTemplate query;
//        private VOTemplate vo;
//        private String templateRootPath;
//        private List<AbstractTemplate> templates;
//
//        TemplateConfigBuilder() {
//        }
//
//        public TemplateConfig.TemplateConfigBuilder controller(final ControllerTemplate controller) {
//            this.controller = controller;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder service(final ServiceTemplate service) {
//            this.service = service;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder serviceImpl(final ServiceImplTemplate serviceImpl) {
//            this.serviceImpl = serviceImpl;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder mapper(final MapperTemplate mapper) {
//            this.mapper = mapper;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder xml(final MapperXmlTemplate xml) {
//            this.xml = xml;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder entity(final EntityTemplate entity) {
//            this.entity = entity;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder createDTO(final CreateDTOTemplate createDTO) {
//            this.createDTO = createDTO;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder updateDTO(final UpdateDTOTemplate updateDTO) {
//            this.updateDTO = updateDTO;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder query(final QueryTemplate query) {
//            this.query = query;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder vo(final VOTemplate vo) {
//            this.vo = vo;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder templateRootPath(final String templateRootPath) {
//            this.templateRootPath = templateRootPath;
//            return this;
//        }
//
//        public TemplateConfig.TemplateConfigBuilder addTemplate(AbstractTemplate... templates) {
//            this.templates = Arrays.asList(templates);
//            return this;
//        }
//
//        public TemplateConfig build() {
//            return new TemplateConfig(this.controller, this.service, this.serviceImpl, this.mapper, this.xml, this.entity, this.createDTO, this.updateDTO, this.query, this.vo, this.templateRootPath, this.templates);
//        }
//    }
}
