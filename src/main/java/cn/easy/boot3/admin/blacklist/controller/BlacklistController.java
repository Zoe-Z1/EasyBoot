package cn.easy.boot3.admin.blacklist.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.blacklist.entity.*;
import cn.easy.boot3.admin.blacklist.service.IBlacklistService;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot3.admin.blacklist.entity.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 黑名单 前端控制器
 */
@Slf4j
@Tag(name = "黑名单接口")
@RestController
@RequestMapping("/admin/blacklist")
public class BlacklistController extends BaseController {

    @Resource
    private IBlacklistService blacklistService;


    @SaCheckPermission(value = "ops:blacklist:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取黑名单列表")
    @EasyLog(module = "分页获取黑名单列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Blacklist>> page(@Validated BlacklistQuery query) {
        return Result.success(blacklistService.selectPage(query));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "ops:blacklist:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建黑名单")
    @EasyLog(module = "创建黑名单", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody BlacklistCreateDTO dto) {
        return Result.r(blacklistService.create(dto));
    }

    @SaCheckPermission(value = "ops:blacklist:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑黑名单")
    @EasyLog(module = "编辑黑名单", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody BlacklistUpdateDTO dto) {
        return Result.r(blacklistService.updateById(dto));
    }

    @SaCheckPermission(value = "ops:blacklist:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "取消拉黑")
    @EasyLog(module = "取消拉黑", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(blacklistService.deleteById(id));
    }

    @SaCheckPermission(value = "ops:blacklist:batch:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "批量取消拉黑")
    @EasyLog(module = "批量取消拉黑", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(blacklistService.deleteBatchByIds(ids));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "ops:blacklist:export")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "导出黑名单")
    @EasyLog(module = "导出黑名单", operateType = OperateTypeEnum.EXPORT)
    @PostMapping("/export")
    public void exportExcel(@Validated @RequestBody BlacklistQuery query) throws IOException {
        query.setPageNum(1L);
        query.setPageSize(maxLimit);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), BlacklistExportDO.class)
                .build();
        WriteSheet writeSheet = EasyExcel.writerSheet("黑名单信息列表").build();
        while (true) {
            IPage<Blacklist> page = blacklistService.selectPage(query);
            List<BlacklistExportDO> dos = page.getRecords().stream().map(item -> {
                BlacklistExportDO exportDO = BlacklistExportDO.builder()
                        .type(item.getType())
                        .startTime(item.getCreateTime())
                        .createUsername(item.getCreateUsername())
                        .build();
                if (item.getEndTime() == 0) {
                    exportDO.setEndTime("永久拉黑");
                } else {
                    exportDO.setEndTime(DateUtil.date(item.getEndTime()).toString());
                }
                if (item.getType() == 1) {
                    exportDO.setRelevanceData("拉黑账号：" + item.getRelevanceData());
                } else {
                    exportDO.setRelevanceData("拉黑IP：" + item.getRelevanceData());
                }
                return exportDO;
            }).collect(Collectors.toList());
            build.write(dos, writeSheet);
            if (page.getCurrent() >= page.getPages()) {
                break;
            }
            query.setPageNum(query.getPageNum() + 1);
        }
        build.finish();
    }
}
