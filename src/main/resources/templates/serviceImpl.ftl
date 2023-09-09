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
    public IPage<${entityName}> selectPage(${queryName} query) {
        Page<${entityName}> page = new Page<>(query.getPageNum(), query.getPageSize());
        return lambdaQuery()
                .page(page);
    }

    @Override
    public ${entityName} detail(Long id) {
        return getById(id);
    }

    @Override
    public Boolean create(${createDTOName} dto) {
        ${entityName} ${entityCamelName} = BeanUtil.copyBean(dto, ${entityName}.class);
        return save(${entityCamelName});
    }

    @Override
    public Boolean updateById(${updateDTOName} dto) {
        ${entityName} ${entityCamelName} = BeanUtil.copyBean(dto, ${entityName}.class);
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
