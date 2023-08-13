package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Entity}.${entity}Query;
import ${package.Entity}.${entity}CreateDTO;
import ${package.Entity}.${entity}UpdateDTO;
import ${package.Mapper}.${table.mapperName};
<#if table.serviceInterface>
import ${package.Service}.${table.serviceName};
</#if>
import ${superServiceImplClassPackage};
import com.fast.start.common.base.BaseEntity;
import com.fast.start.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ${author}
* @date ${date}
* @description ${table.comment!} 服务实现类
*/
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>()<#if table.serviceInterface>, ${table.serviceName}</#if> {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}><#if table.serviceInterface> implements ${table.serviceName}</#if> {

    @Override
    public IPage<${entity}> selectPage(${entity}Query query) {
        Page<${entity}> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .orderByAsc(${entity}::getSort)
                .orderByDesc(BaseEntity::getCreateTime)
                .page(page);
    }

    @Override
    public ${entity} detail(Long id) {
        return getById(id);
    }

    @Override
    public Boolean create(${entity}CreateDTO dto) {
        ${entity} entity = BeanUtil.copyBean(dto, ${entity}.class);
        return save(entity);
    }

    @Override
    public Boolean updateById(${entity}UpdateDTO dto) {
        ${entity} entity = BeanUtil.copyBean(dto, ${entity}.class);
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

}
</#if>
