package com.easy.boot.admin.loginLog.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.loginLog.entity.LoginLog;
import com.easy.boot.admin.loginLog.entity.LoginLogCreateDTO;
import com.easy.boot.admin.loginLog.entity.LoginLogQuery;
import com.easy.boot.admin.loginLog.mapper.LoginLogMapper;
import com.easy.boot.admin.loginLog.service.ILoginLogService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.utils.JsonUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
* @author zoe
* @date 2023/08/02
* @description 登录日志 服务实现类
*/
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {

    @Override
    public IPage<LoginLog> selectPage(LoginLogQuery query) {
        Page<LoginLog> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(LoginLog::getIp, query.getKeyword()).or()
                            .like(LoginLog::getUsername, query.getKeyword()).or()
                            .like(LoginLog::getAddr, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getBrowser()), LoginLog::getBrowser, query.getBrowser())
                .like(StrUtil.isNotEmpty(query.getOs()), LoginLog::getOs, query.getOs())
                .eq(StrUtil.isNotEmpty(query.getStatus()), LoginLog::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Async("LogThreadPoolTaskExecutor")
    @Override
    public void asyncCreate(@Validated LoginLogCreateDTO dto) {
        LoginLog loginLog = this.getLoginLog(dto.getUserAgent(), dto.getIp());
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

}
