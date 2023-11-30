package com.easy.boot3.common.excel.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zoe
 * @date 2023/8/9
 * @description
 */
@Data
@Builder
@Schema(title = "Excel导入请求对象", description = "Excel导入请求对象")
public class UploadDTO {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "文件")
    @JSONField(serialize = false)
    private MultipartFile file;
}
