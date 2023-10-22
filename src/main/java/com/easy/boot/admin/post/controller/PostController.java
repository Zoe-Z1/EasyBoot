package com.easy.boot.admin.post.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.post.entity.Post;
import com.easy.boot.admin.post.entity.PostCreateDTO;
import com.easy.boot.admin.post.entity.PostQuery;
import com.easy.boot.admin.post.entity.PostUpdateDTO;
import com.easy.boot.admin.post.service.IPostService;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.excel.entity.ImportExcelError;
import com.easy.boot.common.excel.entity.ImportVO;
import com.easy.boot.common.excel.entity.UploadDTO;
import com.easy.boot.common.excel.handler.ExportExcelErrorCellWriteHandler;
import com.easy.boot.common.excel.handler.ExportExcelSelectCellWriteHandler;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
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



    @SaCheckPermission(value = "system:post:all")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取全部岗位")
    @EasyLog(module = "获取全部岗位", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/all")
    public Result<List<Post>> all() {
        return Result.success(postService.selectAll());
    }

    @SaCheckPermission(value = "system:post:page")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "分页获取岗位列表")
    @EasyLog(module = "分页获取岗位列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Post>> page(@Validated PostQuery query) {
        return Result.success(postService.selectPage(query));
    }

    @SaCheckPermission(value = "system:post:detail")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取岗位详情")
    @EasyLog(module = "获取岗位详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<Post> detail(@PathVariable Long id) {
        return Result.success(postService.detail(id));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:post:create")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建岗位")
    @EasyLog(module = "创建岗位", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody PostCreateDTO dto) {
        return Result.r(postService.create(dto));
    }

    @SaCheckPermission(value = "system:post:update")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑岗位")
    @EasyLog(module = "编辑岗位", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody PostUpdateDTO dto) {
        return Result.r(postService.updateById(dto));
    }

    @SaCheckPermission(value = "system:post:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除岗位")
    @EasyLog(module = "删除岗位", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(postService.deleteById(id));
    }

    @SaCheckPermission(value = "system:post:batch:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除岗位")
    @EasyLog(module = "批量删除岗位", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(postService.deleteBatchByIds(ids));
    }

    @SaCheckPermission(value = "system:post:import")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导入岗位")
    @EasyLog(module = "导入岗位", operateType = OperateTypeEnum.IMPORT)
    @PostMapping("/import")
    public Result<ImportVO> importExcel(UploadDTO dto) throws IOException {
        Assert.notNull(dto.getFile(), "文件不能为空");
        List<Post> list = EasyExcel.read(dto.getFile().getInputStream())
                .head(Post.class)
                .sheet()
                .doReadSync();
        List<ImportExcelError> errors = new ArrayList<>();
        List<Post> errorList = new ArrayList<>();
        // 导入Excel处理
        postService.importExcel(list, errorList, errors);
        String base64 = "";
        if (!errorList.isEmpty()) {
            // 将错误数据写到Excel文件
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            EasyExcel.write(out).head(Post.class)
                    .sheet("岗位导入错误信息列表")
                    .registerWriteHandler(new ExportExcelSelectCellWriteHandler(Post.class))
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
    @SaCheckPermission(value = "system:post:export")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出岗位")
    @EasyLog(module = "导出岗位", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody PostQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), Post.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("岗位信息列表").build();
        while (true) {
            IPage<Post> page = postService.selectPage(query);
            build.write(page.getRecords(), writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:post:download")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "下载岗位导入模板")
    @EasyLog(module = "下载岗位导入模板", operateType = OperateTypeEnum.DOWNLOAD)
    @PostMapping("/download")
    public void downloadTemplate() throws IOException {
        EasyExcel.write(response.getOutputStream(), Post.class)
                .registerWriteHandler(new ExportExcelSelectCellWriteHandler(Post.class))
                .excludeColumnFieldNames(Collections.singletonList("createTime"))
                .sheet("岗位导入模板")
                .doWrite(new ArrayList<>());
    }
}
