package com.easy.boot.admin.dataDict.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.dataDict.entity.*;
import com.easy.boot.admin.dataDict.service.IDataDictService;
import com.easy.boot.admin.dataDictDomain.entity.DataDictDomain;
import com.easy.boot.admin.dataDictDomain.service.IDataDictDomainService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.entity.ImportExcelError;
import com.easy.boot.common.excel.entity.ImportVO;
import com.easy.boot.common.excel.entity.UploadDTO;
import com.easy.boot.common.excel.handler.ExportExcelErrorCellWriteHandler;
import com.easy.boot.common.excel.handler.ExportExcelSelectCellWriteHandler;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.easy.boot.exception.BusinessException;
import com.easy.boot.utils.BeanUtil;
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
import java.util.*;
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
    private IDataDictService dataDictService;

    @Resource
    private IDataDictDomainService dataDictDomainService;


    @SaCheckPermission(value = "system:data:dict:page")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "分页获取数据字典列表")
    @EasyLog(module = "分页获取数据字典列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<DataDict>> page(@Validated DataDictQuery query) {
        return Result.success(dataDictService.selectPage(query));
    }

//    @SaCheckPermission(value = "system:data:dict:all")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取全部数据字典")
    @EasyLog(module = "获取全部数据字典", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/all")
    public Result<Map<String, List<DataDict>>> all() {
        Map<String, List<DataDict>> map = dataDictDomainService.selectAll();
        return Result.success(map);
    }

//    @SaCheckPermission(value = "system:data:dict:list:code")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "使用编码获取数据字典列表")
    @EasyLog(module = "使用编码获取数据字典列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/listByCode")
    public Result<List<DataDict>> listByCode(@Validated ListQuery query) {
        return Result.success(dataDictDomainService.selectListByDomainCode(query.getCode()));
    }

    @SaCheckPermission(value = "system:data:dict:detail")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数据字典详情")
    @EasyLog(module = "获取数据字典详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<DataDict> detail(@PathVariable Long id) {
        return Result.success(dataDictService.detail(id));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:data:dict:create")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建数据字典")
    @EasyLog(module = "创建数据字典", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody DataDictCreateDTO dto) {
        return Result.r(dataDictService.create(dto));
    }

    @SaCheckPermission(value = "system:data:dict:update")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑数据字典")
    @EasyLog(module = "编辑数据字典", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody DataDictUpdateDTO dto) {
        return Result.r(dataDictService.updateById(dto));
    }

    @SaCheckPermission(value = "system:data:dict:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除数据字典")
    @EasyLog(module = "删除数据字典", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(dataDictService.deleteById(id));
    }

    @SaCheckPermission(value = "system:data:dict:batch:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除数据字典")
    @EasyLog(module = "批量删除数据字典", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(dataDictService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "system:data:dict:import")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导入数据字典")
    @EasyLog(module = "导入数据字典", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto, Long domainId) throws IOException {
        Assert.notNull(dto.getFile(), "文件不能为空");
        Assert.notNull(domainId, "数据字典域ID不能为空");
        List<DataDict> list = EasyExcel.read(dto.getFile().getInputStream())
                .head(DataDict.class)
                .sheet()
                .doReadSync();
        list.forEach(item -> item.setDomainId(domainId));
        List<ImportExcelError> errors = new ArrayList<>();
        List<DataDict> errorList = new ArrayList<>();
        // 导入Excel处理
        dataDictService.importExcel(list, errorList, errors);
        String base64 = "";
        if (!errorList.isEmpty()) {
            // 将错误数据写到Excel文件
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            EasyExcel.write(out).head(DataDict.class)
                    .sheet("数据字典导入错误信息列表")
                    .registerWriteHandler(new ExportExcelSelectCellWriteHandler(DataDict.class))
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

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:data:dict:export")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出数据字典")
    @EasyLog(module = "导出数据字典", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody DataDictQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), DataDictExcelDO.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据字典信息列表").build();
        while (true) {
            IPage<DataDict> page = dataDictService.selectPage(query);
            DataDictDomain dictDomain = dataDictDomainService.getById(query.getDomainId());
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
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:data:dict:download")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "下载数据字典导入模板")
    @EasyLog(module = "下载数据字典导入模板", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/download")
    public void downloadTemplate() throws IOException {
        EasyExcel.write(response.getOutputStream(), DataDict.class)
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(DataDict.class))
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("数据字典导入模板")
                .doWrite(new ArrayList<>());
    }
}
