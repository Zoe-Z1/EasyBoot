package com.easy.boot.common.excel.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Data;

/**
 * @author zoe
 * @date 2023/8/9
 * @description
 */
@Data
@Builder
@Schema(title = "Excel导入返回对象", description = "Excel导入返回对象")
public class ImportVO {

    @Schema(title = "导入条数")
    private Integer count;

    @Schema(title = "错误条数")
    private Integer errorCount;

    @Schema(title = "错误文件Base64")
    private String errorBase64;
}
