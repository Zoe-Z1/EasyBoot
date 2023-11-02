package com.easy.boot.admin.roleMenu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.roleMenu.entity.RoleMenu;
import com.easy.boot.admin.roleMenu.entity.RoleMenuQuery;

import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 角色菜单关联 服务类
*/
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
    * 查询角色菜单关联
    * @param query
    * @return
    */
    List<RoleMenu> selectList(RoleMenuQuery query);

    /**
     * 根据角色ID查询菜单ID集合
     * @param roleId 角色ID
     * @return
     */
    List<Long> selectMenuIdsByRoleId(Long roleId);

    /**
     * 根据角色ID集合查询菜单ID集合
     * @param roleIds 角色ID集合
     * @return
     */
    List<Long> selectMenuIdsInRoleIds(List<Long> roleIds);

    /**
    * 批量创建角色菜单关联
    * @param ids 菜单ID集合
    * @param roleId 角色ID
    * @return
    */
    Boolean batchCreate(List<Long> ids, Long roleId);

    /**
    * 根据角色ID删除角色菜单关联
    * @param roleId 角色ID
    * @return
    */
    Boolean deleteByRoleId(Long roleId);

}
