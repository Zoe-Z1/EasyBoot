package com.easy.boot3.admin.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot3.admin.role.entity.*;
import com.easy.boot3.admin.role.mapper.RoleMapper;
import com.easy.boot3.admin.role.service.IRoleService;
import com.easy.boot3.admin.roleMenu.service.IRoleMenuService;
import com.easy.boot3.admin.userRole.entity.UserRole;
import com.easy.boot3.admin.userRole.service.IUserRoleService;
import com.easy.boot3.common.base.BaseEntity;
import com.easy.boot3.common.excel.entity.ImportExcelError;
import com.easy.boot3.exception.BusinessException;
import com.easy.boot3.utils.BeanUtil;
import com.easy.boot3.utils.Constant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/07/30
* @description 角色 服务实现类
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Override
    public List<Role> selectAll() {
        List<Role> list = lambdaQuery()
                .select(Role::getCode, Role::getName, BaseEntity::getId, Role::getStatus)
                .eq(Role::getStatus, 1)
                .orderByAsc(Role::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        // 超级管理员不存在于列表中 不允许绑定
        list.removeIf(item -> Constant.ADMIN.equals(item.getCode()));
        return list;
    }

    @Override
    public IPage<Role> selectPage(RoleQuery query) {
        Page<Role> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery.like(Role::getCode, query.getKeyword()).or()
                            .like(Role::getName, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getName()), Role::getName, query.getName())
                .like(StrUtil.isNotEmpty(query.getCode()), Role::getCode, query.getCode())
                .eq(Objects.nonNull(query.getStatus()), Role::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(Role::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public RoleVO detail(Long id) {
        Role role = getById(id);
        if (role == null) {
            throw new BusinessException("当前角色不存在");
        }
        RoleVO vo = BeanUtil.copyBean(role, RoleVO.class);
        List<Long> ids = roleMenuService.selectMenuIdsByRoleId(id);
        vo.setMenuIds(ids);
        return vo;
    }

    @Override
    public Role getByCode(String code) {
        return lambdaQuery().eq(Role::getCode, code).one();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean create(RoleCreateDTO dto) {
        Role role = this.getByCode(dto.getCode());
        if (Objects.nonNull(role)) {
            throw new BusinessException("角色编码已存在");
        }
        Role entity = BeanUtil.copyBean(dto, Role.class);
        boolean status = save(entity);
        if (status) {
            roleMenuService.batchCreate(dto.getMenuIds(), entity.getId());
        }
        return status;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateById(RoleUpdateDTO dto) {
        Role role = getById(dto.getId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        } else {
            if (Constant.ADMIN.equals(role.getCode())) {
                if (dto.getStatus() == 2) {
                    throw new BusinessException("该角色不允许禁用");
                } else {
                    throw new BusinessException("该角色不允许编辑");
                }
            }
        }
        role = this.getByCode(dto.getCode());
        if (Objects.nonNull(role) && !dto.getId().equals(role.getId())) {
            throw new BusinessException("角色编码已存在");
        }
        if (!dto.getIsStatusChange()) {
            // 删除菜单后新增
            roleMenuService.deleteByRoleId(dto.getId());
            roleMenuService.batchCreate(dto.getMenuIds(), dto.getId());
        }
        Role entity = BeanUtil.copyBean(dto, Role.class);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        Role role = getById(id);
        if (role != null && Constant.ADMIN.equals(role.getCode())) {
            throw new BusinessException("该角色不允许删除");
        }
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<UserRole> userRoles = userRoleService.selectListByRoleIds(ids);
        if (CollUtil.isNotEmpty(userRoles)) {
            throw new BusinessException("已有用户绑定选中的角色，不允许删除");
        }
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        List<String> codes = selectCodesInRoleIds(ids);
        if (codes.contains(Constant.ADMIN)) {
            throw new BusinessException("存在不允许删除的角色，无法删除");
        }
        List<UserRole> userRoles = userRoleService.selectListByRoleIds(ids);
        if (CollUtil.isNotEmpty(userRoles)) {
            throw new BusinessException("已有用户绑定选中的角色，不允许删除");
        }
        return removeBatchByIds(ids);
    }

    @Override
    public List<Role> selectNotDisabledListByUserId(Long userId) {
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(userId);
        return selectNotDisabledListInRoleIds(roleIds);
    }

    @Override
    public List<Long> selectNotDisabledRoleIdsByUserId(Long id) {
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(id);
        return selectNotDisabledIdsInRoleIds(roleIds);
    }

    @Override
    public List<String> selectNotDisabledCodesByUserId(Long id) {
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(id);
        return selectNotDisabledCodesInRoleIds(roleIds);
    }

    @Override
    public List<String> selectCodesInRoleIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<Role> list = lambdaQuery().select(Role::getCode)
                .in(BaseEntity::getId, ids)
                .orderByAsc(Role::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        return list.stream().map(Role::getCode)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
    }

    @Override
    public List<Role> selectNotDisabledListInRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        return lambdaQuery()
                .in(BaseEntity::getId, roleIds)
                .eq(Role::getStatus, 1)
                .orderByAsc(Role::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public List<Long> selectNotDisabledIdsInRoleIds(List<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        List<Role> list = lambdaQuery().select(BaseEntity::getId)
                .in(BaseEntity::getId, roleIds)
                .eq(Role::getStatus, 1)
                .orderByAsc(Role::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        return list.stream().map(BaseEntity::getId)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> selectNotDisabledCodesInRoleIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<Role> list = lambdaQuery().select(Role::getCode)
                .in(BaseEntity::getId, ids).eq(Role::getStatus, 1)
                .orderByAsc(Role::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        return list.stream().map(Role::getCode)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
    }

    @Override
    public void importExcel(List<Role> list, List<Role> errorList, List<ImportExcelError> errors) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<String> codes = list.stream().map(Role::getCode).distinct().collect(Collectors.toList());
        List<Role> roles = lambdaQuery().in(Role::getCode, codes).list();
        codes = roles.stream().map(Role::getCode).distinct().collect(Collectors.toList());
        // 去除表头 行数从1起算
        int rowIndex = 1;
        Iterator<Role> iterator = list.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            boolean isError = false;
            ImportExcelError.ImportExcelErrorBuilder builder = ImportExcelError.builder();
            if (StrUtil.isEmpty(role.getCode())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("角色编码不能为空");
                errors.add(builder.build());
            } else if (role.getCode().length() < 1 || role.getCode().length() > 20) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("角色编码在1-20个字符之间");
                errors.add(builder.build());
            }
            if (codes.contains(role.getCode())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("角色编码已存在");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(role.getName())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("角色名称不能为空");
                errors.add(builder.build());
            } else if (role.getName().length() < 1 || role.getName().length() > 20) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("角色名称在1-20个字符之间");
                errors.add(builder.build());
            }
            if (role.getStatus() == null) {
                isError = true;
                builder.columnIndex(2).rowIndex(rowIndex).msg("角色状态不能为空");
                errors.add(builder.build());
            }
            // 这一行有错误，行数增加，错误数据加到list，删除原list的数据
            if (isError) {
                rowIndex++;
                errorList.add(role);
                iterator.remove();
            } else {
                // 没有错误，会进行新增，加进去不存在的，防止后续存在
                codes.add(role.getCode());
            }
        }
        saveBatch(list);
    }

    @Override
    public Boolean isAdmin(Long userId) {
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(userId);
        List<String> codes = selectNotDisabledCodesInRoleIds(roleIds);
        return codes.contains(Constant.ADMIN);
    }

}
