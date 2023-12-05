package cn.easy.boot.admin.taskLog.entity;

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
* @date 2023/08/06
* @description 调度日志 DTO
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TaskLog对象", description = "调度日志编辑DTO")
public class TaskLogUpdateDTO extends TaskLogCreateDTO {

    @NotNull(message = "调度日志ID不能为空")
    @ApiModelProperty(required = true, value = "调度日志ID")
    private Long id;

}
