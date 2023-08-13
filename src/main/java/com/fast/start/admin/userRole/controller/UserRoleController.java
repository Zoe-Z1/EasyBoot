package com.fast.start.admin.userRole.controller;

import com.fast.start.admin.userRole.service.IUserRoleService;
import com.fast.start.common.base.BaseController;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 用户角色关联 前端控制器
 */
//@Api(tags = "用户角色关联接口")
//@RestController
//@RequestMapping("/admin/userRole")
public class UserRoleController extends BaseController {

    @Resource
    private IUserRoleService userRoleService;


}
