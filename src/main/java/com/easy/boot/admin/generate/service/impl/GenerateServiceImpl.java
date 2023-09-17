package com.easy.boot.admin.generate.service.impl;

import cn.hutool.core.text.NamingCase;
import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.entity.GenerateTableQuery;
import com.easy.boot.admin.generate.entity.GenerateUpdateDTO;
import com.easy.boot.admin.generate.mapper.GenerateMapper;
import com.easy.boot.admin.generate.service.GenerateService;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnQuery;
import com.easy.boot.admin.generateColumn.service.IGenerateColumnService;
import com.easy.boot.admin.generateConfig.entity.GenerateConfig;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigQuery;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigVO;
import com.easy.boot.admin.generateConfig.service.IGenerateConfigService;
import com.easy.boot.common.base.Page;
import com.easy.boot.common.generator.GenConstant;
import com.easy.boot.common.generator.db.DbManager;
import com.easy.boot.common.generator.db.MetaTable;
import com.easy.boot.common.generator.execute.GeneratorExecute;
import com.easy.boot.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchByTableNames(List<String> tableNames) {
        generateConfigService.deleteBatchByTableNames(tableNames);
        generateColumnService.deleteBatchByTableNames(tableNames);
    }

    @Override
    public void batchGenerateCode(List<String> tableNames, HttpServletResponse response) throws IOException {
        for (String tableName : tableNames) {
            GenerateConfigQuery query = new GenerateConfigQuery(tableName);
            GenerateConfigVO vo = generateConfigService.getTableConfig(query);
            GenerateConfig generateConfig = BeanUtil.copyBean(vo, GenerateConfig.class);
            GenerateColumnQuery columnQuery = new GenerateColumnQuery(tableName);
            List<GenerateColumn> columns = generateColumnService.selectList(columnQuery);
            String filterName = DbManager.filterTableName(generateConfig.getTableName(),
                    generateConfig.getExcludeTablePrefix(), generateConfig.getExcludeTableSuffix());
            MetaTable metaTable = MetaTable.builder()
                    .name(generateConfig.getTableName())
                    .beanName(NamingCase.toPascalCase(filterName))
                    .camelName(NamingCase.toCamelCase(filterName))
                    .moduleName(generateConfig.getModuleName())
                    .remarks(generateConfig.getRemarks())
                    .columns(columns)
                    .build();
            GeneratorExecute.init(generateConfig)
                    .metaTable(metaTable)
                    .response(response)
                    .execute();
        }

    }
}
