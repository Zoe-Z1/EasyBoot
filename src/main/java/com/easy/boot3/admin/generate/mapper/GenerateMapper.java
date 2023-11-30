package com.easy.boot3.admin.generate.mapper;

import com.easy.boot3.admin.generate.entity.GenerateTableQuery;
import com.easy.boot3.admin.generate.entity.DatabaseTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zoe
 * @date 2023/9/9
 * @description 代码生成 Mapper
 */
@Mapper
public interface GenerateMapper {


    /**
     * 获取总条数
     * @param query
     * @return
     */
    Long selectCount(GenerateTableQuery query);

    /**
     * 获取Table列表
     * @param query
     * @return
     */
    List<DatabaseTable> selectPage(GenerateTableQuery query);

    /**
     * 获取数据库Table信息
     * @param query
     * @return
     */
    DatabaseTable getTableByTableName(GenerateTableQuery query);
}
