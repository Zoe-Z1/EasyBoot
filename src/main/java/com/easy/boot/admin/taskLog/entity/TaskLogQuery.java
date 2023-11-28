package com.easy.boot.admin.taskLog.entity;

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
 * @date 2023/08/06
 * @description 调度日志 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "TaskLogQuery对象", description = "调度日志查询DTO")
public class TaskLogQuery extends BasePageQuery {

    @Schema(title = "定时任务ID")
    private Long taskId;

    @Schema(title = "超时策略  #0：全部补偿， 1：补偿1次 ，2：不补偿")
    private Integer instruction;

    @Schema(title = "任务参数")
    private String jobParams;

    @Schema(title = "开始时间/ms")
    private Long startTime;

    @Schema(title = "结束时间/ms")
    private Long endTime;

    @Schema(title = "执行状态 SUCCESS：成功，FAIL：失败")
    private String status;

    @Schema(title = "处理时间/ms")
    private Long handleTime;

    @Range(min = 1, max = 6, message = "处理时间操作符不正确")
    @Schema(title = "处理时间操作符 1：≥ 2：≤ 3：＞ 4：＜ 5：= 6：≠")
    private Integer handleTimeOperator;

}
