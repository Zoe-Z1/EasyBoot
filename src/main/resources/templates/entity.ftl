package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>


/**
 * @author ${global.author}
 * @date ${date}
 * @description ${remarks!}实体
 */
<#if isEntity>
@TableName("${table.name}")
</#if>
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
<#list columns as column>

    <#if column.columnRemarks!?length gt 0>
    @ApiModelProperty("${column.columnRemarks}")
    </#if>
    <#if enableTableField>
        <#if column.isPrimaryKey == 0>
    @TableId
        <#else >
    @TableField("${column.columnName}")
        </#if>
    </#if>
<#if enableExcel??>
    <#if column.isExcel == 0>
        <#if column.dictDomainCode?? && column.dictDomainCode != "" && column.dictDomainCode != "">
    @EasyExcelSelect(code = "${column.dictDomainCode!}")
    @ExcelProperty(value = "${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}")
        <#else >
    @ExcelProperty(value = "${column.columnRemarks!}")
        </#if>
    <#else >
    @ExcelIgnore
    </#if>
</#if>
    <#-- 遍历字段 -->
    private ${column.javaType} ${column.javaName};
</#list>
}
