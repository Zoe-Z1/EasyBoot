package com.easy.boot.admin.login.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.LoginHandlerAfterDO;
import com.easy.boot.admin.login.service.AdminLoginService;
import com.easy.boot.admin.login.service.CheckLoginHandler;
import com.easy.boot.admin.login.service.LoginAfterHandler;
import com.easy.boot.admin.loginLog.entity.LoginLogCreateDTO;
import com.easy.boot.admin.loginLog.service.ILoginLogService;
import com.easy.boot.admin.operationLog.enums.OperateStatusEnum;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.admin.user.service.AdminUserService;
import com.easy.boot.common.redis.EasyRedisManager;
import com.easy.boot.common.saToken.UserContext;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private ILoginLogService loginLogService;

    @Resource
    private HttpServletRequest request;

    @Autowired
    private List<LoginAfterHandler> loginAfterHandlers;

    @Autowired
    private List<CheckLoginHandler> checkLoginHandlers;

    @Resource
    private EasyRedisManager easyRedisManager;

    @Value("${sa-token.timeout:1800}")
    private Long timeout;

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
        loginLog.setIsOnline(0);
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
            loginLogService.updateIsOnlineByToken(1, token);
        }
        UserContext.logout();
    }

    @Override
    public void checkLogin() {
        UserContext.checkAdminUserLogin();
        Long id = UserContext.getId();
        // 续签Token
        StpUtil.renewTimeout(timeout);
        for (CheckLoginHandler checkLoginHandler : checkLoginHandlers) {
            checkLoginHandler.check(id);
        }
    }
}
