package com.easy.boot.common.generator.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.generator.template.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
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
     * save.vue模板配置
     */
    private SaveVueTemplate saveVue;

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

    public SaveVueTemplate getSaveVue() {
        return saveVue == null ? new SaveVueTemplate() : saveVue;
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
        list.add(getSaveVue());
        if (CollUtil.isNotEmpty(templates)) {
            list.addAll(templates);
        }
        this.templates = list;
        return templates;
    }

}
