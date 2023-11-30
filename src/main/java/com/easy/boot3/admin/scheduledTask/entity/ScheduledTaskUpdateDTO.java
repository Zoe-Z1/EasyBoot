package com.easy.boot3.admin.scheduledTask.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/04
* @description 定时任务 DTO
*/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "ScheduledTask对象", description = "定时任务")
public class ScheduledTaskUpdateDTO extends ScheduledTaskCreateDTO {

    @NotNull(message = "任务ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务ID")
    private Long id;

}
