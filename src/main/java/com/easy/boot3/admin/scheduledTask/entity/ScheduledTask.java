package com.easy.boot3.admin.scheduledTask.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot3.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "ScheduledTask对象", description = "定时任务")
public class ScheduledTask extends BaseEntity {

    @Schema(title = "任务名")
    @TableField("job_name")
    private String jobName;

    @Schema(title = "任务分组")
    @TableField("job_group")
    private String jobGroup;

    @Schema(title = "任务key")
    @TableField("job_key")
    private String jobKey;

    @Schema(title = "超时策略  #0：全部补偿， 1：补偿1次， 2：不补偿")
    @TableField("instruction")
    private Integer instruction;

    @Schema(title = "任务描述")
    @TableField("description")
    private String description;

    @Schema(title = "cron表达式")
    @TableField("cron")
    private String cron;

    @Schema(title = "任务参数")
    @TableField("job_params")
    private String jobParams;

    @Schema(title = "任务状态  1：正常 2：暂停")
    @TableField("job_status")
    private Integer jobStatus;

    @Schema(title = "排序")
    @TableField("sort")
    private Integer sort;

    @Schema(title = "备注")
    @TableField("remarks")
    private String remarks;
}
