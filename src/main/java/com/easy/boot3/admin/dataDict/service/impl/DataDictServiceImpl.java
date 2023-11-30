package com.easy.boot3.admin.dataDict.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.boot3.admin.dataDict.entity.DataDict;
import com.easy.boot3.admin.dataDict.entity.DataDictCreateDTO;
import com.easy.boot3.admin.dataDict.entity.DataDictQuery;
import com.easy.boot3.admin.dataDict.entity.DataDictUpdateDTO;
import com.easy.boot3.admin.dataDict.mapper.DataDictMapper;
import com.easy.boot3.admin.dataDict.service.IDataDictService;
import com.easy.boot3.common.base.BaseEntity;
import com.easy.boot3.common.excel.entity.ImportExcelError;
import com.easy.boot3.exception.BusinessException;
import com.easy.boot3.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/08/01
* @description 数据字典 服务实现类
*/
@Service
public class DataDictServiceImpl extends ServiceImpl<DataDictMapper, DataDict> implements IDataDictService {

    @Override
    public IPage<DataDict> selectPage(DataDictQuery query) {
        Page<DataDict> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery.like(DataDict::getCode, query.getKeyword()).or()
                            .like(DataDict::getLabel, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getCode()), DataDict::getCode, query.getCode())
                .like(StrUtil.isNotEmpty(query.getLabel()), DataDict::getLabel, query.getLabel())
                .eq(Objects.nonNull(query.getStatus()) , DataDict::getStatus, query.getStatus())
                .eq(DataDict::getDomainId, query.getDomainId())
                .orderByAsc(DataDict::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public List<DataDict> selectListByDomainIds(List<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return lambdaQuery()
                .select(DataDict::getDomainId, DataDict::getCode, DataDict::getLabel)
                .eq(DataDict::getStatus, 1)
                .in(DataDict::getDomainId, ids)
                .orderByAsc(DataDict::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public DataDict detail(Long id) {
        return getById(id);
    }

    @Override
    public DataDict getByDomainIdAndCode(Long domainId, String code) {
        return lambdaQuery().eq(DataDict::getDomainId, domainId)
                .eq(DataDict::getCode, code)
                .one();
    }

    @Override
    public List<DataDict> getByDomainId(Long domainId) {
        return lambdaQuery()
                .select(DataDict::getCode, DataDict::getLabel)
                .eq(DataDict::getStatus, 1)
                .eq(DataDict::getDomainId, domainId)
                .orderByAsc(DataDict::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public Boolean create(DataDictCreateDTO dto) {
        DataDict dataDict = this.getByDomainIdAndCode(dto.getDomainId(), dto.getCode());
        if (dataDict != null) {
            throw new BusinessException("字典键已存在");
        }
        DataDict entity = BeanUtil.copyBean(dto, DataDict.class);
        return save(entity);
    }

    @Override
    public Boolean updateById(DataDictUpdateDTO dto) {
        if (StrUtil.isNotEmpty(dto.getCode())) {
            DataDict dataDict = this.getById(dto.getId());
            dataDict = this.getByDomainIdAndCode(dataDict.getDomainId(), dto.getCode());
            if (dataDict != null && !dataDict.getId().equals(dto.getId())) {
                throw new BusinessException("字典键已存在");
            }
        }
        DataDict entity = BeanUtil.copyBean(dto, DataDict.class);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        return removeBatchByIds(ids);
    }

    @Override
    public void importExcel(List<DataDict> list, List<DataDict> errorList, List<ImportExcelError> errors) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<String> codes = list.stream().map(DataDict::getCode).distinct().collect(Collectors.toList());
        List<DataDict> dataDicts = lambdaQuery().in(DataDict::getCode, codes).
                eq(DataDict::getDomainId, list.get(0).getDomainId())
                .list();
        codes = dataDicts.stream().map(DataDict::getCode).distinct().collect(Collectors.toList());
        // 去除表头 行数从1起算
        int rowIndex = 1;
        Iterator<DataDict> iterator = list.iterator();
        while (iterator.hasNext()) {
            DataDict dataDict = iterator.next();
            boolean isError = false;
            ImportExcelError.ImportExcelErrorBuilder builder = ImportExcelError.builder();
            if (StrUtil.isEmpty(dataDict.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("字典键不能为空");
                errors.add(builder.build());
            } else if (dataDict.getCode().length() < 1 || dataDict.getCode().length() > 50) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("字典键在1-50个字符之间");
                errors.add(builder.build());
            }
            if (codes.contains(dataDict.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("字典键已存在");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(dataDict.getLabel())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("字典标签不能为空");
                errors.add(builder.build());
            } else if (dataDict.getLabel().length() < 1 || dataDict.getLabel().length() > 20) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("字典标签在1-20个字符之间");
                errors.add(builder.build());
            }
            if (dataDict.getStatus() == null) {
                isError = true;
                builder.columnIndex(2).rowIndex(rowIndex).msg("字典状态不能为空");
                errors.add(builder.build());
            }
            // 这一行有错误，行数增加，错误数据加到list，删除原list的数据
            if (isError) {
                rowIndex++;
                errorList.add(dataDict);
                iterator.remove();
            } else {
                // 没有错误，会进行新增，加进去不存在的，防止后续存在
                codes.add(dataDict.getCode());
            }
        }
        saveBatch(list);
    }

}
