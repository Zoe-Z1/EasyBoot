package com.easy.boot.admin.userRole.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.userRole.entity.UserRole;
import com.easy.boot.admin.userRole.entity.UserRoleQuery;

import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 用户角色关联 服务类
*/
public interface IUserRoleService extends IService<UserRole> {

    /**
    * 查询用户角色关联
    * @param query
    * @return
    */
    IPage<UserRole> selectPage(UserRoleQuery query);

    /**
     * 查询用户绑定的所有角色ID
     * @param userId
     * @return
     */
    List<Long> selectRoleIdsByUserId(Long userId);

    /**
     * 查询用户绑定的所有角色ID
     * @param userIds
     * @return
     */
    List<Long> selectRoleIdsByUserIds(List<Long> userIds);

    /**
     * 查询绑定角色ID的用户
     * @param roleIds 角色ID集合
     * @return
     */
    List<UserRole> selectListByRoleIds(List<Long> roleIds);

    /**
    * 用户绑定角色
    * @param ids 角色ID集合
    * @param userId 用户ID
    * @return
    */
    Boolean userBindRole(List<Long> ids, Long userId);

    /**
     * 角色分配用户
     * @param ids 用户ID集合
     * @param roleId 角色ID
     * @return
     */
    Boolean roleAllotUser(List<Long> ids, Long roleId);

    /**
    * 删除用户角色关联
    * @param userId
    * @return
    */
    Boolean deleteByUserId(Long userId);

    /**
     * 批量删除用户角色关联
     * @param userIds
     */
    Boolean deleteBatchByUserIds(List<Long> userIds);

    /**
     * 删除角色用户关联
     * @param roleId
     * @return
     */
    Boolean deleteByRoleId(Long roleId);

}
