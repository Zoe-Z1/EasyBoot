package com.easy.boot.admin.generateConfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.generateConfig.entity.GenerateConfig;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigUpdateDTO;
import com.easy.boot.admin.generateConfig.entity.TableConfigQuery;

import java.util.List;

/**
* @author zoe
* @date 2023/09/10
* @description 代码生成参数配置 服务类
*/
public interface IGenerateConfigService extends IService<GenerateConfig> {


    /**
     * 获取代码生成全局配置
     * @return
     */
    GenerateConfig getGlobalConfig();

    /**
     * 获取代码生成Table参数配置
     * @param query
     * @return
     */
    GenerateConfig getTableConfig(TableConfigQuery query);

    /**
     * 编辑代码生成参数配置
     * @param dto
     * @return
     */
    Boolean updateById(GenerateConfigUpdateDTO dto);

    /**
     * 删除代码生成参数配置
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除代码生成参数配置
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

}
