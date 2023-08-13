package com.fast.start.admin.dataDictDomain.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.start.admin.dataDictDomain.entity.DataDictDomain;
import com.fast.start.admin.dataDictDomain.entity.DataDictDomainCreateDTO;
import com.fast.start.admin.dataDictDomain.entity.DataDictDomainQuery;
import com.fast.start.admin.dataDictDomain.entity.DataDictDomainUpdateDTO;
import com.fast.start.admin.dataDictDomain.service.DataDictDomainService;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.common.base.BaseController;
import com.fast.start.common.base.Result;
import com.fast.start.common.excel.ImportExcelError;
import com.fast.start.common.excel.ImportVO;
import com.fast.start.common.excel.UploadDTO;
import com.fast.start.common.excel.handler.ImportErrorCellWriteHandler;
import com.fast.start.common.log.FastLog;
import com.fast.start.common.properties.FastFile;
import com.fast.start.exception.FileException;
import com.fast.start.utils.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 数据字典域 前端控制器
 */
@Slf4j
@Api(tags = "数据字典域接口")
@RestController
@RequestMapping("/admin/dataDictDomain")
public class DataDictDomainController extends BaseController {

    @Resource
    private DataDictDomainService dataDictionaryDomainService;

    @Resource
    private FastFile fastFile;

    @Value("${mybatis-plus.global-config.max-limit}")
    private Integer maxLimit;

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数据字典域列表")
    @FastLog(module = "获取数据字典域列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<DataDictDomain>> page(@Validated DataDictDomainQuery query) {
        return Result.success(dataDictionaryDomainService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数据字典域详情")
    @FastLog(module = "获取数据字典域详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<DataDictDomain> detail(@PathVariable Long id) {
        return Result.success(dataDictionaryDomainService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建数据字典域")
    @FastLog(module = "创建数据字典域", operateType = OperateTypeEnum.INSERT)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody DataDictDomainCreateDTO dto) {
        return Result.r(dataDictionaryDomainService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑数据字典域")
    @FastLog(module = "编辑数据字典域", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody DataDictDomainUpdateDTO dto) {
        return Result.r(dataDictionaryDomainService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除数据字典域")
    @FastLog(module = "删除数据字典域", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(dataDictionaryDomainService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除数据字典域")
    @FastLog(module = "批量删除数据字典域", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(dataDictionaryDomainService.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导入数据字典域")
    @FastLog(module = "导入数据字典域", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto) {
        Assert.notNull(dto.getFile(), "文件不能为空");
        try {
            List<DataDictDomain> list = EasyExcel.read(dto.getFile().getInputStream())
                    .head(DataDictDomain.class)
                    .excelType(FileUtil.getExcelType(dto.getFile()))
                    .sheet()
                    .doReadSync();
            List<ImportExcelError> errors = new ArrayList<>();
            List<DataDictDomain> errorList = new ArrayList<>();
            // 导入Excel处理
            dataDictionaryDomainService.importExcel(list, errorList, errors);
            String filePath = "";
            if (!errorList.isEmpty()) {
                // 将错误数据写到Excel文件
                filePath = FileUtil.getFullPath(fastFile.getFilePath(), "数据字典域导入错误信息");
                EasyExcel.write(filePath).head(DataDictDomain.class)
                        .sheet().registerWriteHandler(new ImportErrorCellWriteHandler(errors))
                        .doWrite(errorList);
                filePath = FileUtil.getMapPath(filePath, fastFile.getFilePath(), fastFile.getFileMapPath());
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

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出数据字典域")
    @FastLog(module = "导出数据字典域", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody DataDictDomainQuery query) {
        String filePath = FileUtil.getFullPath(fastFile.getExcelPath(), "数据字典域");
        query.setPageNum(1);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(filePath, DataDictDomain.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据字典域").build();
        while (true) {
            IPage<DataDictDomain> page = dataDictionaryDomainService.selectPage(query);
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

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "下载数据字典域导入模板")
    @FastLog(module = "下载数据字典域导入模板", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void downloadTemplate(HttpServletResponse response) {
        String filePath = FileUtil.getFullPath(fastFile.getExcelPath(), "数据字典域导入模板");
        EasyExcel.write(filePath, DataDictDomain.class)
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("数据字典域导入模板")
                .doWrite(new ArrayList<>());
        try {
            FileUtil.downloadAndDelete(filePath, response);
        } catch (IOException e) {
            log.error("下载数据字典域导入模板失败 e -> ", e);
            throw new FileException("下载数据字典域导入模板失败");
        }
    }
}
