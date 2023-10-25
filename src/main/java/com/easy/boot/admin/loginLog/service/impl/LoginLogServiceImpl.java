package com.easy.boot.admin.loginLog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.loginLog.entity.LoginLog;
import com.easy.boot.admin.loginLog.entity.LoginLogCreateDTO;
import com.easy.boot.admin.loginLog.entity.LoginLogQuery;
import com.easy.boot.admin.loginLog.mapper.LoginLogMapper;
import com.easy.boot.admin.loginLog.service.ILoginLogService;
import com.easy.boot.admin.operationLog.enums.RoleTypeEnum;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.redis.EasyRedisManager;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

/**
* @author zoe
* @date 2023/08/02
* @description 登录日志 服务实现类
*/
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Value("${sa-token.token-name}")
    private String tokenName;

    @Resource
    private EasyRedisManager easyRedisManager;

    @Override
    public IPage<LoginLog> selectPage(LoginLogQuery query) {
        // 更新完在线状态再查询
        updateIsOnline();
        Page<LoginLog> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .select(BaseEntity::getId, LoginLog::getUsername, LoginLog::getIp, LoginLog::getBrowser,
                        LoginLog::getOs, LoginLog::getEngine, LoginLog::getAddr, LoginLog::getStatus,
                        LoginLog::getIsOnline, LoginLog::getRemarks, BaseEntity::getCreateTime)
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(LoginLog::getIp, query.getKeyword()).or()
                            .like(LoginLog::getUsername, query.getKeyword()).or()
                            .like(LoginLog::getAddr, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getBrowser()), LoginLog::getBrowser, query.getBrowser())
                .like(StrUtil.isNotEmpty(query.getOs()), LoginLog::getOs, query.getOs())
                .eq(StrUtil.isNotEmpty(query.getStatus()), LoginLog::getStatus, query.getStatus())
                .eq(query.getIsOnline() != null, LoginLog::getIsOnline, query.getIsOnline())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Async("LogThreadPoolTaskExecutor")
    @Override
    public void asyncCreate(@Validated LoginLogCreateDTO dto) {
        LoginLog loginLog = this.getLoginLog(dto.getUserAgent(), dto.getIp());
        loginLog.setIsOnline(dto.getIsOnline());
        loginLog.setToken(dto.getToken());
        loginLog.setUsername(dto.getUsername());
        loginLog.setStatus(dto.getStatus());
        loginLog.setRemarks(dto.getRemarks());
        loginLog.setCreateBy(dto.getUserId());
        loginLog.setCreateUsername(dto.getUsername());
        save(loginLog);
    }

    @Override
    public LoginLog getLoginLog(String userAgent, String ip) {
        LoginLog log = new LoginLog();
        Map<String, Object> map = new HashMap<>();
        map.put("json", true);
        map.put("ip", ip);
        String url = "https://whois.pconline.com.cn/ipJson.jsp";
        String result = HttpUtil.get(url, map);
        if (StrUtil.isEmpty(result)) {
            return log;
        }
        try{
            log = JsonUtil.toBean(result, LoginLog.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        UserAgent ua = UserAgentUtil.parse(userAgent);
        log.setBrowser(ua.getBrowser().getName());
        log.setOs(ua.getOs().getName());
        log.setEngine(ua.getEngine().getName());
        return log;
    }

    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        return removeBatchByIds(ids);
    }

    @Override
    public Boolean clear() {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        return remove(queryWrapper);
    }

    @Override
    public Boolean updateIsOnlineByToken(Integer isOnline, String token) {
        UpdateWrapper<LoginLog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_online", isOnline)
                .eq("token", token);
        return update(updateWrapper);
    }

    @Override
    public void updateIsOnline() {
        // 获取所有在线用户进行更新
        List<LoginLog> list = lambdaQuery()
                .select(BaseEntity::getId, LoginLog::getIsOnline, LoginLog::getToken)
                .eq(LoginLog::getIsOnline, 0)
                .list();
        // 定义模糊匹配的key
        String prefix = String.join(":", tokenName, String.valueOf(RoleTypeEnum.WEB), "token:");
        Iterator<LoginLog> iterator = list.iterator();
        while (iterator.hasNext()) {
            LoginLog loginLog = iterator.next();
            String key = prefix + loginLog.getToken();
            boolean has = easyRedisManager.hasKey(key);
            if (has) {
                iterator.remove();
            } else {
                loginLog.setIsOnline(1);
            }
        }
        if (!list.isEmpty()) {
            updateBatchById(list);
        }
    }

    @Override
    public Boolean kickoutById(Long id) {
        LoginLog loginLog = getById(id);
        if (loginLog.getIsOnline() == 1) {
            throw new BusinessException("操作失败，用户已下线");
        }
        loginLog.setIsOnline(1);
        StpUtil.kickoutByTokenValue(loginLog.getToken());
        return updateById(loginLog);
    }

}
