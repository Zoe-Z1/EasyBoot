package com.easy.boot.admin.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot.admin.user.entity.*;
import com.easy.boot.common.excel.entity.ImportExcelError;

import java.util.List;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
public interface AdminUserService extends IService<AdminUser> {

    /**
     * 查询用户
     * @param query
     * @return
     */
    IPage<AdminUser> selectPage(AdminUserQuery query);

    /**
     * 获取用户详情
     * @param id
     * @return
     */
    AdminUserVO detail(Long id);

    /**
     * 使用账号获取用户信息
     * @param username
     * @return
     */
    AdminUser getByUsername(String username);

    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @return
     */
    AdminUser login(String username, String password);

    /**
     * 创建用户
     * @param dto
     * @return
     */
    Boolean create(AdminUserCreateDTO dto);

    /**
     * 编辑用户
     * @param dto
     * @return
     */
    Boolean updateById(AdminUserUpdateDTO dto);

    /**
     * 修改密码
     * @param dto
     * @return
     */
    Boolean editPassword(EditPasswordDTO dto);

    /**
     * 重置密码
     * @param dto
     * @return
     */
    Boolean resetPassword(ResetPasswordDTO dto);

    /**
     * 获取当前用户信息
     * @return
     */
    AdminUserInfo getInfo();

    /**
     * 删除用户
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

    /**
     * 导入Excel
     * @param list 要导入的数据集合
     * @param errorList 导入错误的数据集合
     * @param errors 错误标注集合
     */
    void importExcel(List<AdminUser> list, List<AdminUser> errorList, List<ImportExcelError> errors);
}
