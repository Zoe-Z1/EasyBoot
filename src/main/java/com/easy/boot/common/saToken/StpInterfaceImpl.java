package com.easy.boot.common.saToken;

import cn.dev33.satoken.stp.StpInterface;
import com.easy.boot.admin.menu.entity.Menu;
import com.easy.boot.admin.menu.service.IMenuService;
import com.easy.boot.admin.role.service.IRoleService;
import com.easy.boot.admin.roleMenu.service.IRoleMenuService;
import com.easy.boot.admin.userRole.service.IUserRoleService;
import com.easy.boot.utils.Constant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/7/30
 * @description sa-token自定义权限实现
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private IMenuService menuService;

    @Resource
    private IRoleService roleService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(Long.valueOf(loginId.toString()));
        List<String> roles = roleService.selectCodesInRoleIds(roleIds);
        if (roles.contains(Constant.ADMIN)) {
            // 超级管理员拥有全部权限
            List<Menu> menus = menuService.selectMenuAll();
            return menus.stream().map(Menu::getPermission).collect(Collectors.toList());
        }
        List<Long> menuIds = roleMenuService.selectMenuIdsByRoleIds(roleIds);
        return menuService.selectPermissionsByIds(menuIds);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(Long.valueOf(loginId.toString()));
        return roleService.selectCodesInRoleIds(roleIds);
    }
}
