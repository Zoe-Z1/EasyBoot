package com.easy.boot.admin.sse.controller;

import com.easy.boot.admin.sse.service.ISseService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;


/**
 * @author zoe
 * @date 2023/8/12
 * @description 服务器监控接口
 */
@Slf4j
@Api(tags = "SSE订阅接口")
@RestController
@RequestMapping("/admin/sse")
public class SseController {

    @Resource
    private ISseService sseService;

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "SSE连接")
    @GetMapping(value = "/connect/{sessionKey}")
    public SseEmitter connect(@PathVariable String sessionKey) {
        return sseService.connect(sessionKey);
    }

    @ApiOperationSupport(author = "zoe")
    @ApiOperation(value = "SSE关闭连接")
    @GetMapping("/close/{sessionKey}")
    public void close(@PathVariable String sessionKey) {
        sseService.close(sessionKey);
    }

}
