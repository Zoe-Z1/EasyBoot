package com.easy.boot.admin.templateParamConfig.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.templateParamConfig.entity.TemplateParamConfig;
import com.easy.boot.admin.templateParamConfig.entity.TemplateParamConfigCreateDTO;
import com.easy.boot.admin.templateParamConfig.entity.TemplateParamConfigQuery;
import com.easy.boot.admin.templateParamConfig.entity.TemplateParamConfigUpdateDTO;
import java.util.List;

/**
* @author zoe
* @date 2023/11/09
* @description 模板参数配置 服务类
*/
public interface ITemplateParamConfigService extends IService<TemplateParamConfig> {

    /**
     * 分页查询模板参数配置
     * @param query
     * @return
     */
    IPage<TemplateParamConfig> selectPage(TemplateParamConfigQuery query);

    /**
     * 创建模板参数配置
     * @param dto
     * @return
     */
    Boolean create(TemplateParamConfigCreateDTO dto);

    /**
     * 编辑模板参数配置
     * @param dto
     * @return
     */
    Boolean updateById(TemplateParamConfigUpdateDTO dto);

    /**
     * 删除模板参数配置
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除模板参数配置
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

    /**
     * 根据模板ID和配置参数键获取配置
     * @param templateId
     * @param code
     * @return
     */
    TemplateParamConfig getByTemplateIdAndCode(Long templateId, String code);

    /**
     * 根据模板ID获取未被禁用的参数配置列表
     * @param templateId
     * @return
     */
    List<TemplateParamConfig> selectNotDisabledListByTemplateId(Long templateId);
}
