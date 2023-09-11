package com.easy.boot.admin.sysConfig.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.sysConfig.entity.*;
import com.easy.boot.admin.sysConfig.service.ISysConfigService;
import com.easy.boot.admin.sysConfigDomain.entity.SysConfigDomain;
import com.easy.boot.admin.sysConfigDomain.service.ISysConfigDomainService;
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
 * @date 2023/07/29
 * @description 系统配置 前端控制器
 */
@Slf4j
@Api(tags = "系统配置接口")
@RestController
@RequestMapping("/admin/sysConfig")
public class SysConfigController extends BaseController {

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ISysConfigDomainService sysConfigDomainService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取系统配置列表")
    @EasyLog(module = "获取系统配置列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<SysConfig>> page(@Validated SysConfigQuery query) {
        return Result.success(sysConfigService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "使用编码获取系统配置列表")
    @EasyLog(module = "使用编码获取系统配置列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/listByCode")
    public Result<List<SysConfig>> listByCode(@Validated ListQuery query) {
        return Result.success(sysConfigDomainService.selectListByDomainCode(query.getCode()));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取系统配置详情")
    @EasyLog(module = "获取系统配置详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<SysConfig> detail(@PathVariable Long id) {
        return Result.success(sysConfigService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建系统配置")
    @EasyLog(module = "创建系统配置", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody SysConfigCreateDTO dto) {
        return Result.r(sysConfigService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑系统配置")
    @EasyLog(module = "编辑系统配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody SysConfigUpdateDTO dto) {
        return Result.r(sysConfigService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除系统配置")
    @EasyLog(module = "删除系统配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(sysConfigService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除系统配置")
    @EasyLog(module = "批量删除系统配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(sysConfigService.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导入系统配置")
    @EasyLog(module = "导入系统配置", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto, Long domainId) {
        Assert.notNull(dto.getFile(), "文件不能为空");
        Assert.notNull(domainId, "系统配置域ID不能为空");
        try {
            List<SysConfig> list = EasyExcel.read(dto.getFile().getInputStream())
                    .head(SysConfig.class)
                    .excelType(FileUtil.getExcelType(dto.getFile()))
                    .sheet()
                    .doReadSync();
            list.forEach(item -> item.setDomainId(domainId));
            List<ImportExcelError> errors = new ArrayList<>();
            List<SysConfig> errorList = new ArrayList<>();
            // 导入Excel处理
            sysConfigService.importExcel(list, errorList, errors);
            String filePath = "";
            if (!errorList.isEmpty()) {
                // 将错误数据写到Excel文件
                filePath = FileUtil.getFullPath(easyFile.getFilePath(), "系统配置导入错误信息");
                EasyExcel.write(filePath).head(SysConfig.class)
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
    @ApiOperation(value = "导出系统配置")
    @EasyLog(module = "导出系统配置", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody SysConfigQuery query) {
        String filePath = FileUtil.getFullPath(easyFile.getExcelPath(), "系统配置");
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(filePath, SysConfigExcelDO.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("系统配置").build();
        while (true) {
            IPage<SysConfig> page = sysConfigService.selectPage(query);
            SysConfigDomain configDomain = sysConfigDomainService.getById(query.getDomainId());
            if (configDomain == null) {
                throw new BusinessException("系统配置域不存在");
            }
            List<SysConfigExcelDO> list = page.getRecords().stream()
                    .map(item -> {
                        SysConfigExcelDO excelDO = BeanUtil.copyBean(item, SysConfigExcelDO.class);
                        excelDO.setDomainCode(configDomain.getCode());
                        return excelDO;
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
    @ApiOperation(value = "下载系统配置导入模板")
    @EasyLog(module = "下载系统配置导入模板", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void downloadTemplate() {
        String filePath = FileUtil.getFullPath(easyFile.getExcelPath(), "系统配置导入模板");
        EasyExcel.write(filePath, SysConfig.class)
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("系统配置导入模板")
                .doWrite(new ArrayList<>());
        try {
            FileUtil.downloadAndDelete(filePath, response);
        } catch (IOException e) {
            log.error("下载系统配置导入模板失败 e -> ", e);
            throw new FileException("下载系统配置导入模板失败");
        }
    }
}
