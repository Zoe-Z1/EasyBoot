package com.easy.boot.admin.templateConfig.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.templateConfig.entity.TemplateConfig;
import com.easy.boot.admin.templateConfig.entity.TemplateConfigCreateDTO;
import com.easy.boot.admin.templateConfig.entity.TemplateConfigQuery;
import com.easy.boot.admin.templateConfig.entity.TemplateConfigUpdateDTO;

import java.util.List;

/**
* @author zoe
* @date 2023/11/09
* @description 模板配置 服务类
*/
public interface ITemplateConfigService extends IService<TemplateConfig> {


    /**
     * 获取全部模板配置
     * @return
     */
    List<TemplateConfig> selectAll();

    /**
     * 分页查询模板配置
     * @param query
     * @return
     */
    IPage<TemplateConfig> selectPage(TemplateConfigQuery query);

    /**
     * 创建模板配置
     * @param dto
     * @return
     */
    Boolean create(TemplateConfigCreateDTO dto);

    /**
     * 编辑模板配置
     * @param dto
     * @return
     */
    Boolean updateById(TemplateConfigUpdateDTO dto);

    /**
     * 删除模板配置
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 使用ID获取未被禁用的模板配置
     * @param id
     * @return
     */
    TemplateConfig getNotDisabledById(Long id);

}
