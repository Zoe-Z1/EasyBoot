package com.easy.boot.admin.onlineUser.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.onlineUser.entity.OnlineUser;
import com.easy.boot.admin.onlineUser.entity.OnlineUserQuery;
import com.easy.boot.admin.onlineUser.service.IOnlineUserService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zoe
 * @date 2023/08/02
 * @description 在线用户 前端控制器
 */
@Slf4j
@Tag(name = "在线用户接口")
@RestController
@RequestMapping("/admin/onlineUser")
public class OnlineUserController extends BaseController {

    @Resource
    private IOnlineUserService loginLogService;


    @SaCheckPermission(value = "ops:online:user:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取在线用户列表")
    @EasyLog(module = "分页获取在线用户列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<OnlineUser>> page(@Validated OnlineUserQuery query) {
        IPage<OnlineUser> page = loginLogService.selectPage(query);
        return Result.success(page);
    }

    @SaCheckPermission(value = "ops:online:user:kickout")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "下线用户")
    @EasyLog(module = "下线用户", operateType = OperateTypeEnum.KICKOUT)
    @PostMapping("/kickout/{id}")
    public Result kickout(@PathVariable Long id) {
        return Result.r(loginLogService.kickoutById(id));
    }

}
