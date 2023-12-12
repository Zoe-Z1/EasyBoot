package cn.easy.boot.admin.dataDictDomain.service.impl;

import cn.easy.boot.admin.dataDict.entity.DataDict;
import cn.easy.boot.admin.dataDict.service.IDataDictService;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomain;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainCreateDTO;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainQuery;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainUpdateDTO;
import cn.easy.boot.admin.dataDictDomain.mapper.DataDictDomainMapper;
import cn.easy.boot.admin.dataDictDomain.service.IDataDictDomainService;
import cn.easy.boot.common.base.BaseEntity;
import cn.easy.boot.common.excel.entity.ImportExcelError;
import cn.easy.boot.utils.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.easy.boot.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author zoe
* @date 2023/08/01
* @description 数据字典域 服务实现类
*/
@Service
public class DataDictDomainServiceImpl extends ServiceImpl<DataDictDomainMapper, DataDictDomain> implements IDataDictDomainService {

    @Resource
    private IDataDictService dataDictService;

    @Override
    public List<DataDictDomain> selectAll() {
        return lambdaQuery()
                .select(BaseEntity::getId, DataDictDomain::getCode, DataDictDomain::getName)
                .orderByAsc(DataDictDomain::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
    }

    @Override
    public IPage<DataDictDomain> selectPage(DataDictDomainQuery query) {
        Page<DataDictDomain> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery.like(DataDictDomain::getCode, query.getKeyword()).or()
                            .like(DataDictDomain::getName, query.getKeyword());
                })
                .like(StrUtil.isNotEmpty(query.getCode()), DataDictDomain::getCode, query.getCode())
                .like(StrUtil.isNotEmpty(query.getName()), DataDictDomain::getName, query.getName())
                .eq(Objects.nonNull(query.getStatus()), DataDictDomain::getStatus, query.getStatus())
                .between(Objects.nonNull(query.getStartTime()) && Objects.nonNull(query.getEndTime()),
                        BaseEntity::getCreateTime, query.getStartTime(), query.getEndTime())
                .orderByAsc(DataDictDomain::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }


    @Override
    public Map<String, List<DataDict>> selectDictAll() {
        List<DataDictDomain> list = lambdaQuery()
                .select(DataDictDomain::getId, DataDictDomain::getCode)
                .eq(DataDictDomain::getStatus, 1)
                .orderByAsc(DataDictDomain::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .list();
        if (CollUtil.isEmpty(list)) {
            return new HashMap<>();
        }
        List<Long> ids = list.stream().map(DataDictDomain::getId).collect(Collectors.toList());
        List<DataDict> dataDicts = dataDictService.selectListByDomainIds(ids);
        Map<Long, List<DataDict>> dataDictMap = dataDicts.stream().collect(Collectors.groupingBy(DataDict::getDomainId));
        Map<String, List<DataDict>> resMap = new HashMap<>();
        list.forEach(item -> resMap.put(item.getCode(), dataDictMap.get(item.getId())));
        return resMap;
    }

    @Override
    public DataDictDomain detail(Long id) {
        return getById(id);
    }

    @Override
    public DataDictDomain getByCode(String code) {
        return lambdaQuery().eq(DataDictDomain::getCode, code).one();
    }

    @Override
    public DataDictDomain getNotDisabledByCode(String code) {
        return lambdaQuery().eq(DataDictDomain::getCode, code).eq(DataDictDomain::getStatus, 1).one();
    }

    @Override
    public List<DataDictDomain> selectListByCodes(List<String> codes) {
        if (CollUtil.isEmpty(codes)) {
            return new ArrayList<>();
        }
        return lambdaQuery().in(DataDictDomain::getCode, codes).eq(DataDictDomain::getStatus, 1).list();
    }

    @Override
    public List<DataDict> selectListByDomainCode(String code) {
        DataDictDomain domain = this.getNotDisabledByCode(code);
        if (domain == null) {
            return new ArrayList<>();
        }
        return dataDictService.getByDomainId(domain.getId());
    }

    @Override
    public Boolean create(DataDictDomainCreateDTO dto) {
        DataDictDomain dataDictDomain = this.getByCode(dto.getCode());
        if (dataDictDomain != null) {
            throw new BusinessException("字典域编码已存在");
        }
        DataDictDomain entity = BeanUtil.copyBean(dto, DataDictDomain.class);
        return save(entity);
    }

    @Override
    public Boolean updateById(DataDictDomainUpdateDTO dto) {
        if (StrUtil.isNotEmpty(dto.getCode())) {
            DataDictDomain dataDictDomain = this.getByCode(dto.getCode());
            if (dataDictDomain != null && !dataDictDomain.getId().equals(dto.getId())) {
                throw new BusinessException("字典域编码已存在");
            }
        }
        DataDictDomain entity = BeanUtil.copyBean(dto, DataDictDomain.class);
        return updateById(entity);
    }

    @Override
    public Boolean deleteById(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<DataDict> list = dataDictService.selectListByDomainIds(ids);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("存在下级数据字典，不允许删除");
        }
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        List<DataDict> list = dataDictService.selectListByDomainIds(ids);
        if (CollUtil.isNotEmpty(list)) {
            throw new BusinessException("存在下级数据字典，不允许删除");
        }
        return removeBatchByIds(ids);
    }

    @Override
    public void importExcel(List<DataDictDomain> list, List<DataDictDomain> errorList, List<ImportExcelError> errors) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        List<String> codes = list.stream().map(DataDictDomain::getCode).distinct().collect(Collectors.toList());
        List<DataDictDomain> dataDictDomains = lambdaQuery().in(DataDictDomain::getCode, codes).list();
        codes = dataDictDomains.stream().map(DataDictDomain::getCode).distinct().collect(Collectors.toList());
        // 去除表头 行数从1起算
        int rowIndex = 1;
        Iterator<DataDictDomain> iterator = list.iterator();
        while (iterator.hasNext()) {
            DataDictDomain dataDictDomain = iterator.next();
            boolean isError = false;
            ImportExcelError.ImportExcelErrorBuilder builder = ImportExcelError.builder();
            if (StrUtil.isEmpty(dataDictDomain.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("字典域编码不能为空");
                errors.add(builder.build());
            } else if (dataDictDomain.getCode().length() < 1 || dataDictDomain.getCode().length() > 50) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("字典域编码在1-50个字符之间");
                errors.add(builder.build());
            }
            if (codes.contains(dataDictDomain.getCode())) {
                isError = true;
                builder.columnIndex(0).rowIndex(rowIndex).msg("字典域编码已存在");
                errors.add(builder.build());
            }
            if (StrUtil.isEmpty(dataDictDomain.getName())) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("字典域名称不能为空");
                errors.add(builder.build());
            } else if (dataDictDomain.getName().length() < 1 || dataDictDomain.getName().length() > 50) {
                isError = true;
                builder.columnIndex(1).rowIndex(rowIndex).msg("字典域名称在1-50个字符之间");
                errors.add(builder.build());
            }
            if (dataDictDomain.getStatus() == null) {
                isError = true;
                builder.columnIndex(2).rowIndex(rowIndex).msg("字典域状态不能为空");
                errors.add(builder.build());
            }
            // 这一行有错误，行数增加，错误数据加到list，删除原list的数据
            if (isError) {
                rowIndex++;
                errorList.add(dataDictDomain);
                iterator.remove();
            } else {
                // 没有错误，会进行新增，加进去不存在的，防止后续存在
                codes.add(dataDictDomain.getCode());
            }
        }
        saveBatch(list);
    }

}
