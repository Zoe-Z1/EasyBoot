package com.easy.boot.admin.sysConfigDomain.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.sysConfig.entity.SysConfig;
import com.easy.boot.admin.sysConfig.service.ISysConfigService;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomain;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainCreateDTO;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainQuery;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainUpdateDTO;
import com.easy.boot.admin.sysConfigDomain.mapper.SysConfigDomainMapper;
import com.easy.boot.admin.sysConfigDomain.service.ISysConfigDomainService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.ImportExcelError;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置域 服务实现类
*/
@Service
public class SysConfigDomainServiceImpl extends ServiceImpl<SysConfigDomainMapper, SysConfigDomain> implements ISysConfigDomainService {

    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public IPage<SysConfigDomain> selectPage(SysConfigDomainQuery query) {
        Page<SysConfigDomain> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .like(StrUtil.isNotEmpty(query.getCode()), SysConfigDomain::getCode, query.getCode())
                .like(StrUtil.isNotEmpty(query.getName()), SysConfigDomain::getName, query.getName())
                .eq(Objects.nonNull(query.getStatus()), SysConfigDomain::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(SysConfigDomain::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public SysConfigDomain detail(Long id) {
        return getById(id);
    }

    @Override
    public SysConfigDomain getByCode(String code) {
        return lambdaQuery()
                .eq(SysConfigDomain::getCode, code).one();
    }

    @Override
    public List<SysConfig> selectListByDomainCode(String code) {
        SysConfigDomain domain = this.getByCode(code);
        if (domain == null || domain.getStatus() == 2) {
            return new ArrayList<>();
        }
        return sysConfigService.getByDomainId(domain.getId());
    }

    @Override
    public Boolean create(SysConfigDomainCreateDTO dto) {
        SysConfigDomain sysConfigDomain = this.getByCode(dto.getCode());
        if (sysConfigDomain != null) {
            throw new BusinessException("系统配置域编码已存在");
        }
        SysConfigDomain entity = BeanUtil.copyBean(dto, SysConfigDomain.class);
        return save(entity);
    }

    @Override
    public Boolean updateById(SysConfigDomainUpdateDTO dto) {
        if (StrUtil.isNotEmpty(dto.getCode())) {
            SysConfigDomain sysConfigDomain = this.getByCode(dto.getCode());
            if (sysConfigDomain != null && !sysConfigDomain.getId().equals(dto.getId())) {
                throw new BusinessException("系统配置域编码已存在");
            }
        }
        SysConfigDomain entity = BeanUtil.copyBean(dto, SysConfigDomain.class);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        List<SysConfig> list = sysConfigService.getByDomainId(id);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("存在下级系统配置，不允许删除");
        }
        return removeById(id);
    }

    @Override
    public void importExcel(List<SysConfigDomain> list, List<SysConfigDomain> errorList, List<ImportExcelError> errors) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<String> codes = list.stream().map(SysConfigDomain::getCode).distinct().collect(Collectors.toList());
        List<SysConfigDomain> sysConfigDomains = lambdaQuery().in(SysConfigDomain::getCode, codes).list();
        codes = sysConfigDomains.stream().map(SysConfigDomain::getCode).distinct().collect(Collectors.toList());
        // 去除表头 行数从1起算
        int rowIndex = 1;
        Iterator<SysConfigDomain> iterator = list.iterator();
        while (iterator.hasNext()) {
            SysConfigDomain sysConfigDomain = iterator.next();
            boolean isError = false;
            ImportExcelError.ImportExcelErrorBuilder builder = ImportExcelError.builder();
            if (StrUtil.isEmpty(sysConfigDomain.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("系统配置域编码不能为空");
                errors.add(builder.build());
            } else if (sysConfigDomain.getCode().length() < 1 || sysConfigDomain.getCode().length() > 50) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("系统配置域编码在1-50个字符之间");
                errors.add(builder.build());
            }
            if (codes.contains(sysConfigDomain.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("系统配置域编码已存在");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(sysConfigDomain.getName())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("系统配置域名称不能为空");
                errors.add(builder.build());
            } else if (sysConfigDomain.getName().length() < 1 || sysConfigDomain.getName().length() > 50) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("系统配置域名称在1-50个字符之间");
                errors.add(builder.build());
            }
            if (sysConfigDomain.getStatus() == null) {
                isError = true;
                builder.columnIndex(2).rowIndex(rowIndex).msg("系统配置域状态不能为空");
                errors.add(builder.build());
            }
            // 这一行有错误，行数增加，错误数据加到list，删除原list的数据
            if (isError) {
                rowIndex++;
                errorList.add(sysConfigDomain);
                iterator.remove();
            } else {
                // 没有错误，会进行新增，加进去不存在的，防止后续存在
                codes.add(sysConfigDomain.getCode());
            }
        }
        saveBatch(list);
    }

}
