package com.easy.boot.admin.generate.service;

import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.entity.GeneratePreviewVO;
import com.easy.boot.admin.generate.entity.GenerateTableQuery;
import com.easy.boot.common.base.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
     */
    List<GeneratePreviewVO> preview(String tableName) throws IOException;

    /**
     * 批量生成代码
     * @param tableNames
     * @param response
     * @return
     */
    void batchGenerateCode(List<String> tableNames, HttpServletResponse response) throws IOException;
}
