package cn.easy.boot.admin.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/12
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Server对象", description = "服务器")
public class ServerVO {

    @ApiModelProperty(value = "项目信息")
    private Project project;

    @ApiModelProperty(value = "处理器信息")
    private Cpu cpu;

    @ApiModelProperty(value = "操作系统信息")
    private Os os;

    @ApiModelProperty(value = "内存信息")
    private Memory memory;

    @ApiModelProperty(value = "磁盘信息")
    private Disk disk;

    @ApiModelProperty(value = "网络信息")
    private Network network;

    @ApiModelProperty(value = "jvm信息")
    private Jvm jvm;
}
