package com.fast.start.admin.login.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.fast.start.admin.login.entity.LoginDTO;
import com.fast.start.admin.login.entity.TokenVO;
import com.fast.start.admin.login.service.AdminLoginService;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.admin.operationLog.enums.RoleTypeEnum;
import com.fast.start.admin.user.entity.AdminUser;
import com.fast.start.common.base.Result;
import com.fast.start.common.log.FastLog;
import com.fast.start.common.redisson.FastLock;
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
    @FastLog(module = "登录", operateType = OperateTypeEnum.LOGIN)
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
    @FastLog(module = "注销登录", operateType = OperateTypeEnum.LOGOUT)
    @PostMapping(value = "/logout")
    public Result logout() {
        StpUtil.logout();
        return Result.success();
    }

    @FastLock(leaseTime = 10, waitTime = 1)
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
