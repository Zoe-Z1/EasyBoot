package com.easy.boot.admin.login.service.impl;

import cn.hutool.core.date.DateUtil;
import com.easy.boot.admin.blacklist.entity.Blacklist;
import com.easy.boot.admin.blacklist.service.IBlacklistService;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.LoginHandlerAfterDO;
import com.easy.boot.admin.login.service.LoginAfterHandler;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.utils.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录后IP黑名单处理器
 */
@Service
public class LoginAfterHandlerIpBlacklistImpl implements LoginAfterHandler {

    @Resource
    private HttpServletRequest request;

    @Resource
    private IBlacklistService blacklistService;

    @Override
    public LoginHandlerAfterDO handler(AdminUser user, LoginDTO dto) {
        LoginHandlerAfterDO afterDO = LoginHandlerAfterDO.builder()
                .status(true)
                .build();
        String ip = IpUtil.getIp(request);
        // 查询IP黑名单
        Blacklist blacklist = blacklistService.getByIp(ip);
        if (Objects.nonNull(blacklist)) {
            if (blacklist.getDuration() != 0) {
                // 计算拉黑结束时间 大于当前时间则代表拉黑中
                long endTime = blacklist.getCreateTime() + blacklist.getDuration() * 60 * 1000;
                if (endTime > DateUtil.current()) {
                    afterDO.setStatus(false)
                            .setMessage("IP已被拉黑，无法登录");
                } else {
                    // 不大于当前时间 解除拉黑
                    blacklistService.removeById(blacklist);
                }
            }
        }
        return afterDO;
    }
}
