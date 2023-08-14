package com.easy.boot.admin.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.role.entity.Role;
import com.easy.boot.admin.role.entity.RoleCreateDTO;
import com.easy.boot.admin.role.entity.RoleQuery;
import com.easy.boot.admin.role.entity.RoleUpdateDTO;
import com.easy.boot.common.excel.ImportExcelError;

import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 角色 服务类
*/
public interface IRoleService extends IService<Role> {

    /**
    * 查询角色列表
    * @param query
    * @return
    */
    IPage<Role> selectPage(RoleQuery query);

    /**
     * 获取角色详情
     * @param id
     * @return
     */
    Role detail(Long id);

    /**
     * 根据编码获取角色
     * @param code
     * @return
     */
    Role getByCode(String code);

    /**
    * 创建角色
    * @param dto
    * @return
    */
    Boolean create(RoleCreateDTO dto);

    /**
    * 编辑角色
    * @param dto
    * @return
    */
    Boolean updateById(RoleUpdateDTO dto);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

    /**
     * 根据角色ID获取角色编码
     * @param ids
     * @return
     */
    List<String> selectCodesInRoleIds(List<Long> ids);

    /**
     * 导入Excel
     * @param list
     * @param errorList
     * @param errors
     */
    void importExcel(List<Role> list, List<Role> errorList, List<ImportExcelError> errors);
}
