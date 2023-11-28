package com.easy.boot.admin.sysConfigDomain.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.sysConfig.entity.SysConfig;
import com.easy.boot.admin.sysConfig.entity.SysTemplateConfigVO;
import com.easy.boot.admin.sysConfig.service.ISysConfigService;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomain;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainCreateDTO;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainQuery;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomainUpdateDTO;
import com.easy.boot.admin.sysConfigDomain.enums.SysConfigDomainCodeEnum;
import com.easy.boot.admin.sysConfigDomain.mapper.SysConfigDomainMapper;
import com.easy.boot.admin.sysConfigDomain.service.ISysConfigDomainService;
import com.easy.boot.admin.templateConfig.entity.TemplateConfig;
import com.easy.boot.admin.templateConfig.service.ITemplateConfigService;
import com.easy.boot.admin.templateParamConfig.entity.TemplateParamConfig;
import com.easy.boot.admin.templateParamConfig.service.ITemplateParamConfigService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Resource
    private ITemplateConfigService templateConfigService;

    @Resource
    private ITemplateParamConfigService templateParamConfigService;

    @Override
    public IPage<SysConfigDomain> selectPage(SysConfigDomainQuery query) {
        Page<SysConfigDomain> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery.like(SysConfigDomain::getCode, query.getKeyword()).or()
                            .like(SysConfigDomain::getName, query.getKeyword());
                })
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
    public SysConfigDomain getNotDisabledByCode(String code) {
        return lambdaQuery()
                .eq(SysConfigDomain::getCode, code)
                .eq(SysConfigDomain::getStatus, 1)
                .one();
    }

    @Override
    public List<SysConfig> selectListByDomainCode(String code) {
        SysConfigDomain domain = this.getNotDisabledByCode(code);
        if (domain == null) {
            return new ArrayList<>();
        }
        return sysConfigService.selectNotDisabledListByDomainId(domain.getId());
    }

    @Override
    public Boolean create(SysConfigDomainCreateDTO dto) {
        SysConfigDomain sysConfigDomain = this.getNotDisabledByCode(dto.getCode());
        if (sysConfigDomain != null && dto.getStatus() != 2) {
            throw new BusinessException("系统配置域编码已存在");
        }
        validateSysConfigDomain(dto);
        SysConfigDomain entity = BeanUtil.copyBean(dto, SysConfigDomain.class);
        return save(entity);
    }

    /**
     * 校验系统参数配置域
     * @param dto
     */
    private void validateSysConfigDomain(SysConfigDomainCreateDTO dto) {
        if (dto.getType() == 1) {
            if (StrUtil.isEmpty(dto.getPath())) {
                throw new BusinessException("跳转组件路径不能为空");
            }
        } else {
            if (dto.getTemplateId() == null) {
                throw new BusinessException("关联模板ID不能为空");
            }
        }
    }

    @Override
    public Boolean updateById(SysConfigDomainUpdateDTO dto) {
        SysConfigDomain sysConfigDomain = this.getNotDisabledByCode(dto.getCode());
        if (sysConfigDomain != null && !sysConfigDomain.getId().equals(dto.getId()) && dto.getStatus() != 2) {
            throw new BusinessException("系统配置域编码已存在");
        }
        if (SysConfigDomainCodeEnum.GLOBAL.getCode().equals(dto.getCode()) && dto.getStatus() == 2) {
            throw new BusinessException("当前系统配置域不允许禁用");
        }
        validateSysConfigDomain(dto);
        SysConfigDomain entity = BeanUtil.copyBean(dto, SysConfigDomain.class);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        SysConfigDomain domain = detail(id);
        if (domain == null) {
            throw new BusinessException("当前删除的系统配置域不存在");
        }
        if (SysConfigDomainCodeEnum.GLOBAL.getCode().equals(domain.getCode())) {
            throw new BusinessException("当前系统配置域不允许删除");
        }
        List<SysConfig> list = sysConfigService.selectByDomainId(id);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("存在下级系统配置，不允许删除");
        }
        return removeById(id);
    }

    @Override
    public SysConfig getNotDisabledByDomainCodeAndConfigCode(String domainCode, String code) {
        SysConfigDomain configDomain = this.getNotDisabledByCode(domainCode);
        if (configDomain == null) {
            return null;
        }
        return sysConfigService.getNotDisabledByDomainIdAndCode(configDomain.getId(), code);
    }

    @Override
    public List<SysConfig> selectGlobalAll() {
        return selectListByDomainCode(SysConfigDomainCodeEnum.GLOBAL.getCode());
    }

    @Override
    public List<SysTemplateConfigVO> selectTemplateList(Long domainId) {
        List<SysTemplateConfigVO> voList = new ArrayList<>();
        SysConfigDomain sysConfigDomain = detail(domainId);
        TemplateConfig templateConfig = templateConfigService.getNotDisabledById(sysConfigDomain.getTemplateId());
        List<TemplateParamConfig> paramConfigs = new ArrayList<>();
        if (templateConfig != null) {
            paramConfigs = templateParamConfigService.selectNotDisabledListByTemplateId(sysConfigDomain.getTemplateId());
        }
        List<SysConfig> sysConfigs = sysConfigService.selectNotDisabledListByDomainId(domainId);
        if (CollUtil.isEmpty(sysConfigs)) {
            for (TemplateParamConfig paramConfig : paramConfigs) {
                SysTemplateConfigVO vo = new SysTemplateConfigVO();
                vo.setDomainId(domainId)
                        .setCode(paramConfig.getCode())
                        .setName(paramConfig.getName())
                        .setValue(paramConfig.getDefaultValue())
                        .setRequired(paramConfig.getRequired())
                        .setMessage(paramConfig.getMessage())
                        .setPlaceholder(paramConfig.getPlaceholder());
                voList.add(vo);
            }
        } else {
            Map<String, TemplateParamConfig> templateParamConfigMap = paramConfigs.stream()
                    .collect(Collectors.toMap(TemplateParamConfig::getCode, x -> x));
            // 主要以已经存在的配置为主
            for (SysConfig sysConfig : sysConfigs) {
                TemplateParamConfig paramConfig = templateParamConfigMap.get(sysConfig.getCode());
                SysTemplateConfigVO vo = new SysTemplateConfigVO();
                vo.setDomainId(domainId)
                        .setCode(sysConfig.getCode())
                        .setName(sysConfig.getName())
                        .setValue(sysConfig.getValue())
                        .setRequired(paramConfig != null ? paramConfig.getRequired() : 2)
                        .setMessage(paramConfig != null ? paramConfig.getMessage() : null)
                        .setPlaceholder(paramConfig != null ? paramConfig.getPlaceholder() : null);
                voList.add(vo);
            }
        }
        return voList;
    }

}
