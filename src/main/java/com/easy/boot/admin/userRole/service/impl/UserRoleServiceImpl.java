package com.easy.boot.admin.userRole.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.userRole.service.IUserRoleService;
import com.easy.boot.admin.userRole.entity.UserRole;
import com.easy.boot.admin.userRole.entity.UserRoleQuery;
import com.easy.boot.admin.userRole.mapper.UserRoleMapper;
import com.easy.boot.common.base.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/07/30
* @description 用户角色关联 服务实现类
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {



    @Override
    public IPage<UserRole> selectPage(UserRoleQuery query) {
        Page<UserRole> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .eq(Objects.nonNull(query.getRoleId()), UserRole::getRoleId, query.getRoleId())
                .eq(Objects.nonNull(query.getUserId()), UserRole::getUserId, query.getUserId())
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public List<Long> selectRoleIdsByUserId(Long userId) {
        List<UserRole> list = lambdaQuery().eq(UserRole::getUserId, userId).list();
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Long> selectRoleIdsByUserIds(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        List<UserRole> list = lambdaQuery().in(UserRole::getUserId, userIds).list();
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList());
    }

    @Override
    public List<UserRole> selectListByRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        return lambdaQuery().in(UserRole::getRoleId, roleIds).list();
    }

    @Override
    public Boolean userBindRole(List<Long> ids, Long userId) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        List<UserRole> list = ids.stream()
                .map(id -> UserRole.builder().roleId(id).userId(userId).build())
                .collect(Collectors.toList());
        return saveBatch(list);
    }

    @Override
    public Boolean roleAllotUser(List<Long> ids, Long roleId) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        List<UserRole> list = lambdaQuery()
                .in(UserRole::getUserId, ids)
                .eq(UserRole::getRoleId, roleId)
                .list();
        list.forEach(item -> {
            ids.removeIf(id -> item.getUserId().equals(id));
        });
        list = ids.stream()
                .map(id -> UserRole.builder().roleId(roleId).userId(id).build())
                .collect(Collectors.toList());
        return saveBatch(list);
    }

    @Override
    public Boolean deleteByUserId(Long userId) {
        List<Long> ids = new ArrayList<>();
        ids.add(userId);
        return deleteBatchByUserIds(ids);
    }

    @Override
    public Boolean deleteBatchByUserIds(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return false;
        }
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        return remove(queryWrapper);
    }

    @Override
    public Boolean deleteByRoleId(Long roleId) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return remove(queryWrapper);
    }


}
