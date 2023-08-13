package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
<#if mapperAnnotationClass??>
import ${mapperAnnotationClass.name};
</#if>
import org.apache.ibatis.annotations.Mapper;

/**
* @author ${author}
* @date ${date}
* @description ${table.comment!} Mapper接口
*/
<#if mapperAnnotationClass??>
@${mapperAnnotationClass.simpleName}
</#if>
@Mapper
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
