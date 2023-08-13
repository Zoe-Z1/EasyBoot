package com.fast.start.admin.redis.controller;

import com.fast.start.admin.operationLog.enums.OperateTypeEnum;
import com.fast.start.admin.redis.entity.RedisVO;
import com.fast.start.admin.redis.service.RedisService;
import com.fast.start.common.base.Result;
import com.fast.start.common.log.FastLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zoe
 * @date 2023/8/12
 * @description 服务器监控接口
 */
@Slf4j
@Api(tags = "缓存监控接口")
@RestController
@RequestMapping("/admin/redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "获取缓存详情")
    @FastLog(module = "获取缓存详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail")
    public Result<RedisVO> detail() {
        return Result.success(redisService.getRedisInfo());
    }

}
