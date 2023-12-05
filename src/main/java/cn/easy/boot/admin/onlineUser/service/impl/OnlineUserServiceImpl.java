package cn.easy.boot.admin.onlineUser.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.easy.boot.admin.onlineUser.entity.OnlineUser;
import cn.easy.boot.admin.onlineUser.entity.OnlineUserQuery;
import cn.easy.boot.admin.onlineUser.mapper.OnlineUserMapper;
import cn.easy.boot.admin.onlineUser.service.IOnlineUserService;
import cn.easy.boot.admin.operationLog.enums.RoleTypeEnum;
import cn.easy.boot.common.base.BaseEntity;
import cn.easy.boot.common.redis.EasyRedisManager;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easy.boot.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author zoe
* @date 2023/08/02
* @description 在线用户 服务实现类
*/
@Service
public class OnlineUserServiceImpl extends ServiceImpl<OnlineUserMapper, OnlineUser> implements IOnlineUserService {

    @Value("${sa-token.token-name}")
    private String tokenName;

    @Resource
    private EasyRedisManager easyRedisManager;

    @Override
    public IPage<OnlineUser> selectPage(OnlineUserQuery query) {
        // 更新完在线用户再查询
        updateIsOnline();
        Page<OnlineUser> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .select(BaseEntity::getId, OnlineUser::getUsername, OnlineUser::getIp, OnlineUser::getBrowser,
                        OnlineUser::getOs, OnlineUser::getEngine, OnlineUser::getAddr, BaseEntity::getCreateTime,
                        BaseEntity::getCreateBy)
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(OnlineUser::getIp, query.getKeyword()).or()
                            .like(OnlineUser::getUsername, query.getKeyword()).or()
                            .like(OnlineUser::getAddr, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getBrowser()), OnlineUser::getBrowser, query.getBrowser())
                .like(StrUtil.isNotEmpty(query.getOs()), OnlineUser::getOs, query.getOs())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public Boolean deleteByToken(String token) {
        QueryWrapper<OnlineUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        return remove(queryWrapper);
    }

    @Override
    public void updateIsOnline() {
        // 获取所有在线用户进行更新
        List<OnlineUser> list = lambdaQuery()
                .select(BaseEntity::getId, OnlineUser::getToken)
                .list();
        List<Long> ids = new ArrayList<>();
        // 定义模糊匹配的key
        String prefix = String.join(":", tokenName, String.valueOf(RoleTypeEnum.WEB), "token:");
        list.forEach(item -> {
            String key = prefix + item.getToken();
            boolean has = easyRedisManager.hasKey(key);
            if (!has) {
                ids.add(item.getId());
            }
        });
        if (!ids.isEmpty()) {
            removeBatchByIds(ids);
        }
    }

    @Override
    public Boolean kickoutById(Long id) {
        OnlineUser onlineUser = getById(id);
        if (onlineUser == null) {
            throw new BusinessException("操作失败，用户已下线");
        }
        StpUtil.kickoutByTokenValue(onlineUser.getToken());
        return removeById(id);
    }

    @Override
    public Long getOnlineNumber() {
        return count();
    }

}
