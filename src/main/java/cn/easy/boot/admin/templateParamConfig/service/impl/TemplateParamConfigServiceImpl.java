package cn.easy.boot.admin.templateParamConfig.service.impl;

import cn.easy.boot.admin.templateParamConfig.entity.TemplateParamConfig;
import cn.easy.boot.admin.templateParamConfig.entity.TemplateParamConfigCreateDTO;
import cn.easy.boot.admin.templateParamConfig.entity.TemplateParamConfigQuery;
import cn.easy.boot.admin.templateParamConfig.entity.TemplateParamConfigUpdateDTO;
import cn.easy.boot.admin.templateParamConfig.mapper.TemplateParamConfigMapper;
import cn.easy.boot.admin.templateParamConfig.service.ITemplateParamConfigService;
import cn.easy.boot.exception.BusinessException;
import cn.easy.boot.utils.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zoe
* @date 2023/11/09
* @description 模板参数配置 服务实现类
*/
@Service
public class TemplateParamConfigServiceImpl extends ServiceImpl<TemplateParamConfigMapper, TemplateParamConfig> implements ITemplateParamConfigService {

    @Override
    public IPage<TemplateParamConfig> selectPage(TemplateParamConfigQuery query) {
        Page<TemplateParamConfig> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                    .like(TemplateParamConfig::getCode, query.getKeyword()).or()
                    .like(TemplateParamConfig::getName, query.getKeyword());
                })
                .eq(TemplateParamConfig::getTemplateId, query.getTemplateId())
                .like(StrUtil.isNotEmpty(query.getCode()), TemplateParamConfig::getCode, query.getCode())
                .like(StrUtil.isNotEmpty(query.getName()), TemplateParamConfig::getName, query.getName())
                .eq(query.getRequired() != null, TemplateParamConfig::getRequired, query.getRequired())
                .eq(query.getStatus() != null, TemplateParamConfig::getStatus, query.getStatus())
                .orderByAsc(TemplateParamConfig::getSort)
                .orderByDesc(TemplateParamConfig::getCreateTime)
                .page(page);
    }

    @Override
    public Boolean create(TemplateParamConfigCreateDTO dto) {
        TemplateParamConfig templateParamConfig = this.getByTemplateIdAndCode(dto.getTemplateId() ,dto.getCode());
        if (templateParamConfig != null) {
            throw new BusinessException("模板参数键已存在");
        }
        templateParamConfig = BeanUtil.copyBean(dto, TemplateParamConfig.class);
        return save(templateParamConfig);
    }

    @Override
    public Boolean updateById(TemplateParamConfigUpdateDTO dto) {
        TemplateParamConfig templateParamConfig = this.getByTemplateIdAndCode(dto.getTemplateId(), dto.getCode());
        if (templateParamConfig != null && !templateParamConfig.getId().equals(dto.getId())) {
            throw new BusinessException("模板参数键已存在");
        }
        templateParamConfig = BeanUtil.copyBean(dto, TemplateParamConfig.class);
        return updateById(templateParamConfig);
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
    public TemplateParamConfig getByTemplateIdAndCode(Long templateId, String code) {
        return lambdaQuery()
                .eq(TemplateParamConfig::getTemplateId, templateId)
                .eq(TemplateParamConfig::getCode, code)
                .one();
    }

    @Override
    public List<TemplateParamConfig> selectNotDisabledListByTemplateId(Long templateId) {
        return lambdaQuery()
                .eq(TemplateParamConfig::getTemplateId, templateId)
                .eq(TemplateParamConfig::getStatus, 1)
                .orderByAsc(TemplateParamConfig::getSort)
                .orderByDesc(TemplateParamConfig::getCreateTime)
                .list();
    }


}
