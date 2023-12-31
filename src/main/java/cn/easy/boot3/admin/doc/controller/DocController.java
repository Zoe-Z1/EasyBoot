package cn.easy.boot3.admin.doc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.easy.boot3.admin.doc.entity.DocVO;
import cn.easy.boot3.admin.operationLog.enums.OperateTypeEnum;
import cn.easy.boot3.common.base.Result;
import cn.easy.boot3.common.log.EasyLog;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zoe
 * @date 2023/10/22
 * @description 接口文档接口
 */
@Slf4j
@Tag(name = "接口文档接口")
@RestController
@RequestMapping("/admin/doc")
public class DocController {

    @Value("${knife4j.enable}")
    private boolean enable;

    @Value("${knife4j.production:false}")
    private boolean production;

    @Value("${spring.profiles.active}")
    private String active;

    @Value("${server.port}")
    private String port;

    @SaCheckPermission(value = "dev:doc:detail")
    @ApiOperationSupport(author = "zoe")
    @Operation(summary = "获取接口文档详情")
    @EasyLog(module = "获取接口文档详情", operateType = OperateTypeEnum.SELECT)
    @GetMapping("/detail")
    public Result<DocVO> detail() {
        String dev = "dev";
        String url = null;
        boolean enable = this.enable && !this.production;
        if (dev.equals(active)) {
            url = "http://localhost:" + port + "/doc.html";
        } else {
            url = "http://easyboot.cn:" + port + "/doc.html";
        }
        DocVO vo = DocVO.builder()
                .enable(enable)
                .url(url)
                .build();
        return Result.success(vo);
    }
}
