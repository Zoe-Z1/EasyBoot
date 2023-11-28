package com.easy.boot.admin.taskLog.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/06
* @description 调度日志 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "TaskLog对象", description = "调度日志新增DTO")
public class TaskLogCreateDTO {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务名")
    private String jobName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务分组")
    private String jobGroup;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务key")
    private String jobKey;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "超时策略  0：全部补偿 1：补偿1次 2：不补偿")
    private Integer instruction;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "cron表达式")
    private String cron;

    @Schema(title = "任务参数")
    private String jobParams;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "开始时间/ms")
    private Long startTime;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "结束时间/ms")
    private Long endTime;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "处理时间/ms")
    private Long handleTime;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "执行状态 SUCCESS：成功，FAIL：失败")
    private String status;

}
