package cn.easy.boot.admin.scheduledTask.entity;

import cn.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ScheduledTaskQuery对象", description = "定时任务")
public class ScheduledTaskQuery extends BasePageQuery {

    @Range(min = 1, max = 2, message = "任务状态不正确")
    @ApiModelProperty("任务状态  1：正常 2：暂停")
    private Integer jobStatus;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

}
