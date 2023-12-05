package cn.easy.boot.admin.scheduledTask.entity;

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
 * @description 恢复/暂停定时任务dto
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "恢复/暂停定时任务dto", description = "定时任务")
public class ChangeJobDTO {

    @NotNull(message = "任务ID不能为空")
    @ApiModelProperty(required = true, value = "任务ID")
    private Long id;

    @NotNull(message = "任务状态不能为空")
    @Range(min = 1, max = 2, message = "任务状态不正确")
    @ApiModelProperty(required = true, value = "任务状态  1：正常 2：暂停")
    private Integer jobStatus;
}
