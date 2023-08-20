package com.easy.boot.common.generator.config;

import cn.hutool.core.collection.CollUtil;
import com.easy.boot.common.generator.template.AbstractTemplate;
import com.easy.boot.common.generator.template.ControllerTemplate;
import com.easy.boot.common.generator.template.CreateDTOTemplate;
import com.easy.boot.common.generator.template.EntityTemplate;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 模板配置
 */
@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class TemplateConfig {

    /**
     * controller模板配置
     */
    private ControllerTemplate controller;

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
    private Boolean enableUpload;

    /**
     * 生成导出
     */
    private Boolean enableDownload;

    /**
     * 增加自定义模板
     */
    private List<AbstractTemplate> addTemplate;

    /**
     * 需要生成的模板列表
     */
    private List<AbstractTemplate> templates;


    public Boolean getEnableUpload() {
        if (enableUpload == null) {
            enableUpload = false;
        }
        return enableUpload;
    }

    public Boolean getEnableDownload() {
        if (enableDownload == null) {
            enableDownload = false;
        }
        return enableDownload;
    }

    public TemplateConfig(ControllerTemplate controller, EntityTemplate entity, CreateDTOTemplate createDTO, Boolean enableUpload, Boolean enableDownload, List<AbstractTemplate> addTemplate, List<AbstractTemplate> templates) {
        this.controller = controller;
        this.entity = entity;
        this.createDTO = createDTO;
        this.enableUpload = enableUpload;
        this.enableDownload = enableDownload;
        this.addTemplate = addTemplate;
        List<AbstractTemplate> list = new ArrayList<>();
        list.add(controller == null ? new ControllerTemplate() : controller);
        list.add(entity == null ? new EntityTemplate() : entity);
        list.add(createDTO == null ? new CreateDTOTemplate() : createDTO);
        if (CollUtil.isNotEmpty(addTemplate)) {
            list.addAll(addTemplate);
        }
        this.templates = list;
    }

    //    public static TemplateConfig.TemplateBuilder builder() {
//        return new TemplateConfig.TemplateBuilder();
//    }
//
//    @ToString
//    public static class TemplateBuilder {
//        private ControllerTemplate controller;
//        private EntityTemplate entity;
//        private CreateDTOTemplate createDTO;
//
//        TemplateBuilder() {
//        }
//
//        public TemplateConfig.TemplateBuilder controller(final ControllerTemplate controller) {
//            this.controller = controller;
//            return this;
//        }
//
//        public TemplateConfig.TemplateBuilder entity(final EntityTemplate entity) {
//            this.entity = entity;
//            return this;
//        }
//
//        public TemplateConfig.TemplateBuilder createDTO(final CreateDTOTemplate createDTO) {
//            this.createDTO = createDTO;
//            return this;
//        }
//
//        public TemplateConfig build() {
//            return new TemplateConfig(this.controller, this.entity, this.createDTO);
//        }
//    }
}
