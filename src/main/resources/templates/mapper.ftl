package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>

/**
 * @author ${global.author}
 * @date ${date}
 * @description ${remarks!} Mapper接口
 */
@Mapper
public interface ${className} extends ${superName}<${entityName}> {

}
