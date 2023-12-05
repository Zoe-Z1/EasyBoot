package cn.easy.boot3.common.generator.db;

import cn.easy.boot3.common.generator.DataMap;
import cn.easy.boot3.common.generator.GenConstant;
import cn.easy.boot3.common.generator.GenerateUtil;
import cn.easy.boot3.common.generator.OptElementEnum;
import cn.easy.boot3.common.generator.config.DataSourceConfig;
import cn.easy.boot3.common.generator.config.FilterConfig;
import cn.easy.boot3.common.generator.db.convert.ColumnConvertHandler;
import cn.easy.boot3.common.generator.db.convert.JavaTypeEnum;
import cn.easy.boot3.common.generator.db.convert.OptElementConvertHandler;
import cn.easy.boot3.exception.GeneratorException;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import cn.easy.boot3.admin.generateColumn.entity.GenerateColumn;
import cn.easy.boot3.admin.generateColumn.entity.GenerateColumnQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.sql.DataSource;
import java.io.*;
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
    private Connection connection;

    /**
     * 过滤配置
     */
    private FilterConfig filter;

    /**
     * 数据库字段转换处理器
     */
    private ColumnConvertHandler columnConvertHandler;

    /**
     * 字段类型转换处理器
     */
    private OptElementConvertHandler optElementConvertHandler;

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
            GenerateColumnQuery query = new GenerateColumnQuery(metaTable.getName());
            List<GenerateColumn> columns = getGenerateColumns(query);
            metaTable.setColumns(columns);
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
                boolean isCreate = GenerateUtil.isParseDomainCode(remarks);
                String domainCode = isCreate ? query.getTableName() + "_" + columnName : null;
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
                        .isForm(isPrimaryKey ? 1:0)
                        .isKeyword(isPrimaryKey ? 1:0)
                        .isAdvancedSearch(isPrimaryKey ? 1:0)
                        .listShow(isPrimaryKey ? 1:0)
                        .detailShow(isPrimaryKey ? 1:0)
                        .isExcel(isPrimaryKey ? 1:0)
                        .isRequired(isPrimaryKey ? 1:0)
                        .optElement(optElement.getValue())
                        .dictDomainCode(domainCode)
                        .isCreate(isCreate)
                        .build();
                columns.add(column);
            }
        } catch (Exception e) {
            log.error("获取 {} 表列信息异常 e-> ", query.getTableName(), e);
            throw new GeneratorException("获取" + query.getTableName() + "表列信息异常");
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }

    /**
     * 表名过滤
     * @param tableName 表名
     * @return
     */
    public static String filterTableName(String tableName,  Set<String> excludeTablePrefixSet, Set<String> excludeTableSuffixSet) {
        if (CollUtil.isNotEmpty(excludeTablePrefixSet)) {
            for (String tablePrefix : excludeTablePrefixSet) {
                if (tableName.startsWith(tablePrefix)) {
                    tableName = tableName.replace(tablePrefix, "");
                    break;
                }
            }
        }
        if (CollUtil.isNotEmpty(excludeTableSuffixSet)) {
            for (String tableSuffix : excludeTableSuffixSet) {
                if (tableName.endsWith(tableSuffix)) {
                    tableName = tableName.replace(tableSuffix, "");
                    break;
                }
            }
        }
        if (StrUtil.isEmpty(tableName)) {
            throw new GeneratorException(tableName + " 表过滤后的表名称为空");
        }
        return tableName;
    }

    /**
     * 执行sql
     * @param buildDataMap
     * @return
     * @throws FileNotFoundException
     */
    public boolean runSql(DataMap buildDataMap) throws Exception {
        if (connection == null) {
            throw new GeneratorException("数据库连接不存在，无法执行SQL");
        }
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        String filename = buildDataMap.getString(GenConstant.DATA_MAP_KEY_FILE_NAME);
        File file = new File(buildDataMap.getString(GenConstant.DATA_MAP_KEY_GEN_PATH), filename);
        // 读取文件，返回Reader对象
        Reader reader = new FileReader(file);
        // 执行失败停止执行并抛出异常回滚
        scriptRunner.setStopOnError(true);
        // 执行SQL脚本
        boolean status = true;
        try {
            scriptRunner.runScript(reader);
        } catch (Exception e) {
            status = false;
        }
        log.info("执行SQL文件 {} " + (status ? "成功" : "失败"), filename);
        connection.close();
        return status;
    }

    /**
     * 执行sql
     * @param connection
     * @param sql
     * @return
     * @throws FileNotFoundException
     */
    public static boolean runSql(Connection connection, String sql) {
        if (connection == null) {
            throw new GeneratorException("数据库连接不存在，无法执行SQL");
        }
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        // 读取文件，返回Reader对象
        Reader reader = new StringReader(sql);
        // 执行失败停止执行并抛出异常回滚
        scriptRunner.setStopOnError(true);
        // 执行SQL脚本
        boolean status = true;
        try {
            scriptRunner.runScript(reader);
        } catch (Exception e) {
            status = false;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
