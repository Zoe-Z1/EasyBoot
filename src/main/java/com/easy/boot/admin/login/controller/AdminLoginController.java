package com.easy.boot.admin.login.controller;


import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.TokenVO;
import com.easy.boot.admin.login.service.AdminLoginService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.captcha.vo.CaptchaResponse;
import com.easy.boot.common.captcha.vo.ImageCaptchaVO;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.easy.boot.common.saToken.UserContext;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Slf4j
@Tag(name = "登录接口")
@Validated
@RestController
@RequestMapping("/admin")
public class AdminLoginController {

    @Resource
    private AdminLoginService adminLoginService;


    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "登录")
    @EasyLog(module = "登录", operateType = OperateTypeEnum.LOGIN)
    @PostMapping(value = "/login")
    public Result<TokenVO> login(@Validated @RequestBody LoginDTO dto) {
        AdminUser user = adminLoginService.login(dto);
        TokenVO token = TokenVO.builder()
                .tokenName(UserContext.getTokenName())
                .token(UserContext.getToken())
                .build();
        return Result.success(token);
    }

    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "注销登录")
    @EasyLog(module = "注销登录", operateType = OperateTypeEnum.LOGOUT)
    @PostMapping(value = "/logout")
    public Result logout() {
        adminLoginService.logout();
        return Result.success();
    }

    //todo 方法可能不兼容
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取验证码")
    @EasyLog(module = "获取验证码", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/code")
    public Result<CaptchaResponse<ImageCaptchaVO>> code() {
        return Result.success(adminLoginService.getCode());
    }

    @EasyNoRepeatSubmit
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "校验验证码")
    @EasyLog(module = "校验验证码", operateType = OperateTypeEnum.OTHER)
    @PostMapping(value = "/validate/code/{id}")
    public Result<CaptchaResponse<ImageCaptchaVO>> validateCode(@PathVariable String id, @RequestBody ImageCaptchaTrack track) {
        ApiResponse<?> matching = adminLoginService.validateCode(id, track);
        return Result.success(String.valueOf(matching.isSuccess()));
    }

}
