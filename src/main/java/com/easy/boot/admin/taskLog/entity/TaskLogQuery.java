package com.easy.boot.admin.taskLog.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/08/06
 * @description 调度日志 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TaskLogQuery对象", description = "调度日志查询DTO")
public class TaskLogQuery extends BasePageQuery {

    @ApiModelProperty("任务名")
    private String jobName;

    @ApiModelProperty("任务分组")
    private String jobGroup;

    @ApiModelProperty("任务key")
    private String jobKey;

    @ApiModelProperty("开始时间/ms")
    private Long startTime;

    @ApiModelProperty("结束时间/ms")
    private Long endTime;

    @ApiModelProperty("执行状态 SUCCESS：成功，FAIL：失败")
    private String status;

}
