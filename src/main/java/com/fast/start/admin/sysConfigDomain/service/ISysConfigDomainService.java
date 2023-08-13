package com.fast.start.admin.sysConfigDomain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.start.admin.sysConfig.entity.SysConfig;
import com.fast.start.admin.sysConfigDomain.entity.SysConfigDomain;
import com.fast.start.admin.sysConfigDomain.entity.SysConfigDomainCreateDTO;
import com.fast.start.admin.sysConfigDomain.entity.SysConfigDomainQuery;
import com.fast.start.admin.sysConfigDomain.entity.SysConfigDomainUpdateDTO;
import com.fast.start.common.excel.ImportExcelError;

import java.util.List;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置域 服务类
*/
public interface ISysConfigDomainService extends IService<SysConfigDomain> {

    /**
    * 查询系统配置域
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
     * 根据配置域编码获取配置域
     * @param code
     * @return
     */
    SysConfigDomain getByCode(String code);

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
     * 导入Excel
     * @param list 要导入的数据集合
     * @param errorList 导入错误的数据集合
     * @param errors 错误标注集合
     */
    void importExcel(List<SysConfigDomain> list, List<SysConfigDomain> errorList, List<ImportExcelError> errors);
}
