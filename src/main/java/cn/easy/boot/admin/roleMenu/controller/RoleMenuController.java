package cn.easy.boot.admin.roleMenu.controller;

import cn.easy.boot.common.base.BaseController;
import cn.easy.boot.admin.roleMenu.service.IRoleMenuService;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 角色菜单关联 前端控制器
 */
//@Api(tags = "角色菜单关联接口")
//@RestController
//@RequestMapping("/admin/roleMenu")
public class RoleMenuController extends BaseController {

    @Resource
    private IRoleMenuService roleMenuService;



}
