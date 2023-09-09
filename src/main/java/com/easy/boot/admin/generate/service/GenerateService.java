package com.easy.boot.admin.generate.service;

import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.entity.GenerateConfig;
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
     * 获取数据库Table 列表
     * @param query
     * @return
     */
    Page<DatabaseTable> selectPage(GenerateTableQuery query);

    /**
     * 获取代码生成全局配置
     * @return
     */
    GenerateConfig getGlobalConfig();

    /**
     * 获取代码生成Table配置
     * @param tableName
     * @return
     */
    GenerateConfig getTableConfig(String tableName);

    /**
     * 获取代码生成Table列
     * @param tableName
     * @return
     */
    List<GenerateTableColumn> selectTableColumnList(String tableName);
}
