package com.easy.boot.admin.sysConfigDomain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.sysConfig.entity.SysConfig;
import com.easy.boot.admin.sysConfig.entity.SysTemplateConfigVO;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomain;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainCreateDTO;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainQuery;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainUpdateDTO;

import java.util.List;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置域 服务类
*/
public interface ISysConfigDomainService extends IService<SysConfigDomain> {

    /**
    * 分页查询系统配置域列表
     * @param query
    * @return
    */
    IPage<SysConfigDomain> selectPage(SysConfigDomainQuery query);

    /**
     * 获取系统配置域详情
     * @param id
     * @return
     */
    SysConfigDomain detail(Long id);

    /**
     * 根据配置域编码获取未被禁用的配置域
     * @param code
     * @return
     */
    SysConfigDomain getNotDisabledByCode(String code);

    /**
     * 根据系统配置域编码获取系统配置列表
     * @param code
     * @return
     */
    List<SysConfig> selectListByDomainCode(String code);

    /**
    * 创建系统配置域
    * @param dto
    * @return
    */
    Boolean create(SysConfigDomainCreateDTO dto);

    /**
    * 编辑系统配置域
    * @param dto
    * @return
    */
    Boolean updateById(SysConfigDomainUpdateDTO dto);

    /**
     * 删除系统配置域
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 根据系统配置域编码和系统配置编码获取未禁用的配置
     * @param domainCode
     * @param code
     * @return
     */
    SysConfig getNotDisabledByDomainCodeAndConfigCode(String domainCode, String code);

    /**
     * 获取全部全局配置
     * @return
     */
    List<SysConfig> selectGlobalAll();

    /**
     * 获取系统模板配置列表
     * @param domainId
     * @return
     */
    List<SysTemplateConfigVO> selectTemplateList(Long domainId);
}
