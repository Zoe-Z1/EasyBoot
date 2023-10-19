package com.easy.boot.admin.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.menu.entity.Menu;
import com.easy.boot.admin.menu.entity.MenuTree;
import com.easy.boot.admin.menu.entity.MenuTreeQuery;
import com.easy.boot.admin.menu.service.IMenuService;
import com.easy.boot.admin.role.service.IRoleService;
import com.easy.boot.admin.roleMenu.service.IRoleMenuService;
import com.easy.boot.admin.user.entity.*;
import com.easy.boot.admin.user.mapper.AdminUserMapper;
import com.easy.boot.admin.user.service.AdminUserService;
import com.easy.boot.admin.userPost.service.IUserPostService;
import com.easy.boot.admin.userRole.service.IUserRoleService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.entity.ImportExcelError;
import com.easy.boot.common.saToken.UserContext;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
import com.easy.boot.utils.Constant;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser> implements AdminUserService {

    @Resource
    private IUserPostService userPostService;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IRoleService roleService;

    @Resource
    private IRoleMenuService roleMenuService;

    @Resource
    private IMenuService menuService;

    @Override
    public IPage<AdminUser> selectPage(AdminUserQuery query) {
        Page<AdminUser> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .eq(Objects.nonNull(query.getDepartmentId()) && query.getDepartmentId() != 0,
                        AdminUser::getDepartmentId, query.getDepartmentId())
                .eq(query.getSex() != null, AdminUser::getSex, query.getSex())
                .eq(query.getStatus() != null, AdminUser::getStatus, query.getStatus())
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(AdminUser::getUsername, query.getKeyword()).or()
                            .like(AdminUser::getName, query.getKeyword()).or()
                            .like(AdminUser::getMobile, query.getKeyword()).or()
                            .like(AdminUser::getEmail, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getUsername()), AdminUser::getUsername, query.getUsername())
                .like(StrUtil.isNotEmpty(query.getName()), AdminUser::getName, query.getName())
                .like(StrUtil.isNotEmpty(query.getMobile()), AdminUser::getMobile, query.getMobile())
                .like(StrUtil.isNotEmpty(query.getEmail()), AdminUser::getEmail, query.getEmail())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                            BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(AdminUser::getSort)
                .orderByDesc(AdminUser::getCreateTime)
                .page(page);
    }

    @Override
    public AdminUserVO detail(Long id) {
        AdminUser adminUser = this.getById(id);
        AdminUserVO vo = BeanUtil.copyBean(adminUser, AdminUserVO.class);
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(id);
        List<Long> postIds = userPostService.selectIdsByUserId(id);
        List<String> codes = roleService.selectCodesInRoleIds(roleIds);
        vo.setIsAdmin(codes.contains(Constant.ADMIN));
        vo.setRoleIds(roleIds);
        vo.setPostIds(postIds);
        return vo;
    }

    @Override
    public AdminUser login(@NonNull String username, @NonNull String password) {
        AdminUser user = lambdaQuery()
                .eq(AdminUser::getUsername, username)
                .one();
        if (user != null) {
            password = DigestUtil.md5Hex(password + user.getSalt());
            if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean create(AdminUserCreateDTO dto) {
        AdminUser user = lambdaQuery().eq(AdminUser::getUsername, dto.getUsername()).one();
        if (user != null) {
            throw new BusinessException("操作失败，用户账号已存在");
        }
        user = BeanUtil.copyBean(dto, AdminUser.class);
        String salt = IdUtil.randomUUID();
        String password = DigestUtil.md5Hex(dto.getPassword() + salt);
        user.setPassword(password);
        user.setSalt(salt);
        boolean status = save(user);
        if (status) {
            // 不允许绑定超级管理员
            List<String> codes = roleService.selectCodesInRoleIds(dto.getRoleIds());
            if (codes.contains(Constant.ADMIN)) {
                throw new BusinessException("无法绑定超级管理员");
            }
            // 分配岗位
            userPostService.userBindPost(dto.getPostIds(), user.getId());
            // 分配角色
            userRoleService.userBindRole(dto.getRoleIds(), user.getId());
        }
        return status;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateById(AdminUserUpdateDTO dto) {
        // 删除所有岗位重新分配
        userPostService.deleteByUserId(dto.getId());
        userPostService.userBindPost(dto.getPostIds(), dto.getId());
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(dto.getId());
        List<String> codes = roleService.selectCodesInRoleIds(roleIds);
        if (!codes.contains(Constant.ADMIN)) {
            // 不允许绑定超级管理员
            codes = roleService.selectCodesInRoleIds(dto.getRoleIds());
            if (codes.contains(Constant.ADMIN)) {
                throw new BusinessException("无法绑定超级管理员");
            }
            // 删除所有角色重新分配
            userRoleService.deleteByUserId(dto.getId());
            userRoleService.userBindRole(dto.getRoleIds(), dto.getId());
        } else {
            if (dto.getStatus() != null && dto.getStatus() == 2) {
                throw new BusinessException("无法禁用超级管理员");
            }
        }
        AdminUser user = BeanUtil.copyBean(dto, AdminUser.class);
        // 禁用账号退出登录
        if (dto.getStatus() != null && dto.getStatus() == 2) {
            StpUtil.kickout(dto.getId());
        }
        return updateById(user);
    }

    @Override
    public Boolean editPassword(EditPasswordDTO dto) {
        AdminUser adminUser = this.detail(dto.getId());
        String oldPassword = DigestUtil.md5Hex(dto.getOldPassword() + adminUser.getSalt());
        if (!oldPassword.equals(dto.getOldPassword())) {
            throw new BusinessException("旧密码错误");
        }
        String salt = IdUtil.randomUUID();
        String password = DigestUtil.md5Hex(dto.getNewPassword() + salt);
        AdminUser user = AdminUser.builder()
                .password(password)
                .salt(salt)
                .id(dto.getId())
                .build();
        boolean status = updateById(user);
        if (status) {
            // 改密码踢下线
            StpUtil.kickout(dto.getId());
        }
        return status;
    }

    @Override
    public Boolean resetPassword(ResetPasswordDTO dto) {
        String salt = IdUtil.randomUUID();
        String password = DigestUtil.md5Hex(dto.getPassword() + salt);
        AdminUser user = AdminUser.builder()
                .password(password)
                .salt(salt)
                .id(dto.getId())
                .build();
        boolean status = updateById(user);
        if (status) {
            // 改密码踢下线
            StpUtil.kickout(dto.getId());
        }
        return status;
    }

    @Override
    public AdminUserInfo getInfo() {
        Long id = UserContext.getId();
        AdminUser adminUser = this.detail(id);
        AdminUserInfo info = BeanUtil.copyBean(adminUser, AdminUserInfo.class);
        // 获取角色编码
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(id);
        List<String> roles = roleService.selectCodesInRoleIds(roleIds);
        // 获取菜单集合
        List<Long> menuIds = roleMenuService.selectMenuIdsByRoleIds(roleIds);
        MenuTreeQuery query = MenuTreeQuery.builder()
                .status(1)
                .parentId(0L)
                .build();
        List<MenuTree> menus = menuService.treeList(query);
        if (!menus.isEmpty()) {
            menus = menus.get(0).getChildren();
        }
        List<String> permissions = new ArrayList<>();
        if (roles.contains(Constant.ADMIN)) {
            info.setIsAdmin(true);
            // 超级管理员拥有所有权限和菜单
            List<Menu> menuAll = menuService.selectMenuAll();
            permissions = menuAll.stream().map(Menu::getPermission).collect(Collectors.toList());
        } else {
            info.setIsAdmin(false);
            eachMenus(menus, menuIds, permissions);
        }
        info.setRoles(roles);
        info.setMenus(menus);
        info.setPermissions(permissions);
        return info;
    }

    /**
     * 递归处理当前用户菜单
     * @param menus
     * @param menuIds
     * @param permissions
     */
    private void eachMenus(List<MenuTree> menus, List<Long> menuIds, List<String> permissions) {
        // todo 先写死 后续可能配置
        Integer mode = 1;   // 1：严格模式 2：非严格模式
        Iterator<MenuTree> iterator = menus.iterator();
        while (iterator.hasNext()) {
            MenuTree menuTree = iterator.next();
            if (!menuTree.getChildren().isEmpty()) {
                eachMenus(menuTree.getChildren(), menuIds, permissions);
            }
            if (mode == 1) {
                // 严格模式 勾选了菜单才显示菜单
                if (!menuIds.contains(menuTree.getId())) {
                    iterator.remove();
                } else if (StrUtil.isNotEmpty(menuTree.getPermission())) {
                    permissions.add(menuTree.getPermission());
                }
            } else {
                // 非严格模式 勾选了菜单下的权限就显示菜单
                if (menuTree.getChildren().isEmpty() && !menuIds.contains(menuTree.getId())) {
                    iterator.remove();
                } else if (StrUtil.isNotEmpty(menuTree.getPermission())) {
                    permissions.add(menuTree.getPermission());
                }
            }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteById(Long id) {
        // 删除用户岗位关联
        userPostService.deleteByUserId(id);
        // 删除用户角色关联
        userRoleService.deleteByUserId(id);
        // 删除用户
        return removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        // 删除用户岗位关联
        userPostService.deleteBatchByUserIds(ids);
        // 删除用户角色关联
        userRoleService.deleteBatchByUserIds(ids);
        // 删除用户
        return removeBatchByIds(ids);
    }

    @Override
    public void importExcel(List<AdminUser> list, List<AdminUser> errorList, List<ImportExcelError> errors) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<String> usernames = list.stream().map(AdminUser::getUsername).distinct().collect(Collectors.toList());
        List<AdminUser> adminUsers = lambdaQuery().in(AdminUser::getUsername, usernames).list();
        usernames = adminUsers.stream().map(AdminUser::getUsername).distinct().collect(Collectors.toList());
        // 去除表头 行数从1起算
        int rowIndex = 1;
        Iterator<AdminUser> iterator = list.iterator();
        while (iterator.hasNext()) {
            AdminUser adminUser = iterator.next();
            boolean isError = false;
            ImportExcelError.ImportExcelErrorBuilder builder = ImportExcelError.builder();
            if (StrUtil.isEmpty(adminUser.getUsername())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("用户账号不能为空");
                errors.add(builder.build());
            } else if (adminUser.getUsername().length() < 5 || adminUser.getUsername().length() > 15) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("用户账号在5-15个字符之间");
                errors.add(builder.build());
            }
            if (usernames.contains(adminUser.getUsername())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("用户账号已存在");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(adminUser.getPassword())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("用户密码不能为空");
                errors.add(builder.build());
            } else if (adminUser.getPassword().length() < 6 || adminUser.getPassword().length() > 20) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("用户密码在6-20个字符之间");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(adminUser.getName())) {
                isError = true;
                builder.columnIndex(2).rowIndex(rowIndex).msg("用户昵称不能为空");
                errors.add(builder.build());
            } else if (adminUser.getName().length() < 2 || adminUser.getName().length() > 20) {
                isError = true;
                builder.columnIndex(2).rowIndex(rowIndex).msg("用户昵称在2-20个字符之间");
                errors.add(builder.build());
            }
            if (adminUser.getSex() == null) {
                isError = true;
                builder.columnIndex(3).rowIndex(rowIndex).msg("用户性别不能为空");
                errors.add(builder.build());
            }
            if (StrUtil.isNotEmpty(adminUser.getEmail()) && !Validator.isEmail(adminUser.getEmail())) {
                isError = true;
                builder.columnIndex(4).rowIndex(rowIndex).msg("用户邮箱格式不正确");
                errors.add(builder.build());
            }
            if (StrUtil.isNotEmpty(adminUser.getMobile()) && !Validator.isMobile(adminUser.getMobile())) {
                isError = true;
                builder.columnIndex(5).rowIndex(rowIndex).msg("用户手机号格式不正确");
                errors.add(builder.build());
            }
            if (adminUser.getStatus() == null) {
                isError = true;
                builder.columnIndex(6).rowIndex(rowIndex).msg("用户账号状态不能为空");
                errors.add(builder.build());
            }
            // 这一行有错误，行数增加，错误数据加到list，删除原list的数据
            if (isError) {
                rowIndex++;
                errorList.add(adminUser);
                iterator.remove();
            } else {
                // 没有错误，会进行新增，加进去不存在的，防止后续存在
                usernames.add(adminUser.getUsername());
                String salt = IdUtil.randomUUID();
                String password = DigestUtil.md5Hex(adminUser.getPassword());
                password = DigestUtil.md5Hex(password + salt);
                adminUser.setSalt(salt);
                adminUser.setPassword(password);
            }
        }
        saveBatch(list);
    }

}
