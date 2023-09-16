package com.easy.boot.common.generator.db;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnQuery;
import com.easy.boot.common.generator.OptElementEnum;
import com.easy.boot.common.generator.config.DataSourceConfig;
import com.easy.boot.common.generator.config.FilterConfig;
import com.easy.boot.common.generator.db.convert.ColumnConvertHandler;
import com.easy.boot.common.generator.db.convert.JavaTypeEnum;
import com.easy.boot.common.generator.db.convert.OptElementConvertHandler;
import com.easy.boot.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/8/19
 * @description 数据库处理
 */
@Slf4j
public class DbManager {

    /**
     * 数据库连接
     */
    private final Connection connection;

    /**
     * 过滤配置
     */
    private final FilterConfig filter;

    /**
     * 数据库字段转换处理器
     */
    private final ColumnConvertHandler columnConvertHandler;

    private final OptElementConvertHandler optElementConvertHandler;

    private DbManager(Connection connection, FilterConfig filterConfig, ColumnConvertHandler columnConvertHandler) {
        this.optElementConvertHandler = OptElementConvertHandler.defaultHandler();
        this.columnConvertHandler = columnConvertHandler;
        this.connection = connection;
        this.filter = filterConfig;
    }

    /**
     * 初始化
     * @param dataSourceConfig 数据源配置
     * @param filterConfig 过滤配置
     * @return DbManager
     */
    public static DbManager init(DataSourceConfig dataSourceConfig, FilterConfig filterConfig) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
        } catch (SQLException e) {
            log.error("获取连接信息异常 e -> ", e);
            throw new GeneratorException("获取连接信息异常");
        }
        return new DbManager(connection, filterConfig, dataSourceConfig.getColumnConvertHandler());
    }

    /**
     * 初始化
     * @param dataSource 数据源
     * @param columnConvertHandler 数据库类型转换处理器
     * @return DbManager
     */
    public static DbManager init(DataSource dataSource, FilterConfig filterConfig, ColumnConvertHandler columnConvertHandler) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            log.error("获取连接信息异常 e -> ", e);
            throw new GeneratorException("获取连接信息异常");
        }
        return new DbManager(connection, filterConfig, columnConvertHandler);
    }


    /**
     * 获取要生成表的列信息
     * @return List<GenerateColumn>
     */
    public List<GenerateColumn> getGenerateColumns(List<GenerateColumnQuery> querys) {
        List<GenerateColumn> list = new ArrayList<>();
        for (GenerateColumnQuery query : querys) {
            List<GenerateColumn> newColumns = getGenerateColumns(query);
            if (CollUtil.isNotEmpty(newColumns)) {
                list.addAll(newColumns);
            }
        }
        return list;
    }


    /**
     * 获取要生成表的列信息
     * @return List<GenerateColumn>
     */
    public List<GenerateColumn> getGenerateColumns(GenerateColumnQuery query) {
        List<GenerateColumn> columns = new ArrayList<>();
        try {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            List<String> primaryKeyNames = new ArrayList<>();
            // 处理主键信息
            ResultSet primaryKeys = dbMetaData.getPrimaryKeys(null, null, query.getTableName());
            while (primaryKeys.next()) {
                primaryKeyNames.add(primaryKeys.getString(DbConstant.COLUMN_NAME));
            }
            // 处理字段信息
            ResultSet rs = dbMetaData.getColumns(connection.getCatalog(), null, query.getTableName(), null);
            while (rs.next()) {
                String columnName = rs.getString(DbConstant.COLUMN_NAME);
                String javaName = NamingCase.toCamelCase(columnName);
                // 字段过滤
                if (filter.getExcludeField().contains(javaName)) {
                    continue;
                }
                String remarks = rs.getString(DbConstant.COLUMN_REMARKS);
                if (StrUtil.isNotEmpty(remarks)) {
                    remarks = remarks.replaceAll("\n", "\t");
                }
                String columnType = rs.getString(DbConstant.COLUMN_TYPE);
                JavaTypeEnum javaType = columnConvertHandler.convert(columnType);
                OptElementEnum optElement = optElementConvertHandler.convert(columnType);
                boolean isPrimaryKey = primaryKeyNames.contains(columnName);
                int columnSize = rs.getInt(DbConstant.COLUMN_SIZE);
                columnType = columnType + "(" + columnSize + ")";
                GenerateColumn column = GenerateColumn.builder()
                        .tableName(query.getTableName())
                        .isPrimaryKey(isPrimaryKey ? 0 : 1)
                        .columnName(columnName)
                        .columnType(columnType)
                        .columnRemarks(remarks)
                        .nullable(rs.getInt(DbConstant.COLUMN_NULLABLE))
                        .javaName(javaName)
                        .javaType(javaType.getValue())
                        .javaTypePackageName(javaType.getPackageName())
                        .isCreate(isPrimaryKey ? 1:0)
                        .isUpdate(0)
                        .listShow(isPrimaryKey ? 1:0)
                        .detailShow(isPrimaryKey ? 1:0)
                        .isImport(isPrimaryKey ? 1:0)
                        .isExport(isPrimaryKey ? 1:0)
                        .isRequired(isPrimaryKey ? 1:0)
                        .optElement(optElement.getValue())
                        .build();
                columns.add(column);
            }
        } catch (Exception e) {
            log.error("获取 {} 表列信息异常 e-> ", query.getTableName(), e);
            throw new GeneratorException("获取表列息异常");
        }
        return columns;
    }

    /**
     * 获取要生成的表信息
     * @return List<MetaTable>
     */
    public List<MetaTable> getTables(List<Table> tables) {
        List<MetaTable> list = new ArrayList<>();
        for (Table table : tables) {
            List<MetaTable> newTables = getTables(table);
            if (CollUtil.isNotEmpty(newTables)) {
                list.addAll(newTables);
            }
        }
        // 去重
        list = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(MetaTable::getName))),
                ArrayList::new)
        );
        // 处理表中的字段信息
        for (MetaTable metaTable : list) {
            handleTableFields(metaTable);
        }
        return list;
    }

    /**
     * 获取表信息
     * @param table 准备生成的表信息
     * @return List<MetaTable>
     */
    private List<MetaTable> getTables(Table table) {
        try {
            // 获取数据库的元数据
            DatabaseMetaData dbMetaData = connection.getMetaData();
            // 从元数据中获取到所有的表名
            ResultSet rs = dbMetaData.getTables(connection.getCatalog(), null, table.getTableName(), new String[]{"TABLE"});
            // 存放所有表信息
            Set<MetaTable> tables = new HashSet<>();
            while (rs.next()) {
                String name = rs.getString(DbConstant.TABLE_NAME);
                String tableRemarks = rs.getString(DbConstant.TABLE_REMARKS);
                if (StrUtil.isNotEmpty(tableRemarks)) {
                    tableRemarks = tableRemarks.replaceAll("\n", "\t");
                }
                String filterName = filterTableName(name);
                String remarks = StrUtil.isEmpty(table.getRemarks()) ? tableRemarks : table.getRemarks();
                MetaTable metaTable = MetaTable.builder()
                        .name(name)
                        .beanName(NamingCase.toPascalCase(filterName))
                        .camelName(NamingCase.toCamelCase(filterName))
                        .moduleName(table.getModuleName())
                        .type(rs.getString(DbConstant.TABLE_TYPE))
                        .remarks(remarks)
                        .build();
                if (StrUtil.isEmpty(table.getModuleName())) {
                    metaTable.setModuleName(NamingCase.toCamelCase(filterName));
                }
                tables.add(metaTable);
            }
            return new ArrayList<>(tables);
        } catch (Exception e) {
            log.error("加载表数据异常 e -> ", e);
            throw new GeneratorException("加载表数据异常");
        }
    }

    /**
     * 表名过滤
     * @param tableName 表名
     * @return
     */
    private String filterTableName(String tableName) {
        for (String tablePrefix : filter.getExcludeTablePrefix()) {
            if (tableName.startsWith(tablePrefix)) {
                tableName = tableName.replace(tablePrefix, "");
                break;
            }
        }
        for (String tableSuffix : filter.getExcludeTableSuffix()) {
            if (tableName.endsWith(tableSuffix)) {
                tableName = tableName.replace(tableSuffix, "");
                break;
            }
        }
        if (StrUtil.isEmpty(tableName)) {
            throw new GeneratorException("过滤后的表名称不能为空");
        }
        return tableName;
    }

    /**
     * 处理表字段信息
     * @param metaTable 表数据
     */
    private void handleTableFields(MetaTable metaTable) {
        List<Field> fields = new ArrayList<>();
        try {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            List<String> primaryKeyNames = new ArrayList<>();
            // 处理主键信息
            ResultSet primaryKeys = dbMetaData.getPrimaryKeys(null, null, metaTable.getName());
            while (primaryKeys.next()) {
                primaryKeyNames.add(primaryKeys.getString(DbConstant.COLUMN_NAME));
            }
            // 处理字段信息
            ResultSet rs = dbMetaData.getColumns(connection.getCatalog(), null, metaTable.getName(), null);
            while (rs.next()) {
                String columnName = rs.getString(DbConstant.COLUMN_NAME);
                String remarks = rs.getString(DbConstant.COLUMN_REMARKS);
                if (StrUtil.isNotEmpty(remarks)) {
                    remarks = remarks.replaceAll("\n", "\t");
                }
                String javaName = NamingCase.toCamelCase(columnName);
                String columnType = rs.getString(DbConstant.COLUMN_TYPE);
                JavaTypeEnum javaType = columnConvertHandler.convert(columnType);
                Field field = Field.builder()
                        .isPrimaryKey(primaryKeyNames.contains(columnName))
                        .name(columnName)
                        .javaName(javaName)
                        .columnType(columnType)
                        .javaType(javaType.getValue())
                        .javaTypePackageName(javaType.getPackageName())
                        .size(rs.getInt(DbConstant.COLUMN_SIZE))
                        .nullable(rs.getInt(DbConstant.COLUMN_NULLABLE))
                        .remarks(remarks)
                        .build();
                fields.add(field);
            }
            metaTable.setFields(fields);
        } catch (Exception e) {
            log.error("加载 {} 表字段信息异常 e-> ", metaTable.getName(), e);
            throw new GeneratorException("加载表字段信息异常");
        }
    }
}
