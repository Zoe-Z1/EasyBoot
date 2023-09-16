package com.easy.boot.admin.dataDictDomain.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.dataDictDomain.entity.DataDictDomain;
import com.easy.boot.admin.dataDictDomain.entity.DataDictDomainCreateDTO;
import com.easy.boot.admin.dataDictDomain.entity.DataDictDomainQuery;
import com.easy.boot.admin.dataDictDomain.entity.DataDictDomainUpdateDTO;
import com.easy.boot.admin.dataDictDomain.service.IDataDictDomainService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.entity.ImportExcelError;
import com.easy.boot.common.excel.entity.ImportVO;
import com.easy.boot.common.excel.entity.UploadDTO;
import com.easy.boot.common.excel.handler.ExportExcelErrorCellWriteHandler;
import com.easy.boot.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
    private IDataDictDomainService dataDictDomainService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数据字典域列表")
    @EasyLog(module = "获取数据字典域列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<DataDictDomain>> page(@Validated DataDictDomainQuery query) {
        return Result.success(dataDictDomainService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数据字典域详情")
    @EasyLog(module = "获取数据字典域详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<DataDictDomain> detail(@PathVariable Long id) {
        return Result.success(dataDictDomainService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建数据字典域")
    @EasyLog(module = "创建数据字典域", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody DataDictDomainCreateDTO dto) {
        return Result.r(dataDictDomainService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑数据字典域")
    @EasyLog(module = "编辑数据字典域", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody DataDictDomainUpdateDTO dto) {
        return Result.r(dataDictDomainService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除数据字典域")
    @EasyLog(module = "删除数据字典域", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(dataDictDomainService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除数据字典域")
    @EasyLog(module = "批量删除数据字典域", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(dataDictDomainService.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导入数据字典域")
    @EasyLog(module = "导入数据字典域", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto) throws IOException {
        Assert.notNull(dto.getFile(), "文件不能为空");
        List<DataDictDomain> list = EasyExcel.read(dto.getFile().getInputStream())
                .head(DataDictDomain.class)
                .sheet()
                .doReadSync();
        List<ImportExcelError> errors = new ArrayList<>();
        List<DataDictDomain> errorList = new ArrayList<>();
        // 导入Excel处理
        dataDictDomainService.importExcel(list, errorList, errors);
        String base64 = "";
        if (!errorList.isEmpty()) {
            // 将错误数据写到Excel文件
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            EasyExcel.write(out).head(DataDictDomain.class)
                    .sheet("数据字典域导入错误信息列表")
                    .registerWriteHandler(new ExportExcelErrorCellWriteHandler(errors))
                    .doWrite(errorList);
            base64 = Base64.getEncoder().encodeToString(out.toByteArray());
        }
        ImportVO importVO = ImportVO.builder()
                .count(list.size())
                .errorCount(errorList.size())
                .errorBase64(base64)
                .build();
        return Result.success(importVO);
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出数据字典域")
    @EasyLog(module = "导出数据字典域", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody DataDictDomainQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), DataDictDomain.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据字典域信息列表").build();
        while (true) {
            IPage<DataDictDomain> page = dataDictDomainService.selectPage(query);
            build.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "下载数据字典域导入模板")
    @EasyLog(module = "下载数据字典域导入模板", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void downloadTemplate() throws IOException {
        EasyExcel.write(response.getOutputStream(), DataDictDomain.class)
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("数据字典域导入模板")
                .doWrite(new ArrayList<>());
    }
}
