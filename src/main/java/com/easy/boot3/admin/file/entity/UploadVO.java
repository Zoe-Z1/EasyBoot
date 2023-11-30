package com.easy.boot3.admin.file.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/6
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "UploadVO对象", description = "文件上传返回VO")
public class UploadVO {

    @Schema(title = "访问路径")
    private String url;
}
