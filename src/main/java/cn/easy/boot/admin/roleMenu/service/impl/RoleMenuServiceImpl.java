package cn.easy.boot.admin.roleMenu.service.impl;

import cn.easy.boot.admin.roleMenu.entity.RoleMenu;
import cn.easy.boot.admin.roleMenu.entity.RoleMenuQuery;
import cn.easy.boot.admin.roleMenu.mapper.RoleMenuMapper;
import cn.easy.boot.admin.roleMenu.service.IRoleMenuService;
import cn.easy.boot.common.base.BaseEntity;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/07/30
* @description 角色菜单关联 服务实现类
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public List<RoleMenu> selectList(RoleMenuQuery query) {
        return lambdaQuery()
                .eq(Objects.nonNull(query.getMenuId()), RoleMenu::getMenuId, query.getMenuId())
                .eq(Objects.nonNull(query.getRoleId()), RoleMenu::getRoleId, query.getRoleId())
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public List<Long> selectMenuIdsByRoleId(Long roleId) {
        if (roleId == null) {
            return new ArrayList<>();
        }
        List<RoleMenu> list = lambdaQuery().eq(RoleMenu::getRoleId, roleId).list();
        return list.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Long> selectMenuIdsInRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        List<RoleMenu> list = lambdaQuery().in(RoleMenu::getRoleId, roleIds).list();
        return list.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());
    }

    @Override
    public Boolean batchCreate(List<Long> ids, Long roleId) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        List<RoleMenu> list = ids.stream()
                .map(id -> RoleMenu.builder().menuId(id).roleId(roleId).build())
                .collect(Collectors.toList());
        return saveBatch(list);
    }


    @Override
    public Boolean deleteByRoleId(Long roleId) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return remove(queryWrapper);
    }

}
