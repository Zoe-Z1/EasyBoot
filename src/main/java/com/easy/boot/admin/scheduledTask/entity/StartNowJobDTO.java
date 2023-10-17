package com.easy.boot.admin.scheduledTask.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author zoe
 * @date 2023/8/5
 * @description 立即执行dto
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "立即执行dto", description = "定时任务")
public class StartNowJobDTO {

    @NotNull(message = "任务ID不能为空")
    @ApiModelProperty(required = true, value = "任务ID")
    private Long id;

    @Range(min = 1, max = 10, message = "执行次数在{min}-{max}次之间")
    @NotNull(message = "执行次数不能为空")
    @ApiModelProperty(required = true, value = "执行次数")
    private Integer count;

    @NotNull(message = "执行间隔不能为空")
    @ApiModelProperty(required = true, value = "执行时间间隔")
    private Integer interval;

    @Range(min = 1, max = 4, message = "执行间隔单位不正确")
    @NotNull(message = "执行间隔单位不能为空")
    @ApiModelProperty(required = true, value = "执行间隔单位 1：小时 2：分钟 3：秒 4：毫秒")
    private Integer unit;
}
