package com.easy.boot3.admin.generate.service;

import com.easy.boot3.admin.generate.entity.DatabaseTable;
import com.easy.boot3.admin.generate.entity.GenerateCode;
import com.easy.boot3.admin.generate.entity.GenerateTableQuery;
import com.easy.boot3.common.base.Page;

import java.util.List;

/**
 * @author zoe
 * @date 2023/9/7
 * @description 代码生成 服务类
 */
public interface GenerateService {

    /**
     * 获取数据库Table列表
     * @param query
     * @return
     */
    Page<DatabaseTable> selectPage(GenerateTableQuery query);

    /**
     * 根据表名获取数据库Table信息
     * @param tableName
     * @return
     */
    DatabaseTable getTableByTableName(String tableName);

    /**
     * 批量删除代码生成配置
     * @param tableNames
     * @return
     */
    void deleteBatchByTableNames(List<String> tableNames);

    /**
     * 预览代码
     * @param tableName
     * @return
     * @throws Exception
     */
    List<GenerateCode> preview(String tableName) throws Exception;

    /**
     * 执行sql
     * @param tableNames
     * @param codes
     */
    void runSql(List<String> tableNames, List<GenerateCode> codes);
}
