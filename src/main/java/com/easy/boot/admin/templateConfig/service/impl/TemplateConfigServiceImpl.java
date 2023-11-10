package com.easy.boot.admin.templateConfig.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.templateConfig.entity.TemplateConfig;
import com.easy.boot.admin.templateConfig.entity.TemplateConfigCreateDTO;
import com.easy.boot.admin.templateConfig.entity.TemplateConfigQuery;
import com.easy.boot.admin.templateConfig.entity.TemplateConfigUpdateDTO;
import com.easy.boot.admin.templateConfig.mapper.TemplateConfigMapper;
import com.easy.boot.admin.templateConfig.service.ITemplateConfigService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zoe
* @date 2023/11/09
* @description 模板配置 服务实现类
*/
@Service
public class TemplateConfigServiceImpl extends ServiceImpl<TemplateConfigMapper, TemplateConfig> implements ITemplateConfigService {

    @Override
    public List<TemplateConfig> selectAll() {
        return lambdaQuery()
                .select(BaseEntity::getId, TemplateConfig::getName)
                .eq(TemplateConfig::getStatus, 1)
                .orderByAsc(TemplateConfig::getSort)
                .orderByDesc(TemplateConfig::getCreateTime)
                .list();
    }

    @Override
    public IPage<TemplateConfig> selectPage(TemplateConfigQuery query) {
        Page<TemplateConfig> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                    .like(TemplateConfig::getName, query.getKeyword());
                })
                .eq(query.getStatus() != null, TemplateConfig::getStatus, query.getStatus())
                .orderByAsc(TemplateConfig::getSort)
                .orderByDesc(TemplateConfig::getCreateTime)
                .page(page);
    }

    @Override
    public Boolean create(TemplateConfigCreateDTO dto) {
        TemplateConfig templateConfig = BeanUtil.copyBean(dto, TemplateConfig.class);
        return save(templateConfig);
    }

    @Override

    public Boolean updateById(TemplateConfigUpdateDTO dto) {
        TemplateConfig templateConfig = BeanUtil.copyBean(dto, TemplateConfig.class);
        return updateById(templateConfig);
    }

    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

}
