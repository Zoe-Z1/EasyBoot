package com.easy.boot3.admin.taskLog.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/06
* @description 调度日志 DTO
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "TaskLog对象", description = "调度日志编辑DTO")
public class TaskLogUpdateDTO extends TaskLogCreateDTO {

    @NotNull(message = "调度日志ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "调度日志ID")
    private Long id;

}
