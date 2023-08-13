package com.fast.start.admin.scheduledTask.entity;

import com.fast.start.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/08/04
 * @description 定时任务 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ScheduledTaskQuery对象", description = "定时任务")
public class ScheduledTaskQuery extends BasePageQuery {

    @ApiModelProperty("任务名")
    private String jobName;

    @ApiModelProperty("任务分组")
    private String jobGroup;

    @ApiModelProperty("任务key")
    private String jobKey;

    @ApiModelProperty("任务状态  1：正常 2：暂停")
    private Integer jobStatus;

}
