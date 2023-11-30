package com.easy.boot3.admin.generateColumn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easy.boot3.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot3.admin.generateColumn.entity.GenerateColumnQuery;
import com.easy.boot3.admin.generateColumn.entity.GenerateColumnUpdateDTO;

import java.util.List;

/**
* @author zoe
* @date 2023/09/15
* @description 代码生成列配置 服务类
*/
public interface IGenerateColumnService extends IService<GenerateColumn> {

    /**
     * 分页查询代码生成列配置
     * @param query
     * @return
     */
    List<GenerateColumn> selectList(GenerateColumnQuery query);

    /**
     * 编辑代码生成列配置
     * @param dto
     * @return
     */
    Boolean updateByTableName(List<GenerateColumnUpdateDTO> dto);

    /**
     * 删除代码生成列配置
     * @param tableName
     * @return
     */
    Boolean deleteByTableName(String tableName);

    /**
     * 批量删除代码生成列配置
     * @param tableNames
     * @return
     */
    Boolean deleteBatchByTableNames(List<String> tableNames);
}
