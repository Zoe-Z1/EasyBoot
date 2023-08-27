package com.easy.boot.admin.dataDict.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.dataDict.entity.*;
import com.easy.boot.admin.dataDict.service.IDataDictService;
import com.easy.boot.admin.dataDictDomain.entity.DataDictDomain;
import com.easy.boot.admin.dataDictDomain.service.DataDictDomainService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.ImportExcelError;
import com.easy.boot.common.excel.ImportVO;
import com.easy.boot.common.excel.UploadDTO;
import com.easy.boot.common.excel.handler.ImportErrorCellWriteHandler;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.exception.FileException;
import com.easy.boot.utils.BeanUtil;
import com.easy.boot.utils.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 数据字典 前端控制器
 */
@Slf4j
@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/dataDict")
public class DataDictController extends BaseController {

    @Resource
    private IDataDictService dataDictionaryService;

    @Resource
    private DataDictDomainService dataDictionaryDomainService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数据字典列表")
    @EasyLog(module = "获取数据字典列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<DataDict>> page(@Validated DataDictQuery query) {
        return Result.success(dataDictionaryService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "使用编码获取数据字典列表")
    @EasyLog(module = "使用编码获取数据字典列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/listByCode")
    public Result<List<DataDict>> listByCode(@Validated ListQuery query) {
        return Result.success(dataDictionaryDomainService.selectListByDomainCode(query.getCode()));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数据字典详情")
    @EasyLog(module = "获取数据字典详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<DataDict> detail(@PathVariable Long id) {
        return Result.success(dataDictionaryService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建数据字典")
    @EasyLog(module = "创建数据字典", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody DataDictCreateDTO dto) {
        return Result.r(dataDictionaryService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑数据字典")
    @EasyLog(module = "编辑数据字典", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody DataDictUpdateDTO dto) {
        return Result.r(dataDictionaryService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除数据字典")
    @EasyLog(module = "删除数据字典", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{ids}")
    public Result delete(@PathVariable Long id) {
        return Result.r(dataDictionaryService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除数据字典")
    @EasyLog(module = "批量删除数据字典", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(dataDictionaryService.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导入数据字典")
    @EasyLog(module = "导入数据字典", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto, Long domainId) {
        Assert.notNull(dto.getFile(), "文件不能为空");
        Assert.notNull(domainId, "数据字典域ID不能为空");
        try {
            List<DataDict> list = EasyExcel.read(dto.getFile().getInputStream())
                    .head(DataDict.class)
                    .excelType(FileUtil.getExcelType(dto.getFile()))
                    .sheet()
                    .doReadSync();
            list.forEach(item -> item.setDomainId(domainId));
            List<ImportExcelError> errors = new ArrayList<>();
            List<DataDict> errorList = new ArrayList<>();
            // 导入Excel处理
            dataDictionaryService.importExcel(list, errorList, errors);
            String filePath = "";
            if (!errorList.isEmpty()) {
                // 将错误数据写到Excel文件
                filePath = FileUtil.getFullPath(easyFile.getFilePath(), "数据字典导入错误信息");
                EasyExcel.write(filePath).head(DataDict.class)
                        .sheet().registerWriteHandler(new ImportErrorCellWriteHandler(errors))
                        .doWrite(errorList);
                filePath = FileUtil.getMapPath(filePath, easyFile.getFilePath(), easyFile.getFileMapPath());
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
    @ApiOperation(value = "导出数据字典")
    @EasyLog(module = "导出数据字典", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody DataDictQuery query) {
        String filePath = FileUtil.getFullPath(easyFile.getExcelPath(), "数据字典");
        query.setPageNum(1);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(filePath, DataDictExcelDO.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据字典").build();
        while (true) {
            IPage<DataDict> page = dataDictionaryService.selectPage(query);
            DataDictDomain dictDomain = dataDictionaryDomainService.getById(query.getDomainId());
            if (dictDomain == null) {
                throw new BusinessException("数据字典域不存在");
            }
            List<DataDictExcelDO> list = page.getRecords().stream()
                    .map(item -> {
                        DataDictExcelDO dictExcelDO = BeanUtil.copyBean(item, DataDictExcelDO.class);
                        dictExcelDO.setDomainCode(dictDomain.getCode());
                        return dictExcelDO;
                    }).collect(Collectors.toList());
            build.write(list, writeSheet);
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
    @ApiOperation(value = "下载数据字典导入模板")
    @EasyLog(module = "下载数据字典导入模板", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void downloadTemplate() {
        String filePath = FileUtil.getFullPath(easyFile.getExcelPath(), "数据字典导入模板");
        EasyExcel.write(filePath, DataDict.class)
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("数据字典导入模板")
                .doWrite(new ArrayList<>());
        try {
            FileUtil.downloadAndDelete(filePath, response);
        } catch (IOException e) {
            log.error("下载数据字典导入模板失败 e -> ", e);
            throw new FileException("下载数据字典导入模板失败");
        }
    }
}
