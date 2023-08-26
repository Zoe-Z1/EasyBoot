package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>

/**
 * @author ${global.author}
 * @date ${date}
 * @description ${table.remarks!} Mapper接口
 */
@Mapper
public interface ${className} extends ${superName}<${entityName}> {

}
