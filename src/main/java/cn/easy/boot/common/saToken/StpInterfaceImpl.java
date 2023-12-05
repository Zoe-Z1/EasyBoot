package cn.easy.boot.common.saToken;

import cn.dev33.satoken.stp.StpInterface;
import cn.easy.boot.common.base.BaseEntity;
import cn.easy.boot.admin.menu.service.IMenuService;
import cn.easy.boot.admin.role.entity.Role;
import cn.easy.boot.admin.role.service.IRoleService;
import cn.easy.boot.utils.Constant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/7/30
 * @description sa-token自定义权限实现
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private IMenuService menuService;

    @Resource
    private IRoleService roleService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<Role> list = roleService.selectNotDisabledListByUserId(Long.valueOf(loginId.toString()));
        List<Long> roleIds = list.stream().map(BaseEntity::getId)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
        List<String> codes = list.stream().map(Role::getCode)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
        if (codes.contains(Constant.ADMIN)) {
            // 超级管理员拥有全部权限
            return menuService.selectPermissionAll();
        }
        return menuService.selectNotDisabledPermissionsInRoleIds(roleIds);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleService.selectNotDisabledCodesByUserId(Long.valueOf(loginId.toString()));
    }
}
