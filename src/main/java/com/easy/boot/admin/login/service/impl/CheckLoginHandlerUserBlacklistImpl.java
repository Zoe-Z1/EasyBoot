package com.easy.boot.admin.login.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.easy.boot.admin.blacklist.entity.Blacklist;
import com.easy.boot.admin.blacklist.service.IBlacklistService;
import com.easy.boot.admin.login.service.CheckLoginHandler;
import com.easy.boot.admin.onlineUser.service.IOnlineUserService;
import com.easy.boot.common.saToken.UserContext;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.exception.enums.SystemErrorEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录状态检查器
 */
@Service
public class CheckLoginHandlerUserBlacklistImpl implements CheckLoginHandler {

    @Resource
    private IBlacklistService blacklistService;

    @Resource
    private IOnlineUserService onlineUserService;

    @Override
    public void check(Long id) {
        // 查询用户黑名单
        Blacklist blacklist = blacklistService.getByUserId(id);
        if (Objects.nonNull(blacklist)) {
            if (blacklist.getDuration() == 0) {
                throw new BusinessException(SystemErrorEnum.USER_IS_BLACKLIST);
            }
            // 计算拉黑结束时间 大于当前时间则代表拉黑中
            long endTime = blacklist.getCreateTime() + blacklist.getDuration() * 60 * 1000;
            if (endTime > DateUtil.current()) {
                String token = StpUtil.getTokenValue();
                UserContext.kickout(token);
                onlineUserService.deleteByToken(token);
                throw new BusinessException(SystemErrorEnum.USER_IS_BLACKLIST);
            }
        }
    }
}
