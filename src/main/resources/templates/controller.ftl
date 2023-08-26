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
@Api(tags = "${remarks!}接口")
@RestController
@RequestMapping("/${table.camelName}")
<#if superClass??>
public class ${className} extends ${superName} {
<#else>
public class ${className} {
</#if>

    @Resource
    private ${serviceName} ${serviceCamelName};


    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "获取${remarks!}列表")
    <#if annotation.enableLog>
    @EasyLog(module = "获取${remarks!}列表", operateType = OperateTypeEnum.SELECT)
    </#if>
    @GetMapping("/page")
    public Result<IPage<${entityName}>> page(@Validated ${queryName} query) {
        return Result.success(${serviceCamelName}.selectPage(query));
    }

    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "获取${remarks!}详情")
    <#if annotation.enableLog>
    @EasyLog(module = "获取${remarks!}详情", operateType = OperateTypeEnum.SELECT)
    </#if>
    @GetMapping("/detail/{id}")
    public Result<${entityName}> detail(@PathVariable Long id) {
        return Result.success(${serviceCamelName}.detail(id));
    }

    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "创建${remarks!}")
    @EasyLog(module = "创建${remarks!}", operateType = OperateTypeEnum.INSERT)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody ${entityName}CreateDTO dto) {
        return Result.r(${serviceCamelName}.create(dto));
    }

    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "编辑${remarks!}")
    @EasyLog(module = "编辑${remarks!}", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody ${entityName}UpdateDTO dto) {
        return Result.r(${serviceCamelName}.updateById(dto));
    }

    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "删除${remarks!}")
    @EasyLog(module = "删除${remarks!}", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(${serviceCamelName}.deleteById(id));
    }

    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "批量删除${remarks!}")
    @EasyLog(module = "批量删除${remarks!}", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(${serviceCamelName}.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "导入${remarks!}")
    @EasyLog(module = "导入${remarks!}", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto) {
        Assert.notNull(dto.getFile(), "文件不能为空");
        try {
            List<${entityName}> list = EasyExcel.read(dto.getFile().getInputStream())
                    .head(${entityName}.class)
                    .excelType(FileUtil.getExcelType(dto.getFile()))
                    .sheet()
                    .doReadSync();
            List<ImportExcelError> errors = new ArrayList<>();
            List<${entityName}> errorList = new ArrayList<>();
            // 导入Excel处理
            ${serviceCamelName}.importExcel(list, errorList, errors);
            String filePath = "";
            if (!errorList.isEmpty()) {
                // 将错误数据写到Excel文件
                filePath = FileUtil.getFullPath(easyFile.getFilePath(), "${remarks!}导入错误信息");
                EasyExcel.write(filePath).head(${entityName}.class)
                        .sheet().registerWriteHandler(new ImportErrorCellWriteHandler(errors))
                        .doWrite(errorList);
                filePath = FileUtil.getMapPath(filePath, easyFile.getFilePath(),
                easyFile.getFileMapPath());
            }
            ImportVO importVO = ImportVO.builder()
                    .count(list.size())
                    .errorCount(errorList.size())
                    .errorFilePath(filePath)
                    .build();
                    return Result.success(importVO);
        } catch (IOException e) {
            log.error("导入Excel失败 e -> ", e);
            throw new FileException("导入Excel失败");
        }
    }

    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "导出${remarks!}")
    @EasyLog(module = "导出${remarks!}", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody ${entityName}Query query) {
        String filePath = FileUtil.getFullPath(easyFile.getExcelPath(), "${remarks!}");
        query.setPageNum(1);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(filePath, ${entityName}.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("${remarks!}").build();
        while (true) {
            IPage<${entityName}> page = ${serviceCamelName}.selectPage(query);
            build.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
        try {
            FileUtil.downloadAndDelete(filePath, response);
        } catch (IOException e) {
            log.error("导出Excel失败 e -> ", e);
            throw new FileException("导出Excel失败");
        }
    }

    @ApiOperationSupport(author = "${global.author}")
    @ApiOperation(value = "下载${remarks!}导入模板")
    @EasyLog(module = "下载${remarks!}导入模板", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void downloadTemplate() {
        String filePath = FileUtil.getFullPath(easyFile.getExcelPath(), "${remarks!}导入模板");
        EasyExcel.write(filePath, ${entityName}.class)
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("${remarks!}导入模板")
                .doWrite(new ArrayList<>());
        try {
            FileUtil.downloadAndDelete(filePath, response);
        } catch (IOException e) {
            log.error("下载${remarks!}导入模板失败 e -> ", e);
            throw new FileException("下载${remarks!}导入模板失败");
        }
    }
}
