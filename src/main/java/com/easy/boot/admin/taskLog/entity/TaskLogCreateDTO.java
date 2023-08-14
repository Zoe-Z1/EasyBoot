package com.easy.boot.admin.taskLog.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "TaskLog对象", description = "调度日志新增DTO")
public class TaskLogCreateDTO {

    @ApiModelProperty(required = true, value = "任务名")
    private String jobName;

    @ApiModelProperty(required = true, value = "任务分组")
    private String jobGroup;

    @ApiModelProperty(required = true, value = "任务key")
    private String jobKey;

    @ApiModelProperty(required = true, value = "超时策略  0：全部补偿 1：补偿1次 2：不补偿")
    private Integer instruction;

    @ApiModelProperty(required = true, value = "cron表达式")
    private String cron;

    @ApiModelProperty(required = false, value = "任务参数")
    private String jobParams;

    @ApiModelProperty(required = true, value = "开始时间/ms")
    private Long startTime;

    @ApiModelProperty(required = true, value = "结束时间/ms")
    private Long endTime;

    @ApiModelProperty(required = true, value = "处理时间/ms")
    private Long handleTime;

    @ApiModelProperty(required = true, value = "执行状态 SUCCESS：成功，FAIL：失败")
    private String status;

}
