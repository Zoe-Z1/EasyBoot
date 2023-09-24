package com.easy.boot.admin.generateColumn.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot.admin.dataDictDomain.entity.DataDictDomain;
import com.easy.boot.admin.dataDictDomain.service.IDataDictDomainService;
import com.easy.boot.admin.generateColumn.entity.GenerateColumn;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnQuery;
import com.easy.boot.admin.generateColumn.entity.GenerateColumnUpdateDTO;
import com.easy.boot.admin.generateColumn.mapper.GenerateColumnMapper;
import com.easy.boot.admin.generateColumn.service.IGenerateColumnService;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigQuery;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigVO;
import com.easy.boot.admin.generateConfig.service.IGenerateConfigService;
import com.easy.boot.common.generator.config.FilterConfig;
import com.easy.boot.common.generator.db.DbManager;
import com.easy.boot.common.generator.db.convert.ColumnConvertHandler;
import com.easy.boot.common.generator.db.convert.DbColumnTypeEnum;
import com.easy.boot.common.redisson.EasyLock;
import com.easy.boot.utils.JsonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/09/15
* @description 代码生成列配置 服务实现类
*/
@Service
public class GenerateColumnServiceImpl extends ServiceImpl<GenerateColumnMapper, GenerateColumn> implements IGenerateColumnService {

    @Resource
    private DataSource dataSource;

    @Resource
    private IGenerateConfigService generateConfigService;

    @Resource
    private IDataDictDomainService dataDictDomainService;

    @EasyLock
    @Override
    public List<GenerateColumn> selectList(GenerateColumnQuery query) {
        List<GenerateColumn> list = lambdaQuery()
                .eq(GenerateColumn::getTableName, query.getTableName())
                .list();
        if (CollUtil.isEmpty(list)) {
            GenerateConfigQuery generateConfigQuery = new GenerateConfigQuery(query.getTableName());
            GenerateConfigVO tableConfig = generateConfigService.getTableConfig(generateConfigQuery);
            Set<String> tablePrefix = new HashSet<>();
            Set<String> tableSuffix = new HashSet<>();
            if (StrUtil.isNotEmpty(tableConfig.getExcludeTablePrefix())) {
                tablePrefix = Arrays.stream(tableConfig.getExcludeTablePrefix().split(",")).collect(Collectors.toSet());
            }
            if (StrUtil.isNotEmpty(tableConfig.getExcludeTableSuffix())) {
                tableSuffix = Arrays.stream(tableConfig.getExcludeTableSuffix().split(",")).collect(Collectors.toSet());
            }
            Set<String> excludeField = CollUtil.newHashSet("createBy","createUsername","createTime","updateBy","updateUsername","updateTime","isDel");
            FilterConfig filterConfig = FilterConfig.builder()
                    .excludeTablePrefix(tablePrefix)
                    .excludeTableSuffix(tableSuffix)
                    .excludeField(tableSuffix)
                    .excludeField(excludeField)
                    .build();
            list = DbManager.init(dataSource, filterConfig, ColumnConvertHandler.defaultHandler())
                    .getGenerateColumns(query);
        }
        List<String> domainCodes = list.stream()
                .map(GenerateColumn::getDictDomainCode)
                .filter(StrUtil::isNotEmpty)
                .collect(Collectors.toList());
        List<DataDictDomain> domains = dataDictDomainService.getByCodes(domainCodes);
        Map<String, DataDictDomain> domainMap = domains.stream().collect(Collectors.toMap(DataDictDomain::getCode, x -> x));
        list.forEach(item -> {
            item.setIsCreate(StrUtil.isNotEmpty(item.getDictDomainCode()) && domainMap.get(item.getDictDomainCode()) == null);
        });
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateByTableName(List<GenerateColumnUpdateDTO> dto) {
        List<GenerateColumn> list = JsonUtil.copyList(dto, GenerateColumn.class);
        if (list.isEmpty()) {
            return false;
        }
        String tableName = list.get(0).getTableName();
        deleteByTableName(tableName);
        list.forEach(item -> {
            String pkgName = DbColumnTypeEnum.toOptElement(item.getColumnType()).getValue();
            item.setJavaTypePackageName(pkgName);
        });
        return saveBatch(list);
    }

    @Override
    public Boolean deleteByTableName(String tableName) {
        QueryWrapper<GenerateColumn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_name", tableName);
        return remove(queryWrapper);
    }

    @Override
    public Boolean deleteBatchByTableNames(List<String> tableNames) {
        QueryWrapper<GenerateColumn> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("table_name", tableNames);
        return remove(queryWrapper);
    }

}
