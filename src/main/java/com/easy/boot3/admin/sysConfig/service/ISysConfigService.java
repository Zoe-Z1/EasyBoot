package com.easy.boot3.admin.sysConfig.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot3.admin.sysConfig.entity.SysConfig;
import com.easy.boot3.admin.sysConfig.entity.SysConfigCreateDTO;
import com.easy.boot3.admin.sysConfig.entity.SysConfigQuery;
import com.easy.boot3.admin.sysConfig.entity.SysConfigUpdateDTO;

import java.util.List;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置 服务类
*/
public interface ISysConfigService extends IService<SysConfig> {

    /**
    * 查询系统配置
    * @param query
    * @return
    */
    IPage<SysConfig> selectPage(SysConfigQuery query);

    /**
     * 获取系统配置详情
     * @param id
     * @return
     */
    SysConfig detail(Long id);

    /**
    * 创建系统配置
    * @param dto
    * @return
    */
    Boolean create(SysConfigCreateDTO dto);

    /**
    * 编辑系统配置
    * @param dto
    * @return
    */
    Boolean updateById(SysConfigUpdateDTO dto);

    /**
     * 删除系统配置
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除系统配置
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

    /**
     * 删除域下的系统配置
     * @param domainId
     * @return
     */
    Boolean deleteByDomainId(Long domainId);

    /**
     * 根据域id和配置key获取配置
     * @param domainId
     * @param code
     * @return
     */
    SysConfig getByDomainIdAndCode(Long domainId, String code);

    /**
     * 根据域id和配置key获取未禁用的配置
     * @param domainId
     * @param code
     * @return
     */
    SysConfig getNotDisabledByDomainIdAndCode(Long domainId, String code);

    /**
     * 根据配置域id和禁用状态获取配置列表
     * @param domainId
     * @param status
     * @return
     */
    List<SysConfig> selectByDomainIdAndStatus(Long domainId, Integer status);

    /**
     * 根据配置域id获取配置列表
     * @param domainId
     * @return
     */
    List<SysConfig> selectByDomainId(Long domainId);

    /**
     * 根据配置域id获取未禁用配置列表
     * @param domainId
     * @return
     */
    List<SysConfig> selectNotDisabledListByDomainId(Long domainId);

    /**
     * 批量保存系统模板配置
     * @param dtos
     * @return
     */
    Boolean templateBatchSave(List<SysConfigCreateDTO> dtos);
}
