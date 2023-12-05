package cn.easy.boot3.admin.login.service.impl;

import cn.easy.boot3.common.redis.EasyRedisManager;
import cn.easy.boot3.common.redis.RedisKeyEnum;
import cn.hutool.core.util.StrUtil;
import cn.easy.boot3.admin.login.entity.LoginDTO;
import cn.easy.boot3.admin.login.entity.LoginHandlerAfterDO;
import cn.easy.boot3.admin.login.service.LoginAfterHandler;
import cn.easy.boot3.admin.user.entity.AdminUser;
import jakarta.annotation.Resource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author zoe
 * @date 2023/10/24
 * @description 登录后状态检查处理器
 */
@Order(1)
@Service
public class LoginAfterHandlerCheckStatusImpl implements LoginAfterHandler {

    @Resource
    private EasyRedisManager easyRedisManager;

    @Override
    public LoginHandlerAfterDO handler(AdminUser user, LoginDTO dto) {
        LoginHandlerAfterDO afterDO = LoginHandlerAfterDO.builder()
                .status(true)
                .build();
        String key = RedisKeyEnum.ADMIN_LOGIN_CAPTCHA.getKey(dto.getId());
        String id = easyRedisManager.getString(key);
        easyRedisManager.remove(key);
        if (StrUtil.isEmpty(id)) {
            afterDO.setStatus(false)
                    .setMessage("非法的登录请求");
            return afterDO;
        }
        if (user == null) {
            afterDO.setStatus(false)
                    .setMessage("账号或密码错误");
        } else if (user.getStatus() == 2) {
            afterDO.setStatus(false)
                    .setMessage("账号已被禁用，无法登录");
        }
        return afterDO;
    }
}
