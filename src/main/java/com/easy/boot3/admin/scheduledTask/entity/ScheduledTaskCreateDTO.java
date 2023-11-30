package com.easy.boot3.admin.scheduledTask.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/04
* @description 定时任务 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ScheduledTask对象", description = "定时任务")
public class ScheduledTaskCreateDTO {

    @Length(min = 1, max = 50, message = "任务名在{min}-{max}个字符之间")
    @NotBlank(message = "任务名不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务名")
    private String jobName;

    @Length(min = 1, max = 50, message = "任务分组在{min}-{max}个字符之间")
    @NotBlank(message = "任务分组不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务分组")
    private String jobGroup;

    @Length(min = 1, max = 50, message = "任务key在{min}-{max}个字符之间")
    @NotBlank(message = "任务key不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务key")
    private String jobKey;

    @Range(min = 0, max = 2, message = "超时策略不正确")
    @NotNull(message = "超时策略不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "超时策略  #0：全部补偿， 1：补偿1次， 2：不补偿")
    private Integer instruction;

    @Schema(title = "任务描述")
    private String description;

    @NotBlank(message = "cron表达式不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "cron表达式")
    private String cron;

    @Schema(title = "任务参数")
    private String jobParams;

    @Range(min = 1, max = 2, message = "任务状态不正确")
    @NotNull(message = "任务状态不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务状态  1：正常 2：暂停")
    private Integer jobStatus;

    @Schema(title = "排序")
    private Integer sort;

    @Schema(title = "备注")
    private String remarks;
}
