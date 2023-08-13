package com.fast.start.admin.post.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.admin.post.entity.Post;
import com.fast.start.admin.post.entity.PostCreateDTO;
import com.fast.start.admin.post.entity.PostQuery;
import com.fast.start.admin.post.entity.PostUpdateDTO;
import com.fast.start.admin.post.service.IPostService;
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
 * @date 2023/07/29
 * @description 岗位 前端控制器
 */
@Slf4j
@Api(tags = "岗位接口")
@RestController
@RequestMapping("/admin/post")
public class PostController extends BaseController {

    @Resource
    private IPostService postService;

    @Resource
    private FastFile fastFile;

    @Value("${mybatis-plus.global-config.max-limit}")
    private Integer maxLimit;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取岗位列表")
    @FastLog(module = "获取岗位列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Post>> page(@Validated PostQuery query) {
        return Result.success(postService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取岗位详情")
    @FastLog(module = "获取岗位详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<Post> detail(@PathVariable Long id) {
        return Result.success(postService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建岗位")
    @FastLog(module = "创建岗位", operateType = OperateTypeEnum.INSERT)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody PostCreateDTO dto) {
        return Result.r(postService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑岗位")
    @FastLog(module = "编辑岗位", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody PostUpdateDTO dto) {
        return Result.r(postService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除岗位")
    @FastLog(module = "删除岗位", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(postService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除岗位")
    @FastLog(module = "批量删除岗位", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(postService.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导入岗位")
    @FastLog(module = "导入岗位", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto) {
        Assert.notNull(dto.getFile(), "文件不能为空");
        try {
            List<Post> list = EasyExcel.read(dto.getFile().getInputStream())
                    .head(Post.class)
                    .excelType(FileUtil.getExcelType(dto.getFile()))
                    .sheet()
                    .doReadSync();
            List<ImportExcelError> errors = new ArrayList<>();
            List<Post> errorList = new ArrayList<>();
            // 导入Excel处理
            postService.importExcel(list, errorList, errors);
            String filePath = "";
            if (!errorList.isEmpty()) {
                // 将错误数据写到Excel文件
                filePath = FileUtil.getFullPath(fastFile.getFilePath(), "岗位导入错误信息");
                EasyExcel.write(filePath).head(Post.class)
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
    @ApiOperation(value = "导出岗位")
    @FastLog(module = "导出岗位", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody PostQuery query) {
        String filePath = FileUtil.getFullPath(fastFile.getExcelPath(), "岗位");
        query.setPageNum(1);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(filePath, Post.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("岗位").build();
        while (true) {
            IPage<Post> page = postService.selectPage(query);
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
    @ApiOperation(value = "下载岗位导入模板")
    @FastLog(module = "下载岗位导入模板", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void downloadTemplate(HttpServletResponse response) {
        String filePath = FileUtil.getFullPath(fastFile.getExcelPath(), "岗位导入模板");
        EasyExcel.write(filePath, Post.class)
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("岗位导入模板")
                .doWrite(new ArrayList<>());
        try {
            FileUtil.downloadAndDelete(filePath, response);
        } catch (IOException e) {
            log.error("下载岗位导入模板失败 e -> ", e);
            throw new FileException("下载岗位导入模板失败");
        }
    }
}
