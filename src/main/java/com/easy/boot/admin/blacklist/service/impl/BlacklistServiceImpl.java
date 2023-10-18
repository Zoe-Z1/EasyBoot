package com.easy.boot.admin.blacklist.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.blacklist.entity.Blacklist;
import com.easy.boot.admin.blacklist.entity.BlacklistCreateDTO;
import com.easy.boot.admin.blacklist.entity.BlacklistQuery;
import com.easy.boot.admin.blacklist.entity.BlacklistUpdateDTO;
import com.easy.boot.admin.blacklist.mapper.BlacklistMapper;
import com.easy.boot.admin.blacklist.service.IBlacklistService;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.admin.user.service.AdminUserService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 黑名单 服务实现类
 */
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements IBlacklistService {

    @Resource
    private AdminUserService adminUserService;

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
        return lambdaQuery().ne(Blacklist::getDuration, 0).list();
    }

    @Override
    public Blacklist detail(Long id) {
        return getById(id);
    }

    @Override
    public Blacklist getByUserId(Long userId) {
        return this.getByRelevanceDataAndType(userId.toString(), 1);
    }


    @Override
    public Blacklist getByIp(String ip) {
        return this.getByRelevanceDataAndType(ip, 2);
    }

    @Override
    public Blacklist getByRelevanceDataAndType(String relevanceData, Integer type) {
        return lambdaQuery()
                .eq(Blacklist::getRelevanceData, relevanceData)
                .eq(Blacklist::getType, type)
                .one();
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
        if (dto.getType() == 1) {
            AdminUser adminUser = adminUserService.detail(Long.valueOf(dto.getRelevanceData()));
            if (adminUser == null) {
                throw new BusinessException("拉黑账号不存在");
            }
            entity.setUsername(adminUser.getUsername());
        }
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        return removeBatchByIds(ids);
    }

}
