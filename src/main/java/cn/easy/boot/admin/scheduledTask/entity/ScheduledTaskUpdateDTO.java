package cn.easy.boot.admin.scheduledTask.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

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
@ApiModel(value = "ScheduledTask对象", description = "定时任务")
public class ScheduledTaskUpdateDTO extends ScheduledTaskCreateDTO {

    @NotNull(message = "任务ID不能为空")
    @ApiModelProperty(required = true, value = "任务ID")
    private Long id;

}
