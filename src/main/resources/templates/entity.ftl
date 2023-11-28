package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>


/**
 * @author ${global.author}
 * @date ${date}
 * @description ${remarks!}实体
 */
<#if entityType == 'entity'>
@TableName("${table.name}")
</#if>
@Schema(title = "${remarks!}实体")
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
    @Schema(title = "${column.columnRemarks}")
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
            <#if column.dictDomainCode?? && column.dictDomainCode != "">
    @EasyExcelSelect(code = "${column.dictDomainCode!}")
                <#if (column.columnRemarks!?index_of('#') > -1)>
    @ExcelProperty(value = "${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}")
                <#else >
    @ExcelProperty(value = "${column.columnRemarks!}")
                </#if>
            <#else >
    @ExcelProperty(value = "${column.columnRemarks!}")
            </#if>
        <#else >
    @ExcelIgnore
        </#if>
    </#if>
    <#if (entityType == 'createDTO' || entityType == 'updateDTO') && (column.isRequired == 0)>
        <#if column.javaType == 'String'>
            <#if column.dictDomainCode?? && column.dictDomainCode != "" && (column.columnRemarks!?index_of('#') > -1)>
    @NotBlank(message = "${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}不能为空")
            <#else>
    @NotBlank(message = "${column.columnRemarks!}不能为空")
            </#if>
        <#else >
            <#if column.dictDomainCode?? && column.dictDomainCode != "" && (column.columnRemarks!?index_of('#') > -1)>
    @NotNull(message = "${column.columnRemarks!?substring(0, column.columnRemarks!?index_of('#'))?trim}不能为空")
            <#else>
    @NotNull(message = "${column.columnRemarks!}不能为空")
            </#if>
        </#if>
    </#if>
    <#-- 遍历字段 -->
    private ${column.javaType} ${column.javaName};
</#list>
}
