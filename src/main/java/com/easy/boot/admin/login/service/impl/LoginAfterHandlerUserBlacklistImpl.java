package com.easy.boot.admin.login.service.impl;

import cn.hutool.core.date.DateUtil;
import com.easy.boot.admin.blacklist.entity.Blacklist;
import com.easy.boot.admin.blacklist.service.IBlacklistService;
import com.easy.boot.admin.login.entity.LoginDTO;
import com.easy.boot.admin.login.entity.LoginHandlerAfterDO;
import com.easy.boot.admin.login.service.LoginAfterHandler;
import com.easy.boot.admin.user.entity.AdminUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录后用户黑名单处理器
 */
@Service
public class LoginAfterHandlerUserBlacklistImpl implements LoginAfterHandler {


    @Resource
    private IBlacklistService blacklistService;

    @Override
    public LoginHandlerAfterDO handler(AdminUser user, LoginDTO dto) {
        LoginHandlerAfterDO afterDO = LoginHandlerAfterDO.builder()
                .status(true)
                .build();
        // 查询用户黑名单
        Blacklist blacklist = blacklistService.getByUsername(user.getUsername());
        if (Objects.nonNull(blacklist)) {
            if (blacklist.getEndTime() != 0) {
                // 拉黑结束时间大于当前时间则代表拉黑中
                if (blacklist.getEndTime() > DateUtil.current()) {
                    afterDO.setStatus(false)
                            .setMessage("您已被加入黑名单，无法登录");
                } else {
                    // 不大于当前时间 使拉黑失效
                    blacklistService.updateStatusById(blacklist.getId(), 2);
                }
            }
        }
        return afterDO;
    }
}
