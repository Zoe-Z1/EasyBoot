package com.easy.boot.admin.generate.service.impl;

import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.entity.GenerateTableQuery;
import com.easy.boot.admin.generate.mapper.GenerateMapper;
import com.easy.boot.admin.generate.service.GenerateService;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnQuery;
import com.easy.boot.admin.generateColumn.service.IGenerateColumnService;
import com.easy.boot.admin.generateConfig.entity.GenerateConfig;
import com.easy.boot.admin.generateConfig.entity.TableConfigQuery;
import com.easy.boot.admin.generateConfig.service.IGenerateConfigService;
import com.easy.boot.common.base.Page;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.execute.GeneratorExecute;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zoe
 * @date 2023/9/7
 * @description 代码生成 服务实现类
 */
@Service
public class GenerateServiceImpl implements GenerateService {


    @Resource
    private GenerateMapper generateMapper;

    @Value("${spring.datasource.url}")
    private String url;

    @Resource
    private IGenerateConfigService generateConfigService;

    @Resource
    private IGenerateColumnService generateColumnService;


    @Override
    public Page<DatabaseTable> selectPage(GenerateTableQuery query) {
        Page<DatabaseTable> page = new Page<>();
        String[] splits = url.split("/");
        String endStr = splits[splits.length - 1];
        String dbName = endStr.substring(0, endStr.indexOf("?"));
        query.setDbName(dbName)
                .setTableType(GenConstant.TABLE_TYPE_BASE_TABLE);
        List<DatabaseTable> list = generateMapper.selectPage(query);
        Long count = generateMapper.selectCount(query);

        page.setCurrent(query.getPageNum())
                .setSize(query.getPageSize())
                .setTotal(count)
                .setRecords(list);
        return page;
    }

    @Override
    public DatabaseTable getTableByTableName(String tableName) {
        String[] splits = url.split("/");
        String endStr = splits[splits.length - 1];
        String dbName = endStr.substring(0, endStr.indexOf("?"));
        GenerateTableQuery query = GenerateTableQuery.builder()
                .tableName(tableName)
                .dbName(dbName)
                .tableType(GenConstant.TABLE_TYPE_BASE_TABLE)
                .build();
        return generateMapper.getTableByTableName(query);
    }

    @Override
    public void generateCode(String tableName) {
        TableConfigQuery query = new TableConfigQuery(tableName);
        GenerateConfig generateConfig = generateConfigService.getTableConfig(query);
        GenerateColumnQuery columnQuery = new GenerateColumnQuery(tableName);
        List<GenerateColumn> columns = generateColumnService.selectList(columnQuery);
        GeneratorExecute.init(generateConfig).columns(columns).execute();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteBatchByTableNames(List<String> tableNames) {
        generateConfigService.deleteBatchByTableNames(tableNames);
        generateColumnService.deleteBatchByTableNames(tableNames);
        return null;
    }
}
