package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>

/**
* @author ${global.author}
* @date ${date}
* @description ${remarks!} 服务实现类
*/
@Service
public class ${className} extends ${superName}<${mapperName}, ${entityName}> implements ${serviceName} {

    @Override
<#if queryName??>
    public IPage<${entityName}> selectPage(${queryName} query) {
<#else >
    public List<${entityName}> selectList(${entityName} ${entityCamelName}) {
</#if>
    <#if queryName??>
        Page<${entityName}> page = new Page<>(query.getPageNum(), query.getPageSize());
    </#if>
        return lambdaQuery()
            <#if queryName?? && (keywordFields?size > 0)>
                .and(StrUtil.isNotEmpty(query.getKeyword()), keywordQuery -> {
                    keywordQuery
                <#list keywordFields as field>
                    .like(${entityName}::get${field?cap_first}, query.getKeyword())<#if (field_index + 1) == keywordFields?size>;<#else >.or()</#if>
                </#list>
                })
            </#if>
<#list columns as column>
    <#if column.isAdvancedSearch == 0>
            <#if column.javaType == 'String'>
                .like(StrUtil.isNotEmpty(<#if queryName??>query<#else >${entityCamelName}</#if>.get${column.javaName?cap_first}()), ${entityName}::get${column.javaName?cap_first}, <#if queryName??>query<#else >${entityCamelName}</#if>.get${column.javaName?cap_first}())
            <#else >
                .eq(<#if queryName??>query<#else >${entityCamelName}</#if>.get${column.javaName?cap_first}() != null, ${entityName}::get${column.javaName?cap_first}, <#if queryName??>query<#else >${entityCamelName}</#if>.get${column.javaName?cap_first}())
            </#if>
    </#if>
</#list>
                .orderByDesc(${entityName}::getCreateTime)
            <#if queryName??>
                .page(page);
            <#else >
                .list();
            </#if>
    }

    @Override
    public <#if voName??>${voName}<#else >${entityName}</#if> detail(Long id) {
        <#if voName??>
        ${entityName} ${entityCamelName} = getById(id);
        ${voName} vo = BeanUtil.copyBean(${entityCamelName}, ${voName}.class);
        return vo;
        <#else >
        return getById(id);
        </#if>
    }

    @Override
    public Boolean create(<#if createDTOName??>${createDTOName} dto<#else >${entityName} ${entityCamelName}</#if>) {
        <#if createDTOName??>
        ${entityName} ${entityCamelName} = BeanUtil.copyBean(dto, ${entityName}.class);
        </#if>
        return save(${entityCamelName});
    }

    @Override

<#if updateDTOName??>
    public Boolean updateById(${updateDTOName} dto) {
        ${entityName} ${entityCamelName} = BeanUtil.copyBean(dto, ${entityName}.class);
    <#else >
    public Boolean edit(${entityName} ${entityCamelName}) {
</#if>
        return updateById(${entityCamelName});
    }

    @Override
    public Boolean deleteById(Long id) {
        return removeById(id);
    }

    @Override
    public Boolean deleteBatchByIds(List<Long> ids) {
        return removeBatchByIds(ids);
    }

<#if global.enableImport>
    @Override
    public void importExcel(List<${entityName}> list, List<${entityName}> errorList, List<${ImportExcelError}> errors) {
        if (CollUtil.isEmpty(list)) {
            return;
        }
        // 去除表头 行数从1起算
        int rowIndex = 1;
        Iterator<${entityName}> iterator = list.iterator();
        while (iterator.hasNext()) {
            ${entityName} ${entityCamelName} = iterator.next();
            boolean isError = false;
            ${ImportExcelError}.${ImportExcelError}Builder builder = ${ImportExcelError}.builder();
            // todo 这里处理导入逻辑



            // 这一行有错误，行数增加，错误数据加到list，删除原list的数据
            if (isError) {
                rowIndex++;
                errorList.add(${entityCamelName});
                iterator.remove();
            } else {
                // 没有错误，会进行新增

            }
        }
        saveBatch(list);
    }
</#if>

}
