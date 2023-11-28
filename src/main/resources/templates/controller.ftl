package ${pkg};

<#list pkgs as pkg>
import ${pkg};
</#list>

/**
 * @author ${global.author}
 * @date ${date}
 * @description ${remarks!}接口
 */
@Slf4j
@Tag(name = "${remarks!}接口")
@RestController
@RequestMapping("${global.requestMappingPrefix!}/${table.moduleName}")
<#if superClass??>
public class ${className} extends ${superName} {
<#else>
public class ${className} {
</#if>

    @Resource
    private ${serviceName} ${serviceCamelName};


<#if queryName??>
    @SaCheckPermission(value = "${permission}:page")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "分页获取${remarks!}列表")
    <#if annotation.enableLog>
    @EasyLog(module = "分页获取${remarks!}列表", operateType = OperateTypeEnum.SELECT)
    </#if>
    @GetMapping("/page")
    public Result<IPage<${entityName}>> page(@Validated ${queryName} query) {
        return Result.success(${serviceCamelName}.selectPage(query));
    }

<#else >
    @SaCheckPermission(value = "${permission}:list")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "获取${remarks!}列表")
    <#if annotation.enableLog>
    @EasyLog(module = "获取${remarks!}列表", operateType = OperateTypeEnum.SELECT)
    </#if>
    @GetMapping("/list")
    public Result<List<${entityName}>> list(@Validated ${entityName} ${entityCamelName}) {
        return Result.success(${serviceCamelName}.selectList(${entityCamelName}));
    }

</#if>
    @SaCheckPermission(value = "${permission}:detail")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "获取${remarks!}详情")
    <#if annotation.enableLog>
    @EasyLog(module = "获取${remarks!}详情", operateType = OperateTypeEnum.SELECT)
    </#if>
    @GetMapping("/detail/{id}")
    public Result<<#if voName??>${voName}<#else >${entityName}</#if>> detail(@PathVariable Long id) {
        return Result.success(${serviceCamelName}.detail(id));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "${permission}:create")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "创建${remarks!}")
    @EasyLog(module = "创建${remarks!}", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody <#if createDTOName??>${createDTOName} dto<#else >${entityName} ${entityCamelName}</#if>) {
        return Result.r(${serviceCamelName}.create(<#if createDTOName??>dto<#else >${entityCamelName}</#if>));
    }

    @SaCheckPermission(value = "${permission}:update")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "编辑${remarks!}")
    @EasyLog(module = "编辑${remarks!}", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody <#if updateDTOName??>${updateDTOName} dto<#else >${entityName} ${entityCamelName}</#if>) {
<#if updateDTOName??>
        return Result.r(${serviceCamelName}.updateById(dto));
<#else >
        return Result.r(${serviceCamelName}.edit(${entityCamelName});
</#if>
    }

    @SaCheckPermission(value = "${permission}:del")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "删除${remarks!}")
    @EasyLog(module = "删除${remarks!}", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(${serviceCamelName}.deleteById(id));
    }

    @SaCheckPermission(value = "${permission}:batch:del")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "批量删除${remarks!}")
    @EasyLog(module = "批量删除${remarks!}", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(${serviceCamelName}.deleteBatchByIds(ids));
    }

<#if global.enableImport>
    @SaCheckPermission(value = "${permission}:import")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "导入${remarks!}")
    @EasyLog(module = "导入${remarks!}", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<${ImportVO}> importExcel(UploadDTO dto) throws IOException {
        Assert.notNull(dto.getFile(), "文件不能为空");
        // 同步的写法仅适用于数据量不多的情况，否则容易导致OOM
        List<${entityName}> list = EasyExcel.read(dto.getFile().getInputStream())
                .head(${entityName}.class)
                .sheet()
                .doReadSync();
        List<${ImportExcelError}> errors = new ArrayList<>();
        List<${entityName}> errorList = new ArrayList<>();
        // 导入Excel处理
        ${serviceCamelName}.importExcel(list, errorList, errors);
        String base64 = "";
        if (!errorList.isEmpty()) {
            // 将错误数据写到Excel文件
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            EasyExcel.write(out).head(${entityName}.class)
                .sheet("${remarks!}导入错误信息列表")
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(${entityName}.class))
                .registerWriteHandler(new ExportExcelErrorCellWriteHandler(errors))
                .doWrite(errorList);
            base64 = Base64.getEncoder().encodeToString(out.toByteArray());
        }
        ${ImportVO} importVO = ${ImportVO}.builder()
                .count(list.size())
                .errorCount(errorList.size())
                .errorBase64(base64)
                .build();
                return Result.success(importVO);
    }
</#if>

<#if global.enableExport>
    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "${permission}:export")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "导出${remarks!}")
    @EasyLog(module = "导出${remarks!}", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody ${entityName}Query query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter writer = EasyExcel.write(response.getOutputStream(), ${entityName}.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("${remarks!}信息列表").build();
        while (true) {
            IPage<${entityName}> page = ${serviceCamelName}.selectPage(query);
            writer.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        writer.finish();
    }
</#if>

<#if global.enableImport>
    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "${permission}:download")
    @ApiOperationSupport(author = "${global.author}")
    @Operation(summary = "下载${remarks!}导入模板")
    @EasyLog(module = "下载${remarks!}导入模板", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/download")
    public void downloadTemplate() throws IOException {
        EasyExcel.write(response.getOutputStream(), ${entityName}.class)
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(${entityName}.class))
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("${remarks!}导入模板")
                .doWrite(new ArrayList<>());
    }
</#if>

}
