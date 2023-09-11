package com.easy.boot.admin.generateConfig.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.service.GenerateService;
import com.easy.boot.admin.generateConfig.entity.GenerateConfig;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigCreateDTO;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigUpdateDTO;
import com.easy.boot.admin.generateConfig.entity.TableConfigQuery;
import com.easy.boot.admin.generateConfig.mapper.GenerateConfigMapper;
import com.easy.boot.admin.generateConfig.service.IGenerateConfigService;
import com.easy.boot.common.redisson.EasyLock;
import com.easy.boot.exception.GeneratorException;
import com.easy.boot.utils.BeanUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zoe
* @date 2023/09/10
* @description 代码生成参数配置 服务实现类
*/
@Service
public class GenerateConfigServiceImpl extends ServiceImpl<GenerateConfigMapper, GenerateConfig> implements IGenerateConfigService {


    @Resource
    private GenerateService generateService;

    @EasyLock
    @Override
    public GenerateConfig getGlobalConfig() {
        GenerateConfig generateConfig = lambdaQuery()
                .eq(GenerateConfig::getType, 1)
                .one();
        if (generateConfig == null) {
            generateConfig = GenerateConfig.defaultGlobalBuild();
            save(generateConfig);
        }
        return generateConfig;
    }

    @EasyLock
    @Override
    public GenerateConfig getTableConfig(TableConfigQuery query) {
        DatabaseTable tableByTableName = generateService.getTableByTableName(query.getTableName());
        if (tableByTableName == null) {
            throw new GeneratorException("当前表配置信息不存在");
        }
        GenerateConfig generateConfig = lambdaQuery()
                .eq(GenerateConfig::getType, 2)
                .eq(GenerateConfig::getTableName, query.getTableName())
                .one();
        if (generateConfig == null) {
            generateConfig = getGlobalConfig();
            generateConfig.setType(2)
                    .setTableName(tableByTableName.getTableName())
                    .setComment(tableByTableName.getComment());
            generateConfig.setId(null);
            generateConfig.setCreateBy(null);
            generateConfig.setCreateUsername(null);
            generateConfig.setCreateTime(null);
            generateConfig.setUpdateBy(null);
            generateConfig.setUpdateUsername(null);
            generateConfig.setUpdateTime(null);
            save(generateConfig);
        }
        return generateConfig;
    }

    @Override
    public Boolean create(GenerateConfigCreateDTO dto) {
        GenerateConfig generateConfig = BeanUtil.copyBean(dto, GenerateConfig.class);
        return save(generateConfig);
    }

    @Override
    public Boolean updateById(GenerateConfigUpdateDTO dto) {
        GenerateConfig generateConfig = BeanUtil.copyBean(dto, GenerateConfig.class);
        return updateById(generateConfig);
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
