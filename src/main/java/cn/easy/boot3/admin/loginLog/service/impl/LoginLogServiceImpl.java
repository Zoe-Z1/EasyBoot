package cn.easy.boot3.admin.loginLog.service.impl;

import cn.easy.boot3.common.base.BaseEntity;
import cn.easy.boot3.utils.BeanUtil;
import cn.easy.boot3.utils.JsonUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easy.boot3.admin.loginLog.entity.LoginLog;
import cn.easy.boot3.admin.loginLog.entity.LoginLogCreateDTO;
import cn.easy.boot3.admin.loginLog.entity.LoginLogQuery;
import cn.easy.boot3.admin.loginLog.mapper.LoginLogMapper;
import cn.easy.boot3.admin.loginLog.service.ILoginLogService;
import cn.easy.boot3.admin.onlineUser.entity.OnlineUser;
import cn.easy.boot3.admin.onlineUser.service.IOnlineUserService;
import cn.easy.boot3.admin.operationLog.enums.OperateStatusEnum;
import cn.easy.boot3.admin.sysConfig.entity.SysConfig;
import cn.easy.boot3.admin.sysConfig.enums.SysConfigCodeEnum;
import cn.easy.boot3.admin.sysConfigDomain.enums.SysConfigDomainCodeEnum;
import cn.easy.boot3.admin.sysConfigDomain.service.ISysConfigDomainService;
import jakarta.annotation.Resource;
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

    @Resource
    private IOnlineUserService onlineUserService;

    @Resource
    private ISysConfigDomainService sysConfigDomainService;

    @Resource
    private LoginLogMapper loginLogMapper;

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
        if (StrUtil.isNotEmpty(dto.getToken())) {
            OnlineUser onlineUser = BeanUtil.copyBean(loginLog, OnlineUser.class);
            onlineUser.setId(null);
            onlineUser.setToken(dto.getToken());
            onlineUserService.save(onlineUser);
        }
    }

    @Override
    public LoginLog getLoginLog(String userAgent, String ip) {
        LoginLog log = new LoginLog();
        Map<String, Object> map = new HashMap<>();
        map.put("json", true);
        map.put("ip", ip);
//        String url = "https://whois.pconline.com.cn/ipJson.jsp";
        SysConfig sysConfig = sysConfigDomainService.getNotDisabledByDomainCodeAndConfigCode(
                SysConfigDomainCodeEnum.GLOBAL.getCode(), SysConfigCodeEnum.IP_PARSE_URL.getCode()
                );
        String url = sysConfig.getValue();
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
    public Long getLoginNumber(Long startTime, Long endTime) {
        return lambdaQuery()
                .ge(startTime != null, BaseEntity::getCreateTime, startTime)
                .le(endTime != null, BaseEntity::getCreateTime, endTime)
                .eq(LoginLog::getStatus, String.valueOf(OperateStatusEnum.SUCCESS))
                .count();
    }


    @Override
    public Long getIpNumber(Long startTime, Long endTime) {
        return loginLogMapper.getIpNumber(startTime, endTime);
    }

}
