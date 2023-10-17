package com.easy.boot.admin.scheduledTask.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/04
* @description 定时任务 实体
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("scheduled_task")
@ApiModel(value = "ScheduledTask对象", description = "定时任务")
public class ScheduledTask extends BaseEntity {

    @ApiModelProperty("任务名")
    @TableField("job_name")
    private String jobName;

    @ApiModelProperty("任务分组")
    @TableField("job_group")
    private String jobGroup;

    @ApiModelProperty("任务key")
    @TableField("job_key")
    private String jobKey;

    @ApiModelProperty("超时策略  #0：全部补偿， 1：补偿1次， 2：不补偿")
    @TableField("instruction")
    private Integer instruction;

    @ApiModelProperty("任务描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("cron表达式")
    @TableField("cron")
    private String cron;

    @ApiModelProperty("任务参数")
    @TableField("job_params")
    private String jobParams;

    @ApiModelProperty("任务状态  1：正常 2：暂停")
    @TableField("job_status")
    private Integer jobStatus;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;
}
