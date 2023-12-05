package cn.easy.boot3.admin.user.service.impl;

import cn.easy.boot3.admin.menu.entity.MenuTree;
import cn.easy.boot3.admin.user.entity.*;
import cn.easy.boot3.common.base.BaseEntity;
import cn.easy.boot3.common.saToken.UserContext;
import cn.easy.boot3.exception.BusinessException;
import cn.easy.boot3.utils.BeanUtil;
import cn.easy.boot3.utils.Constant;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easy.boot3.admin.department.entity.Department;
import cn.easy.boot3.admin.department.service.IDepartmentService;
import cn.easy.boot3.admin.menu.entity.MenuTreeQuery;
import cn.easy.boot3.admin.menu.service.IMenuService;
import cn.easy.boot3.admin.post.entity.Post;
import cn.easy.boot3.admin.post.service.IPostService;
import cn.easy.boot3.admin.role.entity.Role;
import cn.easy.boot3.admin.role.service.IRoleService;
import cn.easy.boot3.admin.user.entity.*;
import cn.easy.boot3.admin.user.mapper.AdminUserMapper;
import cn.easy.boot3.admin.user.service.AdminUserService;
import cn.easy.boot3.admin.userPost.service.IUserPostService;
import cn.easy.boot3.admin.userRole.service.IUserRoleService;
import cn.easy.boot3.common.excel.entity.ImportExcelError;
import jakarta.annotation.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private IMenuService menuService;

    @Resource
    private IPostService postService;

    @Resource
    private IDepartmentService departmentService;



    @Override
    public IPage<AdminUser> selectPage(AdminUserQuery query) {
        Page<AdminUser> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(AdminUser::getUsername, query.getKeyword()).or()
                            .like(AdminUser::getName, query.getKeyword()).or()
                            .like(AdminUser::getMobile, query.getKeyword()).or()
                            .like(AdminUser::getEmail, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getUsername()), AdminUser::getUsername, query.getUsername())
                .like(StrUtil.isNotEmpty(query.getName()), AdminUser::getName, query.getName())
                .eq(Objects.nonNull(query.getDepartmentId()) && query.getDepartmentId() != 0,
                        AdminUser::getDepartmentId, query.getDepartmentId())
                .eq(query.getSex() != null, AdminUser::getSex, query.getSex())
                .eq(query.getStatus() != null, AdminUser::getStatus, query.getStatus())
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
        List<Role> roles = roleService.selectNotDisabledListByUserId(id);
        List<Long> roleIds = roles.stream().map(BaseEntity::getId)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
        List<String> codes = roles.stream().map(Role::getCode)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
        List<Long> postIds = postService.selectNotDisabledIdsByUserId(id);
        vo.setIsAdmin(codes.contains(Constant.ADMIN));
        vo.setRoleIds(roleIds);
        vo.setPostIds(postIds);
        return vo;
    }

    @Override
    public AdminUser getByUsername(@NonNull String username) {
        return lambdaQuery().eq(AdminUser::getUsername, username).one();
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
            List<String> codes = roleService.selectNotDisabledCodesInRoleIds(dto.getRoleIds());
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
        if (!dto.getIsStatusChange()) {
            // 删除所有岗位重新分配
            userPostService.deleteByUserId(dto.getId());
            userPostService.userBindPost(dto.getPostIds(), dto.getId());
        }
        List<Long> roleIds = userRoleService.selectRoleIdsByUserId(dto.getId());
        List<String> codes = roleService.selectNotDisabledCodesInRoleIds(roleIds);
        if (!codes.contains(Constant.ADMIN)) {
            // 不允许绑定超级管理员
            codes = roleService.selectNotDisabledCodesInRoleIds(dto.getRoleIds());
            if (codes.contains(Constant.ADMIN)) {
                throw new BusinessException("无法绑定超级管理员");
            }
            if (!dto.getIsStatusChange()) {
                // 删除所有角色重新分配
                roleIds = roleService.selectNotDisabledIdsInRoleIds(dto.getRoleIds());
                userRoleService.deleteByUserId(dto.getId());
                userRoleService.userBindRole(roleIds, dto.getId());
            }
        } else {
            if (dto.getStatus() != null && dto.getStatus() == 2) {
                throw new BusinessException("无法禁用该用户");
            }
        }
        AdminUser user = BeanUtil.copyBean(dto, AdminUser.class);
        // 禁用账号退出登录
        if (dto.getStatus() != null && dto.getStatus() == 2) {
            UserContext.kickoutAndRemoveCache(dto.getId());
        }
        if (!dto.getIsStatusChange()) {
            LambdaUpdateWrapper<AdminUser> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(AdminUser::getDepartmentId, user.getDepartmentId())
                    .set(AdminUser::getAvatar, user.getAvatar())
                    .set(user.getName() != null, AdminUser::getName, user.getName())
                    .set(user.getSex() != null, AdminUser::getSex, user.getSex())
                    .set(AdminUser::getEmail, user.getEmail())
                    .set(user.getMobile() != null, AdminUser::getMobile, user.getMobile())
                    .set(user.getStatus() != null, AdminUser::getStatus, user.getStatus())
                    .set(user.getSort() != null, AdminUser::getSort, user.getSort())
                    .eq(BaseEntity::getId, user.getId());
            return update(updateWrapper);
        }
        return updateById(user);
    }

    @Override
    public Boolean editPassword(EditPasswordDTO dto) {
        AdminUser adminUser = this.detail(dto.getId());
        String oldPassword = DigestUtil.md5Hex(dto.getOldPassword() + adminUser.getSalt());
        if (!oldPassword.equals(adminUser.getPassword())) {
            throw new BusinessException("旧密码错误");
        }
        if (dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new BusinessException("旧密码不能与新密码相同");
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
            UserContext.kickoutAndRemoveCache(dto.getId());
        }
        return status;
    }

    @Override
    public Boolean resetPassword(ResetPasswordDTO dto) {
        boolean isAdmin = roleService.isAdmin(dto.getId());
        if (isAdmin) {
            Long id = UserContext.getId();
            isAdmin = roleService.isAdmin(id);
            if (!isAdmin) {
                throw new BusinessException("无法重置该用户密码");
            }
        }
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
            UserContext.kickoutAndRemoveCache(dto.getId());
        }
        return status;
    }

    @Override
    public AdminUserInfo getInfo() {
        Long id = UserContext.getId();
        AdminUser adminUser = this.detail(id);
        AdminUserInfo info = BeanUtil.copyBean(adminUser, AdminUserInfo.class);
        if (info.getDepartmentId() != null) {
            Department department = departmentService.detail(info.getDepartmentId());
            info.setDepartment(department);
        }
        // 获取角色列表
        List<Role> roles = roleService.selectNotDisabledListByUserId(id);
        List<Long> roleIds = roles.stream().map(BaseEntity::getId)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
        List<String> codes = roles.stream()
                .map(Role::getCode)
                .filter(Objects::nonNull)
                .distinct().collect(Collectors.toList());
        // 获取岗位列表
        List<Post> posts = postService.selectNotDisabledListByUserId(id);
        // 获取菜单ID集合
        List<Long> menuIds = menuService.selectNotDisabledIdsInRoleIds(roleIds);
        MenuTreeQuery query = MenuTreeQuery.builder()
                .status(1)
                .parentId(0L)
                .build();
        // 获取菜单树 与已拥有的菜单进行对比
        List<MenuTree> menus = menuService.treeList(query);
        if (!menus.isEmpty()) {
            menus = menus.get(0).getChildren();
        }
        List<String> permissions = new ArrayList<>();
        if (codes.contains(Constant.ADMIN)) {
            info.setIsAdmin(true);
            // 超级管理员拥有所有权限和菜单
            permissions = menuService.selectPermissionAll();
        } else {
            info.setIsAdmin(false);
            eachMenus(menus, menuIds, permissions);
        }
        info.setRoles(roles);
        info.setPosts(posts);
        info.setRoleCodes(codes);
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
        int mode = 1;   // 1：严格模式 2：非严格模式
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
        boolean isAdmin = roleService.isAdmin(id);
        if (isAdmin) {
            throw new BusinessException("该用户不允许删除");
        }
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
        List<Long> roleIds = userRoleService.selectRoleIdsByUserIds(ids);
        List<String> codes = roleService.selectCodesInRoleIds(roleIds);
        if (codes.contains(Constant.ADMIN)) {
            throw new BusinessException("存在不允许删除的用户，无法删除");
        }
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
