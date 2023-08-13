package ${package.Service};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${package.Entity}.${entity};
import ${package.Entity}.${entity}Query;
import ${package.Entity}.${entity}CreateDTO;
import ${package.Entity}.${entity}UpdateDTO;
import ${superServiceClassPackage};

import java.util.List;

/**
* @author ${author}
* @date ${date}
* @description ${table.comment!} 服务类
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
    * 查询${table.comment!}列表
    * @param query
    * @return
    */
    IPage<${entity}> selectPage(${entity}Query query);

    /**
    * 获取${table.comment!}详情
    * @param id
    * @return
    */
    ${entity} detail(Long id);

    /**
    * 创建${table.comment!}
    * @param dto
    * @return
    */
    Boolean create(${entity}CreateDTO dto);

    /**
    * 编辑${table.comment!}
    * @param dto
    * @return
    */
    Boolean updateById(${entity}UpdateDTO dto);

    /**
    * 删除${table.comment!}
    * @param id
    * @return
    */
    Boolean deleteById(Long id);

    /**
    * 批量删除${table.comment!}
    * @param ids
    * @return
    */
    Boolean deleteBatchByIds(List<Long> ids);

}
</#if>
