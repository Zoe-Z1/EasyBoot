package com.easy.boot.admin.scheduledTask.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

/**
 * @author zoe
 * @date 2023/8/5
 * @description 恢复/暂停定时任务dto
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "恢复/暂停定时任务dto", description = "定时任务")
public class ChangeJobDTO {

    @NotNull(message = "任务ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务ID")
    private Long id;

    @NotNull(message = "任务状态不能为空")
    @Range(min = 1, max = 2, message = "任务状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "任务状态  1：正常 2：暂停")
    private Integer jobStatus;
}
