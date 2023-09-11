package com.easy.boot.admin.generate.service;

import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.entity.GenerateTableColumn;
import com.easy.boot.admin.generate.entity.GenerateTableQuery;
import com.easy.boot.common.base.Page;

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
     * 获取代码生成Table列
     * @param tableName
     * @return
     */
    List<GenerateTableColumn> selectTableColumnList(String tableName);
}
