package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>


/**
* @author ${global.author}
* @date ${date}
* @description ${table.remarks!}实体
*/
@TableName("${table.name}")
@ApiModel(value = "${table.remarks!}实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
<#if superName??>
<#if annotation.enableBuilder>
@SuperBuilder
</#if>
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ${clssName} extends ${superName} {
<#else>
<#if annotation.enableBuilder>
@Builder
</#if>
@ToString
@EqualsAndHashCode
public class ${clssName} {
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>

    <#if field.remarks!?length gt 0>
        @ApiModelProperty("${field.remarks}")
    </#if>
    <#-- 遍历字段 -->
    private ${field.javaType} ${field.javaName};
</#list>
}
