package cn.easy.boot3.admin.templateConfig.service.impl;

import cn.easy.boot3.common.base.BaseEntity;
import cn.easy.boot3.utils.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easy.boot3.admin.templateConfig.entity.TemplateConfig;
import cn.easy.boot3.admin.templateConfig.entity.TemplateConfigCreateDTO;
import cn.easy.boot3.admin.templateConfig.entity.TemplateConfigQuery;
import cn.easy.boot3.admin.templateConfig.entity.TemplateConfigUpdateDTO;
import cn.easy.boot3.admin.templateConfig.mapper.TemplateConfigMapper;
import cn.easy.boot3.admin.templateConfig.service.ITemplateConfigService;
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

    @Override
    public TemplateConfig getNotDisabledById(Long id) {
        return lambdaQuery()
                .eq(BaseEntity::getId, id)
                .eq(TemplateConfig::getStatus, 1)
                .one();
    }

}
