package com.easy.boot.admin.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.menu.entity.*;
import lombok.NonNull;

import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 菜单 服务类
*/
public interface IMenuService extends IService<Menu> {

    /**
     * 获取最上级菜单
     * @return
     */
    Menu getRoot();

    /**
     * 根据父级菜单ID查询子菜单
     * @param parentId
     * @return
     */
    List<Menu> selectListByParentId(@NonNull Long parentId);

    /**
     * 查询所有菜单，组成菜单树
     * @return
     */
    List<MenuTree> selectAll();

    /**
     * 查询所有菜单权限字符
     * @return
     */
    List<String> selectPermissionAll();

    /**
     * 查询树形菜单（包含当前菜单）
     * @param query
     * @return
     */
    List<MenuTree> treeList(MenuTreeQuery query);

    /**
    * 查询菜单
    * @param query
    * @return
    */
    IPage<Menu> selectPage(MenuQuery query);

    /**
     * 获取菜单详情
     * @param id
     * @return
     */
    Menu detail(Long id);

    /**
    * 创建菜单
    * @param dto
    * @return
    */
    Boolean create(MenuCreateDTO dto);

    /**
    * 编辑菜单
    * @param dto
    * @return
    */
    Boolean updateById(MenuUpdateDTO dto);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 根据角色ID集合获取未被禁用的菜单集合
     * @param roleIds
     * @return
     */
    List<Menu> selectNotDisabledListInRoleIds(List<Long> roleIds);

    /**
     * 根据角色ID集合获取未被禁用的菜单ID集合
     * @param roleIds
     * @return
     */
    List<Long> selectNotDisabledIdsInRoleIds(List<Long> roleIds);

    /**
     * 根据角色ID集合获取未被禁用的菜单权限字符集合
     * @param roleIds
     * @return
     */
    List<String> selectNotDisabledPermissionsInRoleIds(List<Long> roleIds);

}
