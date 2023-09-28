package com.easy.boot.admin.generate.service.impl;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.entity.GenerateCode;
import com.easy.boot.admin.generate.entity.GenerateTableQuery;
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
import com.easy.boot.exception.GeneratorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
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

    @Resource
    private DataSource dataSource;

    @Resource
    private IGenerateConfigService generateConfigService;

    @Resource
    private IGenerateColumnService generateColumnService;


    private String getDbName() {
        String dbName = "";
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            dbName = connection.getCatalog();
        } catch (SQLException e) {
            throw new GeneratorException("获取数据库名失败");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbName;
    }


    @Override
    public Page<DatabaseTable> selectPage(GenerateTableQuery query) {
        String dbName = getDbName();
        Page<DatabaseTable> page = new Page<>();
        query.setDbName(dbName)
                .setTableType(GenConstant.TABLE_TYPE_BASE_TABLE);
        Long count = generateMapper.selectCount(query);
        List<DatabaseTable> list = new ArrayList<>();
        if (count >= 0) {
            list = generateMapper.selectPage(query);
        }
        page.setCurrent(query.getPageNum())
                .setSize(query.getPageSize())
                .setTotal(count)
                .setRecords(list);
        return page;
    }

    @Override
    public DatabaseTable getTableByTableName(String tableName) {
        String dbName = getDbName();
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
    public List<GenerateCode> preview(String tableName) throws Exception {
        if (StrUtil.isEmpty(tableName)) {
            throw new GeneratorException("要生成的表名不能为空");
        }
        GenerateConfigQuery query = new GenerateConfigQuery(tableName);
        GenerateConfigVO vo = generateConfigService.getTableConfig(query);
        GenerateConfig generateConfig = GenerateConfigVO.toGenerateConfig(vo);
        GenerateColumnQuery columnQuery = new GenerateColumnQuery(tableName);
        List<GenerateColumn> columns = generateColumnService.selectList(columnQuery);
        String filterName = DbManager.filterTableName(generateConfig.getTableName(),
                vo.getExcludeTablePrefix(), vo.getExcludeTableSuffix());
        MetaTable metaTable = MetaTable.builder()
                .name(generateConfig.getTableName())
                .beanName(NamingCase.toPascalCase(filterName))
                .camelName(NamingCase.toCamelCase(filterName))
                .moduleName(generateConfig.getModuleName())
                .uiModuleName(generateConfig.getUiModuleName())
                .parentMenuId(generateConfig.getParentMenuId())
                .remarks(generateConfig.getTableRemarks())
                .columns(columns)
                .build();
        return GeneratorExecute.init(generateConfig)
                .metaTable(metaTable)
                .preview();
    }

    @Override
    public void runSql(List<String> tableNames, List<GenerateCode> codes) {
        StringBuffer sb = new StringBuffer();
        String sql = "sql";
        codes.forEach(item -> {
            if (item.getExecute() && sql.equals(item.getFilename())) {
                sb.append(item.getFileContent());
            }
        });
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            boolean status = DbManager.runSql(connection, sb.toString());
            if (!status) {
                throw new GeneratorException("执行SQL失败，已中断代码生成");
            }
        } catch (SQLException e) {
            throw new GeneratorException("获取数据库连接失败，已中断代码生成");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
