package com.easy.boot.admin.redis.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.easy.boot.admin.operationLog.enums.OperateTypeEnum;
import com.easy.boot.admin.redis.entity.RedisVO;
import com.easy.boot.admin.redis.service.RedisService;
import com.easy.boot.common.base.Result;
import com.easy.boot.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zoe
 * @date 2023/8/12
 * @description 服务器监控接口
 */
@Slf4j
@Tag(name = "Redis监控接口")
@RestController
@RequestMapping("/admin/redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    @SaCheckPermission(value = "ops:redis:detail")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取Redis详情")
    @EasyLog(module = "获取缓存详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail")
    public Result<RedisVO> detail() {
        return Result.success(redisService.getRedisInfo());
    }

}
