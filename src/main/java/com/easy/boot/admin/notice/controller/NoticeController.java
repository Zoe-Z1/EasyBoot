package com.easy.boot.admin.notice.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.easy.boot.admin.notice.entity.Notice;
import com.easy.boot.admin.notice.entity.NoticeCreateDTO;
import com.easy.boot.admin.notice.entity.NoticeQuery;
import com.easy.boot.admin.notice.entity.NoticeUpdateDTO;
import com.easy.boot.admin.notice.service.INoticeService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.BaseController;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.easy.boot.common.noRepeatSubmit.EasyNoRepeatSubmit;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告接口
 */
@Slf4j
@Api(tags = "公告接口")
@RestController
@RequestMapping("/admin/notice")
public class NoticeController extends BaseController {

    @Resource
    private INoticeService noticeService;


    @SaCheckPermission(value = "system:notice:page")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "分页获取公告列表")
    @EasyLog(module = "分页获取公告列表", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/page")
    public Result<IPage<Notice>> page(@Validated NoticeQuery query) {
        return Result.success(noticeService.selectPage(query));
    }

    @EasyNoRepeatSubmit
    @SaCheckPermission(value = "system:notice:create")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "创建公告")
    @EasyLog(module = "创建公告", operateType = OperateTypeEnum.CREATE)
    @PostMapping(value = "/create")
    public Result create(@Validated @RequestBody NoticeCreateDTO dto) {
        return Result.r(noticeService.create(dto));
    }

    @SaCheckPermission(value = "system:notice:update")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "编辑公告")
    @EasyLog(module = "编辑公告", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody NoticeUpdateDTO dto) {
        return Result.r(noticeService.updateById(dto));
    }

    @SaCheckPermission(value = "system:notice:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "删除公告")
    @EasyLog(module = "删除公告", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.r(noticeService.deleteById(id));
    }

    @SaCheckPermission(value = "system:notice:batch:del")
    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "批量删除公告")
    @EasyLog(module = "批量删除公告", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/batchDel")
    public Result batchDel(@RequestBody List<Long> ids) {
        return Result.r(noticeService.deleteBatchByIds(ids));
    }

}
