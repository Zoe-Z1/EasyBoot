package cn.easy.boot3.admin.generateColumn.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.generateColumn.entity.GenerateColumn;
import cn.easy.boot3.admin.generateColumn.entity.GenerateColumnQuery;
import cn.easy.boot3.admin.generateColumn.service.IGenerateColumnService;
import cn.easy.boot3.common.base.BaseController;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import cn.easy.boot3.admin.generateColumn.entity.GenerateColumnUpdateDTO;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
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
 * @date 2023/09/15
 * @description 代码生成列配置接口
 */
@Slf4j
@Tag(name = "代码生成列配置接口")
@RestController
@RequestMapping("/admin/generateColumn")
public class GenerateColumnController extends BaseController {

    @Resource
    private IGenerateColumnService generateColumnService;


    @SaCheckPermission(value = "dev:gen:column:list")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取代码生成Table中的列")
    @EasyLog(module = "获取代码生成Table中的列", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/list")
    public Result<List<GenerateColumn>> list(@Validated GenerateColumnQuery query) {
        return Result.success(generateColumnService.selectList(query));
    }

    @SaCheckPermission(value = "dev:gen:column:update")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "编辑代码生成列配置")
    @EasyLog(module = "编辑代码生成列配置", operateType = OperateTypeEnum.UPDATE)
    @PostMapping(value = "/update")
    public Result update(@Validated @RequestBody List<GenerateColumnUpdateDTO> dto) {
        generateColumnService.updateByTableName(dto);
        return Result.success();
    }

    @SaCheckPermission(value = "dev:gen:column:del")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "重置代码生成列配置")
    @EasyLog(module = "重置代码生成列配置", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/delete/{tableName}")
    public Result delete(@PathVariable String tableName) {
        generateColumnService.deleteByTableName(tableName);
        return Result.success();
    }

}
