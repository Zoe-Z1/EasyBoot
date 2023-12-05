package cn.easy.boot.admin.sysConfig.service.impl;

import cn.easy.boot.admin.sysConfig.entity.SysConfig;
import cn.easy.boot.admin.sysConfig.entity.SysConfigCreateDTO;
import cn.easy.boot.admin.sysConfig.entity.SysConfigQuery;
import cn.easy.boot.admin.sysConfig.entity.SysConfigUpdateDTO;
import cn.easy.boot.admin.sysConfig.mapper.SysConfigMapper;
import cn.easy.boot.admin.sysConfig.service.ISysConfigService;
import cn.easy.boot.common.base.BaseEntity;
import cn.easy.boot.utils.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easy.boot.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置 服务实现类
*/
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    public IPage<SysConfig> selectPage(SysConfigQuery query) {
        Page<SysConfig> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery.like(SysConfig::getCode, query.getKeyword()).or()
                            .like(SysConfig::getName, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getCode()), SysConfig::getCode, query.getCode())
                .like(StrUtil.isNotEmpty(query.getName()), SysConfig::getName, query.getName())
                .eq(SysConfig::getDomainId, query.getDomainId())
                .eq(Objects.nonNull(query.getStatus()), SysConfig::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(SysConfig::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public SysConfig detail(Long id) {
        return getById(id);
    }

    @Override
    public Boolean create(SysConfigCreateDTO dto) {
        SysConfig sysConfig = this.getByDomainIdAndCode(dto.getDomainId(), dto.getCode());
        if (sysConfig != null) {
            throw new BusinessException("配置参数键已存在");
        }
        SysConfig entity = BeanUtil.copyBean(dto, SysConfig.class);
        return save(entity);
    }

    @Override
    public Boolean updateById(SysConfigUpdateDTO dto) {
        if (StrUtil.isNotEmpty(dto.getCode())) {
            SysConfig sysConfig = this.getById(dto.getId());
            sysConfig =  this.getByDomainIdAndCode(sysConfig.getDomainId(), dto.getCode());
            if (sysConfig != null && !sysConfig.getId().equals(dto.getId())) {
                throw new BusinessException("配置参数键已存在");
            }
        }
        SysConfig entity = BeanUtil.copyBean(dto, SysConfig.class);
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

    @Override
    public Boolean deleteByDomainId(Long domainId) {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("domain_id", domainId);
        return remove(queryWrapper);
    }

    @Override
    public SysConfig getByDomainIdAndCode(Long domainId, String code) {
        return lambdaQuery().eq(SysConfig::getDomainId, domainId)
                .eq(SysConfig::getCode, code)
                .one();
    }

    @Override
    public SysConfig getNotDisabledByDomainIdAndCode(Long domainId, String code) {
        return lambdaQuery().eq(SysConfig::getDomainId, domainId)
                .eq(SysConfig::getCode, code)
                .eq(SysConfig::getStatus, 1)
                .one();
    }

    @Override
    public List<SysConfig> selectByDomainIdAndStatus(Long domainId, Integer status) {
        return lambdaQuery()
                .select(BaseEntity::getId, SysConfig::getCode, SysConfig::getName, SysConfig::getValue)
                .eq(status != null, SysConfig::getStatus, status)
                .eq(SysConfig::getDomainId, domainId)
                .orderByAsc(SysConfig::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public List<SysConfig> selectByDomainId(Long domainId) {
        return this.selectByDomainIdAndStatus(domainId, null);
    }

    @Override
    public List<SysConfig> selectNotDisabledListByDomainId(Long domainId) {
        return this.selectByDomainIdAndStatus(domainId, 1);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean templateBatchSave(List<SysConfigCreateDTO> dtos) {
        if (CollUtil.isEmpty(dtos)) {
            return false;
        }
        List<SysConfig> sysConfigs = BeanUtil.copyList(dtos, SysConfig.class);
        Long domainId = sysConfigs.get(0).getDomainId();
        deleteByDomainId(domainId);
        return saveBatch(sysConfigs);
    }

}
