package com.easy.boot.admin.login.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.TokenVO;
import com.easy.boot.admin.login.service.AdminLoginService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.operationLog.enums.RoleTypeEnum;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.redisson.EasyLock;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Slf4j
@Api(tags = "登录接口")
@Validated
@RestController
@RequestMapping("/admin")
public class AdminLoginController {

    @Resource
    private AdminLoginService adminLoginService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "登录")
    @EasyLog(module = "登录", operateType = OperateTypeEnum.LOGIN)
    @PostMapping(value = "/login")
    public Result<TokenVO> login(@Validated @RequestBody LoginDTO dto) {
        AdminUser user = adminLoginService.login(dto);
        StpLogic stpLogic = new StpLogic(String.valueOf(RoleTypeEnum.WEB));
        StpUtil.setStpLogic(stpLogic);
        StpUtil.login(user.getId());
        SaManager.getSaTokenDao().setObject(user.getId().toString(), user, -1);
        TokenVO token = TokenVO.builder()
                .tokenName(SaManager.getConfig().getTokenName())
                .token(SaManager.getConfig().getTokenPrefix() + " " + StpUtil.getTokenValue())
                .build();
        return Result.success(token);
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "注销登录")
    @EasyLog(module = "注销登录", operateType = OperateTypeEnum.LOGOUT)
    @PostMapping(value = "/logout")
    public Result logout() {
        StpUtil.logout();
        return Result.success();
    }

    @EasyLock(leaseTime = 10, waitTime = 1)
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "测试")
    @PostMapping(value = "/test")
    public Result test(String username) {
        long start = DateUtil.current();
        log.info("test start time ->>>  {}", start);

        ThreadUtil.sleep(10000L);

        long end = DateUtil.current();
        log.info("test end time ->>>  {}", end);
        log.info("diff time ->>>  {}", end - start);
        return Result.success();
    }

}
