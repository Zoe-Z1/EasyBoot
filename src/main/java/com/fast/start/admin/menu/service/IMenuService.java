package com.fast.start.admin.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fast.start.admin.menu.entity.*;
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
     * 根据菜单集合查询菜单权限字符集合
     * @param ids 菜单ID集合
     * @return
     */
    List<String> selectPermissionsByIds(List<Long> ids);

    /**
     * 查询所有菜单
     * @return
     */
    List<MenuTree> all();

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


}
