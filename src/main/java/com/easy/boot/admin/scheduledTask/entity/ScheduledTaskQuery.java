package com.easy.boot.admin.scheduledTask.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

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
@Schema(title = "ScheduledTaskQuery对象", description = "定时任务")
public class ScheduledTaskQuery extends BasePageQuery {

    @Range(min = 1, max = 2, message = "任务状态不正确")
    @Schema(title = "任务状态  1：正常 2：暂停")
    private Integer jobStatus;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

}
