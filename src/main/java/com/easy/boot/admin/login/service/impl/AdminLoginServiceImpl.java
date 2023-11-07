package com.easy.boot.admin.login.service.impl;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.easy.boot.admin.dataDict.entity.DataDict;
import com.easy.boot.admin.dataDictDomain.service.IDataDictDomainService;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.LoginHandlerAfterDO;
import com.easy.boot.admin.login.service.AdminLoginService;
import com.easy.boot.admin.login.service.CheckLoginHandler;
import com.easy.boot.admin.login.service.LoginAfterHandler;
import com.easy.boot.admin.loginLog.entity.LoginLogCreateDTO;
import com.easy.boot.admin.loginLog.service.ILoginLogService;
import com.easy.boot.admin.onlineUser.service.IOnlineUserService;
import com.easy.boot.admin.operationLog.enums.OperateStatusEnum;
import com.easy.boot.admin.sysConfig.entity.SysConfig;
import com.easy.boot.admin.sysConfig.enums.SysConfigCodeEnum;
import com.easy.boot.admin.sysConfigDomain.enums.SysConfigDomainCodeEnum;
import com.easy.boot.admin.sysConfigDomain.service.ISysConfigDomainService;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.admin.user.service.AdminUserService;
import com.easy.boot.common.redis.EasyRedisManager;
import com.easy.boot.common.redis.RedisKeyEnum;
import com.easy.boot.common.saToken.UserContext;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
@Slf4j
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private ILoginLogService loginLogService;

    @Resource
    private IOnlineUserService onlineUserService;

    @Resource
    private HttpServletRequest request;

    @Autowired
    private List<LoginAfterHandler> loginAfterHandlers;

    @Autowired
    private List<CheckLoginHandler> checkLoginHandlers;

    @Resource
    private ImageCaptchaApplication application;

    @Resource
    private ISysConfigDomainService sysConfigDomainService;

    @Value("${sa-token.timeout:1800}")
    private Long timeout;

    @Resource
    private EasyRedisManager easyRedisManager;

    @Resource
    private IDataDictDomainService dataDictDomainService;



    @Override
    public AdminUser login(LoginDTO dto) {
        // 登录
        AdminUser user = adminUserService.login(dto.getUsername(), dto.getPassword());
        // 解析登录信息
        String ip = IpUtil.getIp(request);
        String userAgent = request.getHeader("User-Agent");
        LoginLogCreateDTO loginLog = LoginLogCreateDTO.builder()
                .username(dto.getUsername())
                .ip(ip)
                .userId(user == null ? null : user.getId())
                .userAgent(userAgent)
                .build();
        for (LoginAfterHandler loginAfterHandler : loginAfterHandlers) {
            LoginHandlerAfterDO afterDO = loginAfterHandler.handler(user, dto);
            if (!afterDO.getStatus()) {
                loginLog.setStatus(String.valueOf(OperateStatusEnum.FAIL));
                loginLog.setRemarks(afterDO.getMessage());
                loginLogService.asyncCreate(loginLog);
                throw new BusinessException(afterDO.getMessage());
            }
        }
        UserContext.login(user);
        loginLog.setToken(StpUtil.getTokenValue());
        loginLog.setStatus(String.valueOf(OperateStatusEnum.SUCCESS));
        loginLog.setRemarks("登录成功");
        loginLogService.asyncCreate(loginLog);
        return user;
    }

    @Override
    public void logout() {
        if (UserContext.isLogin()) {
            String token = StpUtil.getTokenValue();
            onlineUserService.deleteByToken(token);
        }
        UserContext.logout();
    }

    @Override
    public void checkLogin() {
        UserContext.checkAdminUserLogin();
        Long id = UserContext.getId();
        for (CheckLoginHandler checkLoginHandler : checkLoginHandlers) {
            checkLoginHandler.check(id);
        }
        // 续签Token
        StpUtil.renewTimeout(timeout);
    }

    @Override
    public CaptchaResponse<ImageCaptchaVO> getCode() {
//        RANDOM (随机)、SLIDER (滑块验证码)、ROTATE (旋转验证码)、CONCAT (滑动还原验证码)、WORD_IMAGE_CLICK (文字点选验证码)
        SysConfig sysConfig = sysConfigDomainService.getNotDisabledByDomainCodeAndConfigCode(
                SysConfigDomainCodeEnum.GLOBAL.getCode(),
                SysConfigCodeEnum.CAPTCHA_TYPE.getCode()
        );
        if (sysConfig == null) {
            sysConfig = new SysConfig();
            // 不设置则默认为滑块验证码
            sysConfig.setValue(CaptchaTypeConstant.SLIDER);
        }
        // 随机验证码
        String random = "RANDOM";
        if (sysConfig.getValue().equals(random)) {
            List<DataDict> dataDicts = dataDictDomainService.selectListByDomainCode(SysConfigCodeEnum.CAPTCHA_TYPE.getCode());
            if (CollUtil.isNotEmpty(dataDicts)) {
                Collections.shuffle(dataDicts);
                sysConfig.setValue(dataDicts.get(0).getCode());
            }
        }
        return application.generateCaptcha(sysConfig.getValue());
    }

    @Override
    public ApiResponse<?> validateCode(String id, ImageCaptchaTrack track) {
        ApiResponse<?> response = application.matching(id, track);
        if (response.isSuccess()) {
            String key = RedisKeyEnum.ADMIN_LOGIN_CAPTCHA.getKey(id);
            easyRedisManager.put(key, id, 5L);
        } else {
            log.error("validateCode code -> {}, msg -> {} ", response.getCode(), response.getMsg());
        }
        return response;
    }
}
