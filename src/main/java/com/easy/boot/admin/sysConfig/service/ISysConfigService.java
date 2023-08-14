package com.easy.boot.admin.sysConfig.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.sysConfig.entity.SysConfig;
import com.easy.boot.admin.sysConfig.entity.SysConfigQuery;
import com.easy.boot.admin.sysConfig.entity.SysConfigCreateDTO;
import com.easy.boot.admin.sysConfig.entity.SysConfigUpdateDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.common.excel.ImportExcelError;

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
     * 根据配置域id获取未禁用配置列表
     * @param domainId
     * @return
     */
    List<SysConfig> getByDomainId(Long domainId);

    /**
     * 导入Excel
     * @param list 要导入的数据集合
     * @param errorList 导入错误的数据集合
     * @param errors 错误标注集合
     */
    void importExcel(List<SysConfig> list, List<SysConfig> errorList, List<ImportExcelError> errors);
}
