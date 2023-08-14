package com.easy.boot.admin.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/12
 * @description 磁盘
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "disk对象", description = "磁盘")
public class Disk {

    @ApiModelProperty(value = "磁盘路径")
    private String path;

    @ApiModelProperty(value = "磁盘总空间/byte")
    private Long totalSpace;

    @ApiModelProperty(value = "磁盘剩余空间/byte")
    private Long freeSpace;

    @ApiModelProperty(value = "磁盘剩余可用空间/byte")
    private Long usableSpace;

    @ApiModelProperty(value = "磁盘已用空间/byte")
    private Long usedSpace;

    @ApiModelProperty(value = "磁盘总空间")
    private String totalSpaceStr;

    @ApiModelProperty(value = "磁盘剩余空间")
    private String freeSpaceStr;

    @ApiModelProperty(value = "磁盘剩余可用空间")
    private String usableSpaceStr;

    @ApiModelProperty(value = "磁盘已用空间")
    private String usedSpaceStr;

    @ApiModelProperty(value = "磁盘占用率")
    private String percent;
}
