package cn.easy.boot.admin.login.controller;

import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import cn.easy.boot.admin.login.entity.LoginDTO;
import cn.easy.boot.admin.login.entity.TokenVO;
import cn.easy.boot.admin.user.entity.AdminUser;
import cn.easy.boot.common.base.Result;
import cn.easy.boot.common.log.EasyLog;
import cn.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import cn.easy.boot.common.saToken.UserContext;
import cn.easy.boot.admin.login.service.AdminLoginService;
import cn.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        TokenVO token = TokenVO.builder()
                .tokenName(UserContext.getTokenName())
                .token(UserContext.getToken())
                .build();
        return Result.success(token);
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "注销登录")
    @EasyLog(module = "注销登录", operateType = OperateTypeEnum.LOGOUT)
    @PostMapping(value = "/logout")
    public Result logout() {
        adminLoginService.logout();
        return Result.success();
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取验证码")
    @EasyLog(module = "获取验证码", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/code")
    public Result<CaptchaResponse<ImageCaptchaVO>> code() {
        return Result.success(adminLoginService.getCode());
    }

    @EasyNoRepeatSubmit
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "校验验证码")
    @EasyLog(module = "校验验证码", operateType = OperateTypeEnum.OTHER)
    @PostMapping(value = "/validate/code/{id}")
    public Result<CaptchaResponse<ImageCaptchaVO>> validateCode(@PathVariable String id, @RequestBody ImageCaptchaTrack track) {
        ApiResponse<?> matching = adminLoginService.validateCode(id, track);
        return Result.success(String.valueOf(matching.isSuccess()));
    }

}
