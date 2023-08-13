package com.fast.start.admin.blacklist.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.start.admin.blacklist.entity.Blacklist;
import com.fast.start.admin.blacklist.entity.BlacklistCreateDTO;
import com.fast.start.admin.blacklist.entity.BlacklistQuery;
import com.fast.start.admin.blacklist.entity.BlacklistUpdateDTO;
import com.fast.start.admin.blacklist.mapper.BlacklistMapper;
import com.fast.start.admin.blacklist.service.IBlacklistService;
import com.fast.start.admin.user.entity.AdminUser;
import com.fast.start.admin.user.service.AdminUserService;
import com.fast.start.common.base.BaseEntity;
import com.fast.start.common.redis.FastRedisManager;
import com.fast.start.common.redis.RedisKeyEnum;
import com.fast.start.exception.BusinessException;
import com.fast.start.utils.BeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 黑名单 服务实现类
 */
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements IBlacklistService {

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private FastRedisManager fastRedisManager;

    @Override
    public IPage<Blacklist> selectPage(BlacklistQuery query) {
        Page<Blacklist> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .like(StrUtil.isNotEmpty(query.getUsername()), Blacklist::getUsername, query.getUsername())
                .eq(Objects.nonNull(query.getType()), Blacklist::getType, query.getType())
                .and(StrUtil.isNotEmpty(query.getRelevanceData()), likeQuery ->
                        likeQuery.eq(Blacklist::getType, 2)
                                .like(Blacklist::getRelevanceData, query.getRelevanceData())
                )
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public List<Blacklist> selectNotForeverList() {
        String key = RedisKeyEnum.NOT_FOREVER_BLACKLIST.getKey();
        List<Blacklist> list = null;
        if (fastRedisManager.hasKey(key)) {
            list = (List<Blacklist>) fastRedisManager.get(key);
        } else {
            list = lambdaQuery().ne(Blacklist::getDuration, -1).list();
            fastRedisManager.put(key, list);
        }
        return list;
    }

    @Override
    public Blacklist detail(Long id) {
        String key = RedisKeyEnum.BLACKLIST_DETAIL.getKey(id);
        boolean isExist = fastRedisManager.hasKey(key);
        Blacklist blacklist = null;
        if (isExist) {
            blacklist = (Blacklist) fastRedisManager.get(key);
        } else {
            blacklist = getById(id);
            fastRedisManager.put(key, blacklist);
        }
        return blacklist;
    }

    @Override
    public Blacklist getByUserId(String userId) {
        return this.getByRelevanceDataAndType(userId, 1);
    }


    @Override
    public Blacklist getByIp(String ip) {
        return this.getByRelevanceDataAndType(ip, 2);
    }

    @Override
    public Blacklist getByRelevanceDataAndType(String relevanceData, Integer type) {
        String key = RedisKeyEnum.IP_BLACKLIST.getKey(relevanceData);
        if (type == 1) {
            key = RedisKeyEnum.USER_BLACKLIST.getKey(relevanceData);
        }
        boolean isExist = fastRedisManager.hasKey(key);
        Blacklist blacklist = null;
        if (isExist) {
            blacklist = (Blacklist) fastRedisManager.get(key);
        } else {
            blacklist = lambdaQuery()
                    .eq(Blacklist::getRelevanceData, relevanceData)
                    .eq(Blacklist::getType, type)
                    .one();
            fastRedisManager.put(key, blacklist);
        }
        return blacklist;
    }

    @Override
    public Boolean create(BlacklistCreateDTO dto) {
        Blacklist entity = BeanUtil.copyBean(dto, Blacklist.class);
        Blacklist blacklist = this.getByRelevanceDataAndType(dto.getRelevanceData(), dto.getType());
        if (Objects.nonNull(blacklist)) {
            String name = dto.getType() == 1 ? "账号" : "IP";
            throw new BusinessException(name + "已经被拉黑，无法再次拉黑");
        }
        if (dto.getType() == 1) {
            AdminUser adminUser = adminUserService.detail(Long.valueOf(dto.getRelevanceData()));
            if (adminUser == null) {
                throw new BusinessException("拉黑的账号不存在");
            }
            entity.setUsername(adminUser.getUsername());
        }
        String key = RedisKeyEnum.NOT_FOREVER_BLACKLIST.getKey();
        fastRedisManager.remove(key);
        return save(entity);
    }

    @Override
    public Boolean updateById(BlacklistUpdateDTO dto) {
        Blacklist entity = BeanUtil.copyBean(dto, Blacklist.class);
        Blacklist blacklist = this.getByRelevanceDataAndType(dto.getRelevanceData(), dto.getType());
        if (Objects.nonNull(blacklist) && !blacklist.getId().equals(dto.getId())) {
            String name = dto.getType() == 1 ? "账号" : "IP";
            throw new BusinessException(name + "已经被拉黑，无法再次拉黑");
        }
        String key = RedisKeyEnum.IP_BLACKLIST.getKey(dto.getRelevanceData());
        if (dto.getType() == 1) {
            key = RedisKeyEnum.USER_BLACKLIST.getKey(dto.getRelevanceData());
            AdminUser adminUser = adminUserService.detail(Long.valueOf(dto.getRelevanceData()));
            if (adminUser == null) {
                throw new BusinessException("拉黑账号不存在");
            }
            entity.setUsername(adminUser.getUsername());
        }
        String detailKey = RedisKeyEnum.BLACKLIST_DETAIL.getKey(dto.getId());
        String notForeverKey = RedisKeyEnum.NOT_FOREVER_BLACKLIST.getKey();
        fastRedisManager.remove(notForeverKey);
        fastRedisManager.remove(detailKey);
        fastRedisManager.remove(key);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        Blacklist blacklist = this.detail(id);
        String key = RedisKeyEnum.IP_BLACKLIST.getKey(blacklist.getRelevanceData());
        if (blacklist.getType() == 1) {
            key = RedisKeyEnum.USER_BLACKLIST.getKey(blacklist.getRelevanceData());
        }
        String notForeverKey = RedisKeyEnum.NOT_FOREVER_BLACKLIST.getKey();
        fastRedisManager.remove(notForeverKey);
        fastRedisManager.remove(key);
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        List<Blacklist> blacklists = this.listByIds(ids);
        List<String> userData = blacklists.stream()
                .filter(item -> item.getType() == 1)
                .map(Blacklist::getRelevanceData)
                .collect(Collectors.toList());
        List<String> ipData = blacklists.stream()
                .filter(item -> item.getType() == 2)
                .map(Blacklist::getRelevanceData)
                .collect(Collectors.toList());
        List<String> ipKeys = RedisKeyEnum.IP_BLACKLIST.getKeys(ipData);
        List<String> userKeys = RedisKeyEnum.USER_BLACKLIST.getKeys(userData);
        List<String> detailKeys = RedisKeyEnum.BLACKLIST_DETAIL.getKeys(ids);
        String notForeverKey = RedisKeyEnum.NOT_FOREVER_BLACKLIST.getKey();
        fastRedisManager.remove(notForeverKey);
        fastRedisManager.remove(detailKeys);
        fastRedisManager.remove(ipKeys);
        fastRedisManager.remove(userKeys);
        return removeBatchByIds(ids);
    }

}
