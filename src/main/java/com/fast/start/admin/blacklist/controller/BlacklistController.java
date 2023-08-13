package com.fast.start.admin.blacklist.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fast.start.admin.blacklist.entity.Blacklist;
import com.fast.start.admin.blacklist.entity.BlacklistCreateDTO;
import com.fast.start.admin.blacklist.entity.BlacklistQuery;
import com.fast.start.admin.blacklist.entity.BlacklistUpdateDTO;
import com.fast.start.admin.blacklist.service.IBlacklistService;
import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.common.base.BaseController;
import com.fast.start.common.base.Result;
import com.fast.start.common.log.FastLog;
import com.fast.start.common.properties.FastFile;
import com.fast.start.exception.FileException;
import com.fast.start.utils.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 黑名单 前端控制器
 */
@Slf4j
@Api(tags = "黑名单接口")
@RestController
@RequestMapping("/admin/blacklist")
public class BlacklistController extends BaseController {

    @Resource
    private IBlacklistService blacklistService;

    @Resource
    private FastFile fastFile;

    @Value("${mybatis-plus.global-config.max-limit}")
    private Integer maxLimit;

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取黑名单列表")
    @FastLog(module = "获取黑名单列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Blacklist>> page(@Validated BlacklistQuery query) {
        return Result.success(blacklistService.selectPage(query));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取黑名单详情")
    @FastLog(module = "获取黑名单详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail/{id}")
    public Result<Blacklist> detail(@PathVariable Long id) {
        return Result.success(blacklistService.detail(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建黑名单")
    @FastLog(module = "创建黑名单", operateType = OperateTypeEnum.INSERT)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody BlacklistCreateDTO dto) {
        return Result.r(blacklistService.create(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑黑名单")
    @FastLog(module = "编辑黑名单", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody BlacklistUpdateDTO dto) {
        return Result.r(blacklistService.updateById(dto));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "取消拉黑")
    @FastLog(module = "取消拉黑", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(blacklistService.deleteById(id));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量取消拉黑")
    @FastLog(module = "批量取消拉黑", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel/{ids}")
    public Result batchDel(@PathVariable List<Long> ids) {
        return Result.r(blacklistService.deleteBatchByIds(ids));
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "导出黑名单")
    @FastLog(module = "导出黑名单", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody BlacklistQuery query) {
        String filePath = FileUtil.getFullPath(fastFile.getExcelPath(), "黑名单");
        query.setPageNum(1);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(filePath, Blacklist.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("黑名单").build();
        while (true) {
            IPage<Blacklist> page = blacklistService.selectPage(query);
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
}
