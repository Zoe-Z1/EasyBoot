package cn.easy.boot.admin.dataDictDomain.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomain;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainQuery;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainUpdateDTO;
import cn.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot.common.base.BaseController;
import cn.easy.boot.common.base.Result;
import cn.easy.boot.common.log.EasyLog;
import cn.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot.admin.dataDictDomain.entity.DataDictDomainCreateDTO;
import cn.easy.boot.admin.dataDictDomain.service.IDataDictDomainService;
import cn.easy.boot.common.excel.entity.ImportExcelError;
import cn.easy.boot.common.excel.entity.ImportVO;
import cn.easy.boot.common.excel.entity.UploadDTO;
import cn.easy.boot.common.excel.handler.ExportExcelErrorCellWriteHandler;
import cn.easy.boot.common.excel.handler.ExportExcelSelectCellWriteHandler;
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


    @SaCheckPermission(value = "system:data:dict:domain:all")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取所有数据字典域")
    @EasyLog(module = "获取所有数据字典域", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/all")
    public Result<List<DataDictDomain>> all() {
        return Result.success(dataDictDomainService.selectAll());
    }

    @SaCheckPermission(value = "system:data:dict:domain:page")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "分页获取数据字典域列表")
    @EasyLog(module = "分页获取数据字典域列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<DataDictDomain>> page(@Validated DataDictDomainQuery query) {
        return Result.success(dataDictDomainService.selectPage(query));
    }

    @SaCheckPermission(value = "system:data:dict:domain:detail")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数据字典域详情")
    @EasyLog(module = "获取数据字典域详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<DataDictDomain> detail(@PathVariable Long id) {
        return Result.success(dataDictDomainService.detail(id));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:data:dict:domain:create")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建数据字典域")
    @EasyLog(module = "创建数据字典域", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody DataDictDomainCreateDTO dto) {
        return Result.r(dataDictDomainService.create(dto));
    }

    @SaCheckPermission(value = "system:data:dict:domain:update")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑数据字典域")
    @EasyLog(module = "编辑数据字典域", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody DataDictDomainUpdateDTO dto) {
        return Result.r(dataDictDomainService.updateById(dto));
    }

    @SaCheckPermission(value = "system:data:dict:domain:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除数据字典域")
    @EasyLog(module = "删除数据字典域", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(dataDictDomainService.deleteById(id));
    }

    @SaCheckPermission(value = "system:data:dict:domain:batch:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除数据字典域")
    @EasyLog(module = "批量删除数据字典域", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(dataDictDomainService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "system:data:dict:domain:import")
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
                    .registerWriteHandler(new ExportExcelSelectCellWriteHandler(DataDictDomain.class))
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
    @SaCheckPermission(value = "system:data:dict:domain:export")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出数据字典域")
    @EasyLog(module = "导出数据字典域", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody DataDictDomainQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), DataDictDomain.class)
                .build();
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

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:data:dict:domain:download")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "下载数据字典域导入模板")
    @EasyLog(module = "下载数据字典域导入模板", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/download")
    public void downloadTemplate() throws IOException {
        EasyExcel.write(response.getOutputStream(), DataDictDomain.class)
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(DataDictDomain.class))
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("数据字典域导入模板")
                .doWrite(new ArrayList<>());
    }
}
