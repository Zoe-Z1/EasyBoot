package com.fast.start.admin.userPost.controller;

import com.fast.start.admin.userPost.service.IUserPostService;
import com.fast.start.common.base.BaseController;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 用户岗位关联 前端控制器
 */
//@Api(tags = "用户岗位关联接口")
//@RestController
//@RequestMapping("/admin/userPost")
public class UserPostController extends BaseController {

    @Resource
    private IUserPostService userPostService;


}
