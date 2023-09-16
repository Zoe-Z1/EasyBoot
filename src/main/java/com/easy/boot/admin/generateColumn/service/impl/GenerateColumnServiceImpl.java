package com.easy.boot.admin.generateColumn.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnCreateDTO;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnQuery;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnUpdateDTO;
import com.easy.boot.admin.generateColumn.mapper.GenerateColumnMapper;
import com.easy.boot.admin.generateColumn.service.IGenerateColumnService;
import com.easy.boot.admin.generateConfig.entity.GenerateConfig;
import com.easy.boot.admin.generateConfig.entity.TableConfigQuery;
import com.easy.boot.admin.generateConfig.service.IGenerateConfigService;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.generator.config.FilterConfig;
import com.easy.boot.common.generator.db.DbManager;
import com.easy.boot.common.generator.db.convert.ColumnConvertHandler;
import com.easy.boot.common.generator.execute.GeneratorExecute;
import com.easy.boot.utils.BeanUtil;
import com.easy.boot.utils.JsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/09/15
* @description 代码生成列配置 服务实现类
*/
@Service
public class GenerateColumnServiceImpl extends ServiceImpl<GenerateColumnMapper, GenerateColumn> implements IGenerateColumnService {

    @Resource
    private IGenerateConfigService generateConfigService;

    @Resource
    private DataSource dataSource;


    @Override
    public List<GenerateColumn> selectList(GenerateColumnQuery query) {
        List<GenerateColumn> list = lambdaQuery()
                .eq(GenerateColumn::getTableName, query.getTableName())
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        if (CollUtil.isEmpty(list)) {
            TableConfigQuery tableConfigQuery = new TableConfigQuery(query.getTableName());
            GenerateConfig tableConfig = generateConfigService.getTableConfig(tableConfigQuery);
            Set<String> tablePrefix = new HashSet<>();
            Set<String> tableSuffix = new HashSet<>();
            if (StrUtil.isNotEmpty(tableConfig.getExcludeTablePrefix())) {
                tablePrefix = Arrays.stream(tableConfig.getExcludeTablePrefix().split(",")).collect(Collectors.toSet());
            }
            if (StrUtil.isNotEmpty(tableConfig.getExcludeTableSuffix())) {
                tableSuffix = Arrays.stream(tableConfig.getExcludeTableSuffix().split(",")).collect(Collectors.toSet());
            }
            Set<String> excludeField = CollUtil.newHashSet("createBy","createUsername","createTime","updateBy","updateUsername","updateTime","isDel");
            FilterConfig filterConfig = FilterConfig.builder()
                    .excludeTablePrefix(tablePrefix)
                    .excludeTableSuffix(tableSuffix)
                    .excludeField(tableSuffix)
                    .excludeField(excludeField)
                    .build();
            list = DbManager.init(dataSource, filterConfig, ColumnConvertHandler.defaultHandler())
                    .getGenerateColumns(query);
            saveBatch(list);
        }
        return list;
    }

    @Override
    public Boolean create(GenerateColumnCreateDTO dto) {
        GenerateColumn generateColumn = BeanUtil.copyBean(dto, GenerateColumn.class);
        return save(generateColumn);
    }

    @Override
    public Boolean updateBatchById(List<GenerateColumnUpdateDTO> dto) {
        List<GenerateColumn> list = JsonUtil.copyList(dto, GenerateColumn.class);
        return updateBatchById(list);
    }

    @Override
    public Boolean deleteTableName(String tableName) {
        QueryWrapper<GenerateColumn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_name", tableName);
        return remove(queryWrapper);
    }

    @Override
    public void generateCode(String tableName) {
        TableConfigQuery query = new TableConfigQuery(tableName);
        GenerateConfig generateConfig = generateConfigService.getTableConfig(query);
        GenerateColumnQuery columnQuery = new GenerateColumnQuery(tableName);
        List<GenerateColumn> columns = this.selectList(columnQuery);
        GeneratorExecute.init(generateConfig).columns(columns).execute();
    }

}
