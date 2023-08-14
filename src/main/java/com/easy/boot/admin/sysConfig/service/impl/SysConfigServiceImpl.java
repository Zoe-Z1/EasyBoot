package com.easy.boot.admin.sysConfig.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.sysConfig.entity.SysConfig;
import com.easy.boot.admin.sysConfig.entity.SysConfigCreateDTO;
import com.easy.boot.admin.sysConfig.entity.SysConfigQuery;
import com.easy.boot.admin.sysConfig.entity.SysConfigUpdateDTO;
import com.easy.boot.admin.sysConfig.mapper.SysConfigMapper;
import com.easy.boot.admin.sysConfig.service.ISysConfigService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.ImportExcelError;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        return lambdaQuery().like(StrUtil.isNotEmpty(query.getCode()), SysConfig::getCode, query.getCode())
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
            throw new BusinessException("系统配置键已存在");
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
                throw new BusinessException("系统配置键已存在");
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
    public List<SysConfig> getByDomainId(Long domainId) {
        return lambdaQuery()
                .select(BaseEntity::getId, SysConfig::getCode, SysConfig::getValue)
                .eq(SysConfig::getStatus, 1)
                .eq(SysConfig::getDomainId, domainId)
                .list();
    }

    @Override
    public void importExcel(List<SysConfig> list, List<SysConfig> errorList, List<ImportExcelError> errors) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<String> codes = list.stream().map(SysConfig::getCode).distinct().collect(Collectors.toList());
        List<SysConfig> sysConfigs = lambdaQuery().in(SysConfig::getCode, codes).
                eq(SysConfig::getDomainId, list.get(0).getDomainId())
                .list();
        codes = sysConfigs.stream().map(SysConfig::getCode).distinct().collect(Collectors.toList());
        // 去除表头 行数从1起算
        int rowIndex = 1;
        Iterator<SysConfig> iterator = list.iterator();
        while (iterator.hasNext()) {
            SysConfig sysConfig = iterator.next();
            boolean isError = false;
            ImportExcelError.ImportExcelErrorBuilder builder = ImportExcelError.builder();
            if (StrUtil.isEmpty(sysConfig.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("系统配置编码不能为空");
                errors.add(builder.build());
            } else if (sysConfig.getCode().length() < 1 || sysConfig.getCode().length() > 50) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("系统配置编码在1-50个字符之间");
                errors.add(builder.build());
            }
            if (codes.contains(sysConfig.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("系统配置编码已存在");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(sysConfig.getValue())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("系统配置值不能为空");
                errors.add(builder.build());
            } else if (sysConfig.getValue().length() < 1 || sysConfig.getValue().length() > 100) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("系统配置值在1-100个字符之间");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(sysConfig.getName())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("系统配置名称不能为空");
                errors.add(builder.build());
            } else if (sysConfig.getName().length() < 1 || sysConfig.getName().length() > 20) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("系统配置名称在1-20个字符之间");
                errors.add(builder.build());
            }
            if (sysConfig.getStatus() == null) {
                isError = true;
                builder.columnIndex(2).rowIndex(rowIndex).msg("系统配置状态不能为空");
                errors.add(builder.build());
            }
            // 这一行有错误，行数增加，错误数据加到list，删除原list的数据
            if (isError) {
                rowIndex++;
                errorList.add(sysConfig);
                iterator.remove();
            } else {
                // 没有错误，会进行新增，加进去不存在的，防止后续存在
                codes.add(sysConfig.getCode());
            }
        }
        saveBatch(list);
    }

}
