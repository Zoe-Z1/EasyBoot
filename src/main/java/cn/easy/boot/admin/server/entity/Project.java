package cn.easy.boot.admin.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/10/21
 * @description 项目
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("项目")
public class Project {

    @ApiModelProperty(value = "工作目录")
    private String userDir;

    @ApiModelProperty(value = "启动时间")
    private Long startTime;

    @ApiModelProperty(value = "运行时长/ms")
    private Long runningTime;

    @ApiModelProperty(value = "转换后的运行时长")
    private String runningTimeStr;
}
