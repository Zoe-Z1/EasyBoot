package com.easy.boot3.admin.userRole.controller;

import com.easy.boot3.admin.userRole.service.IUserRoleService;
import com.easy.boot3.common.base.BaseController;
import jakarta.annotation.Resource;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 用户角色关联 前端控制器
 */
//@Tag(name = "用户角色关联接口")
//@RestController
//@RequestMapping("/admin/userRole")
public class UserRoleController extends BaseController {

    @Resource
    private IUserRoleService userRoleService;


}
