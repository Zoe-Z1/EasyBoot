//package com.easy.boot.admin.home.controller;
//
//import com.easy.boot.admin.home.entity.HomeDTO;
//import com.easy.boot.admin.home.entity.HomeVO;
//import com.easy.boot.admin.home.service.HomeService;
//import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
//import com.easy.boot.common.base.Result;
//import com.easy.boot.common.log.EasyLog;
//import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * @author zoe
// * @date 2023/7/21
// * @description
// */
//@Slf4j
//@Api(tags = "登录接口")
//@Validated
//@RestController
//@RequestMapping("/admin")
//public class HomeController {
//
//    @Resource
//    private HomeService homeService;
//
//
//    @ApiOperationSupport(author = "zoe")
//    @ApiOperation(value = "首页")
//    @EasyLog(module = "首页", operateType = OperateTypeEnum.SELECT)
//    @GetMapping(value = "/home")
//    public Result<HomeVO> login(@Validated HomeDTO dto) {
//        return Result.success(homeService.getStatistics(dto));
//    }
//
//}
