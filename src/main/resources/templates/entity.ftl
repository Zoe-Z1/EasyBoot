package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>


/**
 * @author ${global.author}
 * @date ${date}
 * @description ${remarks!}实体
 */
@TableName("${table.name}")
@ApiModel(value = "${remarks!}实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
<#if superName??>
    <#if annotation.enableBuilder>
@SuperBuilder
    </#if>
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ${className} extends ${superName} {
<#else>
    <#if annotation.enableBuilder>
@SuperBuilder
    </#if>
@ToString
@EqualsAndHashCode
public class ${className} {
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list fields as field>

    <#if field.remarks!?length gt 0>
    @ApiModelProperty("${field.remarks}")
    </#if>
    <#if enableTableField>
        <#if field.isPrimaryKey>
    @TableId
        <#else >
    @TableField("${field.name}")
        </#if>
    </#if>
    <#if enableExcel??>
    @ExcelProperty(value = "${field.remarks!}")
    </#if>
    <#-- 遍历字段 -->
    private ${field.javaType} ${field.javaName};
</#list>
}
