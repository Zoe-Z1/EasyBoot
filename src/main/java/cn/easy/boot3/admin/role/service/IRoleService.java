package cn.easy.boot3.admin.role.service;

import cn.easy.boot3.admin.role.entity.*;
import cn.easy.boot3.common.excel.entity.ImportExcelError;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.easy.boot3.admin.role.entity.*;

import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 角色 服务类
*/
public interface IRoleService extends IService<Role> {

    /**
     * 查询角色列表
     * @return
     */
    List<Role> selectAll();

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
    RoleVO detail(Long id);

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
     * 根据用户ID获取未被禁用的角色列表
     * @param userId
     * @return
     */
    List<Role> selectNotDisabledListByUserId(Long userId);

    /**
     * 根据用户ID获取未被禁用的角色ID列表
     * @param id
     * @return
     */
    List<Long> selectNotDisabledRoleIdsByUserId(Long id);

    /**
     * 根据用户ID获取未被禁用的角色编码列表
     * @param id
     * @return
     */
    List<String> selectNotDisabledCodesByUserId(Long id);

    /**
     * 根据角色ID集合获取角色编码集合
     * @param ids
     * @return
     */
    List<String> selectCodesInRoleIds(List<Long> ids);

    /**
     * 根据角色ID集合获取未禁用的角色集合
     * @param roleIds
     * @return
     */
    List<Role> selectNotDisabledListInRoleIds(List<Long> roleIds);

    /**
     * 根据角色ID集合获取未禁用的角色ID集合
     * @param roleIds
     * @return
     */
    List<Long> selectNotDisabledIdsInRoleIds(List<Long> roleIds);

    /**
     * 根据角色ID集合获取未禁用的角色编码集合
     * @param ids
     * @return
     */
    List<String> selectNotDisabledCodesInRoleIds(List<Long> ids);

    /**
     * 导入Excel
     * @param list
     * @param errorList
     * @param errors
     */
    void importExcel(List<Role> list, List<Role> errorList, List<ImportExcelError> errors);

    /**
     * 判断用户是否超级管理员角色
     * @param userId
     * @return
     */
    Boolean isAdmin(Long userId);

}
