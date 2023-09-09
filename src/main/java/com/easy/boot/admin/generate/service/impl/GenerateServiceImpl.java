package com.easy.boot.admin.generate.service.impl;

import com.easy.boot.admin.generate.entity.GenerateConfig;
import com.easy.boot.admin.generate.entity.GenerateTableColumn;
import com.easy.boot.admin.generate.entity.GenerateTableQuery;
import com.easy.boot.admin.generate.entity.DatabaseTable;
import com.easy.boot.admin.generate.mapper.GenerateMapper;
import com.easy.boot.admin.generate.service.GenerateService;
import com.easy.boot.common.base.Page;
import com.easy.boot.common.generator.GenConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Value("${spring.datasource.url}")
    private String url;


    @Override
    public Page<DatabaseTable> selectPage(GenerateTableQuery query) {
        Page<DatabaseTable> page = new Page<>();
        String[] splits = url.split("/");
        String endStr = splits[splits.length - 1];
        String dbName = endStr.substring(0, endStr.indexOf("?"));
        query.setDbName(dbName)
                .setTableType(GenConstant.TABLE_TYPE_BASE_TABLE);
        List<DatabaseTable> list = generateMapper.selectPage(query);
        Long count = generateMapper.selectCount(query);

        page.setCurrent(query.getPageNum())
                .setSize(query.getPageSize())
                .setTotal(count)
                .setRecords(list);
        return page;
    }

    @Override
    public GenerateConfig getGlobalConfig() {
//        GenerateConfig generateConfig = generateMapper.getGlobalConfig();
        return null;
    }

    @Override
    public GenerateConfig getTableConfig(String tableName) {
        return null;
    }

    @Override
    public List<GenerateTableColumn> selectTableColumnList(String tableName) {
        return null;
    }
}
