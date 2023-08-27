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

    /**
     * 分页查询${remarks!}
     * @param query
     * @return
     */
    IPage<${entityName}> selectPage(${queryName} query);

    /**
     * 获取${remarks!}详情
     * @param id
     * @return
     */
    ${entityName} detail(Long id);

    /**
     * 创建${remarks!}
     * @param dto
     * @return
     */
    Boolean create(${createDTOName} dto);

    /**
     * 编辑${remarks!}
     * @param dto
     * @return
     */
    Boolean updateById(${updateDTOName} dto);

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

<#if template.enableImport>
    /**
     * 导入Excel
     * @param list 要导入的数据集合
     * @param errorList 导入错误的数据集合
     * @param errors 错误标注集合
     */
    void importExcel(List<${entityName}> list, List<${entityName}> errorList, List<${ImportExcelError}> errors);
</#if>

}
