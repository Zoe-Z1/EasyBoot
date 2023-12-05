package cn.easy.boot3.admin.notice.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.notice.service.INoticeService;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.easy.boot3.admin.notice.entity.Notice;
import cn.easy.boot3.admin.notice.entity.NoticeCreateDTO;
import cn.easy.boot3.admin.notice.entity.NoticeQuery;
import cn.easy.boot3.admin.notice.entity.NoticeUpdateDTO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告接口
 */
@Slf4j
@Tag(name = "公告接口")
@RestController
@RequestMapping("/admin/notice")
public class NoticeController extends BaseController {

    @Resource
    private INoticeService noticeService;

    @SaCheckPermission(value = "system:notice:new")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取最新公告")
    @EasyLog(module = "获取最新公告", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/new")
    public Result<Notice> news() {
        return Result.success(noticeService.news());
    }

    @SaCheckPermission(value = "system:notice:page")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "分页获取公告列表")
    @EasyLog(module = "分页获取公告列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Notice>> page(@Validated NoticeQuery query) {
        return Result.success(noticeService.selectPage(query));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:notice:create")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "创建公告")
    @EasyLog(module = "创建公告", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody NoticeCreateDTO dto) {
        return Result.r(noticeService.create(dto));
    }

    @SaCheckPermission(value = "system:notice:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑公告")
    @EasyLog(module = "编辑公告", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody NoticeUpdateDTO dto) {
        return Result.r(noticeService.updateById(dto));
    }

    @SaCheckPermission(value = "system:notice:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "删除公告")
    @EasyLog(module = "删除公告", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(noticeService.deleteById(id));
    }

    @SaCheckPermission(value = "system:notice:batch:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "批量删除公告")
    @EasyLog(module = "批量删除公告", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(noticeService.deleteBatchByIds(ids));
    }

}
