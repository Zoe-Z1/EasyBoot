package com.fast.start.admin.login.service.impl;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.fast.start.admin.blacklist.entity.Blacklist;
import com.fast.start.admin.blacklist.service.IBlacklistService;
import com.fast.start.admin.login.entity.LoginDTO;
import com.fast.start.admin.login.service.AdminLoginService;
import com.fast.start.admin.loginLog.entity.LoginLogCreateDTO;
import com.fast.start.admin.loginLog.service.ILoginLogService;
import com.fast.start.admin.operationLog.enums.OperateStatusEnum;
import com.fast.start.admin.operationLog.enums.RoleTypeEnum;
import com.fast.start.admin.user.entity.AdminUser;
import com.fast.start.admin.user.service.AdminUserService;
import com.fast.start.exception.BusinessException;
import com.fast.start.exception.enums.SystemErrorEnum;
import com.fast.start.utils.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
    private IBlacklistService blacklistService;

    @Resource
    private ILoginLogService loginLogService;

    @Resource
    private HttpServletRequest request;

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
                .userAgent(userAgent)
                .build();
        String remarks = "登录成功";
        if (user == null) {
            remarks = "账号或密码错误";
            loginLog.setStatus(String.valueOf(OperateStatusEnum.FAIL));
            loginLog.setRemarks(remarks);
            loginLogService.asyncCreate(loginLog);
            throw new BusinessException(remarks);
        }
        loginLog.setUserId(user.getId());

        if (user.getStatus() == 2) {
            remarks = "账号已被禁用，无法登录";
            loginLog.setStatus(String.valueOf(OperateStatusEnum.FAIL));
            loginLog.setRemarks(remarks);
            loginLogService.asyncCreate(loginLog);
            throw new BusinessException(remarks);
        }
        // 查询用户黑名单
        Blacklist blacklist = blacklistService.getByUserId(user.getId().toString());
        if (Objects.nonNull(blacklist)) {
            if (blacklist.getDuration() != -1) {
                // 计算拉黑结束时间 大于当前时间则代表拉黑中
                long endTime = blacklist.getCreateTime() + blacklist.getDuration() * 60 * 1000;
                if (endTime > DateUtil.current()) {
                    remarks = "账号已被拉黑，无法登录";
                    loginLog.setStatus(String.valueOf(OperateStatusEnum.FAIL));
                    loginLog.setRemarks(remarks);
                    loginLogService.asyncCreate(loginLog);
                    throw new BusinessException(remarks);
                }
                // 不大于当前时间 解除拉黑
                blacklistService.removeById(blacklist);
            }
        }
        // 查询IP黑名单
        blacklist = blacklistService.getByIp(ip);
        if (Objects.nonNull(blacklist)) {
            if (blacklist.getDuration() != -1) {
                // 计算拉黑结束时间 大于当前时间则代表拉黑中
                long endTime = blacklist.getCreateTime() + blacklist.getDuration() * 60 * 1000;
                if (endTime > DateUtil.current()) {
                    remarks = "IP已被拉黑，无法登录";
                    loginLog.setStatus(String.valueOf(OperateStatusEnum.FAIL));
                    loginLog.setRemarks(remarks);
                    loginLogService.asyncCreate(loginLog);
                    throw new BusinessException(remarks);
                }
                // 不大于当前时间 解除拉黑
                blacklistService.removeById(blacklist);
            }
        }
        loginLog.setStatus(String.valueOf(OperateStatusEnum.SUCCESS));
        loginLog.setRemarks(remarks);
        loginLogService.asyncCreate(loginLog);
        return user;
    }

    @Override
    public void checkLogin() {
        StpLogic stpLogic = new StpLogic(String.valueOf(RoleTypeEnum.WEB));
        StpUtil.setStpLogic(stpLogic);
        StpUtil.checkLogin();
        Long id = Long.valueOf(String.valueOf(StpUtil.getLoginId()));
        AdminUser user = adminUserService.detail(id);
        if (user.getStatus() == 2) {
            throw new BusinessException(SystemErrorEnum.USER_DISABLED);
        }
        // 查询用户黑名单
        Blacklist blacklist = blacklistService.getByUserId(user.getId().toString());
        if (Objects.nonNull(blacklist)) {
            if (blacklist.getDuration() == -1) {
                throw new BusinessException(SystemErrorEnum.USER_IS_BLACKLIST);
            }
            // 计算拉黑结束时间 大于当前时间则代表拉黑中
            long endTime = blacklist.getCreateTime() + blacklist.getDuration() * 60 * 1000;
            if (endTime > DateUtil.current()) {
                throw new BusinessException(SystemErrorEnum.USER_IS_BLACKLIST);
            }
        }
        String ip = IpUtil.getIp(request);
        // 查询IP黑名单
        blacklist = blacklistService.getByIp(ip);
        if (Objects.nonNull(blacklist)) {
            if (blacklist.getDuration() == -1) {
                throw new BusinessException(SystemErrorEnum.IP_IS_BLACKLIST);
            }
            // 计算拉黑结束时间 大于当前时间则代表拉黑中
            long endTime = blacklist.getCreateTime() + blacklist.getDuration() * 60 * 1000;
            if (endTime > DateUtil.current()) {
                throw new BusinessException(SystemErrorEnum.IP_IS_BLACKLIST);
            }
        }
    }
}
