package com.easy.boot.common.generator.db;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.easy.boot.common.generator.config.DataSourceConfig;
import com.easy.boot.common.generator.db.convert.ColumnConvertHandler;
import com.easy.boot.common.generator.db.convert.JavaTypeEnum;
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
     * 数据库字段转换处理器
     */
    private final ColumnConvertHandler columnConvertHandler;

    private DbManager(Connection connection, ColumnConvertHandler columnConvertHandler) {
        this.columnConvertHandler = columnConvertHandler;
        this.connection = connection;
    }

    /**
     * 初始化
     * @param dataSourceConfig 数据源配置
     * @return DbManager
     */
    public static DbManager init(DataSourceConfig dataSourceConfig) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
        } catch (SQLException e) {
            log.error("获取连接信息异常 e -> ", e);
            throw new GeneratorException("获取连接信息异常");
        }
        return new DbManager(connection, dataSourceConfig.getColumnConvertHandler());
    }

    /**
     * 初始化
     * @param dataSource 数据源
     * @param columnConvertHandler 数据库类型转换处理器
     * @return DbManager
     */
    public static DbManager init(DataSource dataSource, ColumnConvertHandler columnConvertHandler) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            log.error("获取连接信息异常 e -> ", e);
            throw new GeneratorException("获取连接信息异常");
        }
        return new DbManager(connection, columnConvertHandler);
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
        distinctTables(list);
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
                String remarks = StrUtil.isEmpty(table.getRemarks()) ? rs.getString(DbConstant.TABLE_REMARKS) : table.getRemarks();
                MetaTable metaTable = MetaTable.builder()
                        .name(name)
                        .beanName(NamingCase.toPascalCase(name))
                        .camelName(NamingCase.toCamelCase(name))
                        .moduleName(table.getModuleName())
                        .type(rs.getString(DbConstant.TABLE_TYPE))
                        .remarks(remarks)
                        .build();
                if (StrUtil.isEmpty(table.getModuleName())) {
                    metaTable.setModuleName(NamingCase.toCamelCase(name));
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
     * 根据表名进行表数据去重
     * @param tables 表数据集合
     * @return List<MetaTable>
     */
    private void distinctTables(List<MetaTable> tables) {
        tables.stream()
                .collect(
                        Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(MetaTable::getName))),
                                ArrayList::new)
                );
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
                        .remarks(rs.getString(DbConstant.COLUMN_REMARKS))
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
