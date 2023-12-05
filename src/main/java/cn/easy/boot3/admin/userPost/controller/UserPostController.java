package cn.easy.boot3.admin.userPost.controller;

import cn.easy.boot3.admin.userPost.service.IUserPostService;
import cn.easy.boot3.common.base.BaseController;
import jakarta.annotation.Resource;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 用户岗位关联 前端控制器
 */
//@Tag(name = "用户岗位关联接口")
//@RestController
//@RequestMapping("/admin/userPost")
public class UserPostController extends BaseController {

    @Resource
    private IUserPostService userPostService;


}
