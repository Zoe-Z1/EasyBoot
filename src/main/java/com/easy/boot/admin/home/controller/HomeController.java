package com.easy.boot.admin.home.controller;

import com.easy.boot.admin.home.entity.HomeHandlerTimeVO;
import com.easy.boot.admin.home.entity.HomeHotsApiVO;
import com.easy.boot.admin.home.entity.HomeNumberVO;
import com.easy.boot.admin.home.entity.HomeUserAnalysisVO;
import com.easy.boot.admin.home.service.HomeService;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/7/23
 * @description
 */
@Slf4j
@Api(tags = "首页接口")
@Validated
@RestController
@RequestMapping("/admin/home")
public class HomeController {

    @Resource
    private HomeService homeService;


    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取数量统计")
    @EasyLog(module = "获取数量统计", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/statistics/number")
    public Result<HomeNumberVO> number() {
        return Result.success(homeService.getHomeNumber());
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取用户分析统计")
    @EasyLog(module = "获取用户分析统计", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/statistics/userAnalysis")
    public Result<HomeUserAnalysisVO> userAnalysis() {
        return Result.success(homeService.getUserAnalysis());
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取热点接口统计")
    @EasyLog(module = "获取热点接口统计", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/statistics/hotsApi")
    public Result<HomeHotsApiVO> hotsApi() {
        return Result.success(homeService.getHotsApi());
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取接口处理时长统计")
    @EasyLog(module = "获取接口处理时长统计", operateType = OperateTypeEnum.SELECT)
    @GetMapping(value = "/statistics/handlerTime")
    public Result<HomeHandlerTimeVO> handlerTime() {
        return Result.success(homeService.getHandlerTime());
    }

}
