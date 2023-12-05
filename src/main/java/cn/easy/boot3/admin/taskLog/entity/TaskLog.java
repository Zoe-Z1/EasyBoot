package cn.easy.boot3.admin.taskLog.entity;

import cn.easy.boot3.common.base.BaseEntity;
import cn.easy.boot3.common.excel.converter.IntegerInstructionToStringConvert;
import cn.easy.boot3.common.excel.converter.LongTimeToStingTimeConvert;
import cn.easy.boot3.common.excel.converter.StringStatusToStringConvert;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/06
* @description 调度日志 实体
*/
@Data
@ColumnWidth(20)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("task_log")
@Schema(title = "TaskLog对象", description = "调度日志实体")
public class TaskLog extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    @Schema(title = "定时任务ID")
    @TableField("task_id")
    private Long taskId;

    @ExcelProperty(value = "任务名")
    @Schema(title = "任务名")
    @TableField("job_name")
    private String jobName;

    @ExcelProperty(value = "任务分组")
    @Schema(title = "任务分组")
    @TableField("job_group")
    private String jobGroup;

    @ExcelProperty(value = "任务key")
    @Schema(title = "任务key")
    @TableField("job_key")
    private String jobKey;

    @ExcelProperty(value = "超时策略", converter = IntegerInstructionToStringConvert.class)
    @Schema(title = "超时策略  0：全部补偿 1：补偿1次 2：不补偿")
    @TableField("instruction")
    private Integer instruction;

    @ExcelProperty(value = "cron表达式")
    @Schema(title = "cron表达式")
    @TableField("cron")
    private String cron;

    @ExcelProperty(value = "任务参数")
    @Schema(title = "任务参数")
    @TableField("job_params")
    private String jobParams;

    @ExcelProperty(value = "开始时间", converter = LongTimeToStingTimeConvert.class)
    @Schema(title = "开始时间/ms")
    @TableField("start_time")
    private Long startTime;

    @ExcelProperty(value = "结束时间", converter = LongTimeToStingTimeConvert.class)
    @Schema(title = "结束时间/ms")
    @TableField("end_time")
    private Long endTime;

    @ExcelProperty(value = "处理时间/毫秒")
    @Schema(title = "处理时间/ms")
    @TableField("handle_time")
    private Long handleTime;

    @ExcelProperty(value = "执行状态", converter = StringStatusToStringConvert.class)
    @Schema(title = "执行状态 SUCCESS：成功，FAIL：失败")
    @TableField("status")
    private String status;

}
