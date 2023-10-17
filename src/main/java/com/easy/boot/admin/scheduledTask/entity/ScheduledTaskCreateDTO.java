package com.easy.boot.admin.scheduledTask.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/04
* @description 定时任务 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ScheduledTask对象", description = "定时任务")
public class ScheduledTaskCreateDTO {

    @Length(min = 1, max = 50, message = "任务名在{min}-{max}个字符之间")
    @NotBlank(message = "任务名不能为空")
    @ApiModelProperty(required = true, value = "任务名")
    private String jobName;

    @Length(min = 1, max = 50, message = "任务分组在{min}-{max}个字符之间")
    @NotBlank(message = "任务分组不能为空")
    @ApiModelProperty(required = true, value = "任务分组")
    private String jobGroup;

    @Length(min = 1, max = 50, message = "任务key在{min}-{max}个字符之间")
    @NotBlank(message = "任务key不能为空")
    @ApiModelProperty(required = true, value = "任务key")
    private String jobKey;

    @Range(min = 0, max = 2, message = "超时策略不正确")
    @NotNull(message = "超时策略不能为空")
    @ApiModelProperty(required = true, value = "超时策略  #0：全部补偿， 1：补偿1次， 2：不补偿")
    private Integer instruction;

    @ApiModelProperty(required = false, value = "任务描述")
    private String description;

    @NotBlank(message = "cron表达式不能为空")
    @ApiModelProperty(required = true, value = "cron表达式")
    private String cron;

    @ApiModelProperty(required = false, value = "任务参数")
    private String jobParams;

    @Range(min = 1, max = 2, message = "任务状态不正确")
    @NotNull(message = "任务状态不能为空")
    @ApiModelProperty(required = true, value = "任务状态  1：正常 2：暂停")
    private Integer jobStatus;

    @ApiModelProperty(required = false, value = "排序")
    private Integer sort;

    @ApiModelProperty(required = false, value = "备注")
    private String remarks;
}
