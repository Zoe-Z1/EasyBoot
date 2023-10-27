package com.easy.boot.admin.blacklist.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.blacklist.entity.Blacklist;
import com.easy.boot.admin.blacklist.entity.BlacklistCreateDTO;
import com.easy.boot.admin.blacklist.entity.BlacklistQuery;
import com.easy.boot.admin.blacklist.entity.BlacklistUpdateDTO;
import com.easy.boot.admin.blacklist.mapper.BlacklistMapper;
import com.easy.boot.admin.blacklist.service.IBlacklistService;
import com.easy.boot.admin.role.service.IRoleService;
import com.easy.boot.admin.user.entity.AdminUser;
import com.easy.boot.admin.user.service.AdminUserService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    private IRoleService roleService;

    @Override
    public IPage<Blacklist> selectPage(BlacklistQuery query) {
        updateBlacklistStatus();
        Page<Blacklist> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                            .like(Blacklist::getRelevanceData, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getCreateUsername()), Blacklist::getCreateUsername, query.getCreateUsername())
                .eq(Objects.nonNull(query.getType()), Blacklist::getType, query.getType())
                .eq(Objects.nonNull(query.getStatus()), Blacklist::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        Blacklist::getEndTime, query.getStartTime(), query.getEndTime())
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public void updateBlacklistStatus() {
        List<Blacklist> list = selectNotExpiredAndNotForeverList();
        if (CollUtil.isEmpty(list)) {
            return;
        }
        Long currentTime = DateUtil.current();
        Set<Long> ids = list.stream().filter(item -> item.getEndTime() <= currentTime)
                .map(BaseEntity::getId).collect(Collectors.toSet());
        // 修改黑名单状态为失效
        updateBatchByIds(ids, 2);
    }

    @Override
    public List<Blacklist> selectNotExpiredAndNotForeverList() {
        return lambdaQuery().ne(Blacklist::getEndTime, 0)
                .eq(Blacklist::getStatus, 1)
                .list();
    }

    @Override
    public Blacklist getByUsername(String username) {
        return this.getByRelevanceDataAndType(username, 1);
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
                .eq(Blacklist::getStatus, 1)
                .one();
    }

    @Override
    public Boolean create(BlacklistCreateDTO dto) {
        if (dto.getEndTime() != 0 && dto.getEndTime() < DateUtil.current()) {
            throw new BusinessException("拉黑结束时间不能小于当前时间");
        }
        Blacklist entity = BeanUtil.copyBean(dto, Blacklist.class);
        Blacklist blacklist = this.getByRelevanceDataAndType(dto.getRelevanceData(), dto.getType());
        if (Objects.nonNull(blacklist)) {
            String name = dto.getType() == 1 ? "账号" : "IP";
            throw new BusinessException(name + "已经被加入黑名单，无法再次加入");
        }
        if (dto.getType() == 1) {
            AdminUser adminUser = adminUserService.getByUsername(dto.getRelevanceData());
            if (adminUser != null) {
                Boolean isAdmin = roleService.isAdmin(adminUser.getId());
                if (isAdmin) {
                    throw new BusinessException("无法将该账号加入黑名单");
                }
            }
        } else {
            boolean isIpv4 = Validator.isIpv4(dto.getRelevanceData());
            if (!isIpv4) {
                throw new BusinessException("IP地址格式不正确");
            }
        }
        entity.setStatus(1);
        return save(entity);
    }

    @Override
    public Boolean updateById(BlacklistUpdateDTO dto) {
        if (dto.getEndTime() != 0 && dto.getEndTime() < DateUtil.current()) {
            throw new BusinessException("拉黑结束时间不能小于当前时间");
        }
        Blacklist blacklist = Blacklist.builder()
                .id(dto.getId())
                .endTime(dto.getEndTime())
                .status(1)
                .build();
        return updateById(blacklist);
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
    public Boolean updateBatchByIds(Collection<Long> ids, Integer status) {
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        UpdateWrapper<Blacklist> wrapper = new UpdateWrapper<>();
        wrapper.in("id", ids)
                .set("status", status);
        return update(wrapper);
    }

    @Override
    public Boolean updateStatusById(Long id, Integer status) {
        Blacklist blacklist = Blacklist.builder()
                .id(id)
                .status(status)
                .build();
        return updateById(blacklist);
    }

}
