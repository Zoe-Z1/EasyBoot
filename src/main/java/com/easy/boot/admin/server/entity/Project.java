package com.easy.boot.admin.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/10/21
 * @description 项目
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "项目")
public class Project {

    @Schema(title = "工作目录")
    private String userDir;

    @Schema(title = "启动时间")
    private Long startTime;

    @Schema(title = "运行时长/ms")
    private Long runningTime;

    @Schema(title = "转换后的运行时长")
    private String runningTimeStr;
}
