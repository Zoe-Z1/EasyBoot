package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>

/**
* @author ${global.author}
* @date ${date}
* @description ${remarks!} 服务类
*/
public interface ${className} extends ${superName}<${entityName}> {

<#if queryName??>
    /**
     * 分页查询${remarks!}
     * @param query
     * @return
     */
    IPage<${entityName}> selectPage(${queryName} query);

<#else >
    /**
     * 查询${remarks!}列表
     * @param ${entityCamelName}
     * @return
     */
    List<${entityName}> selectList(${entityName} ${entityCamelName});

</#if>
    /**
     * 获取${remarks!}详情
     * @param id
     * @return
     */
    <#if voName??>${voName}<#else >${entityName}</#if> detail(Long id);

    /**
     * 创建${remarks!}
     * @param <#if createDTOName??>dto<#else >${entityCamelName}</#if>
     * @return
     */
    Boolean create(<#if createDTOName??>${createDTOName} dto<#else >${entityName} ${entityCamelName}</#if>);

    /**
     * 编辑${remarks!}
     * @param <#if updateDTOName??>dto<#else >${entityCamelName}</#if>
     * @return
     */
<#if updateDTOName??>
    Boolean updateById(${updateDTOName} dto);
<#else >
    Boolean edit(${entityName} ${entityCamelName});
</#if>

    /**
     * 删除${remarks!}
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 批量删除${remarks!}
     * @param ids
     * @return
     */
    Boolean deleteBatchByIds(List<Long> ids);

<#if global.enableImport>
    /**
     * 导入Excel
     * @param list 要导入的数据集合
     * @param errorList 导入错误的数据集合
     * @param errors 错误标注集合
     */
    void importExcel(List<${entityName}> list, List<${entityName}> errorList, List<${ImportExcelError}> errors);
</#if>

}
