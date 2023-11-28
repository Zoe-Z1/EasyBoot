package com.easy.boot.admin.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "disk对象", description = "磁盘")
public class Disk {

    @Schema(title = "磁盘路径")
    private String path;

    @Schema(title = "磁盘总空间/byte")
    private Long totalSpace;

    @Schema(title = "磁盘剩余空间/byte")
    private Long freeSpace;

    @Schema(title = "磁盘剩余可用空间/byte")
    private Long usableSpace;

    @Schema(title = "磁盘已用空间/byte")
    private Long usedSpace;

    @Schema(title = "磁盘总空间")
    private String totalSpaceStr;

    @Schema(title = "磁盘剩余空间")
    private String freeSpaceStr;

    @Schema(title = "磁盘剩余可用空间")
    private String usableSpaceStr;

    @Schema(title = "磁盘已用空间")
    private String usedSpaceStr;

    @Schema(title = "磁盘占用率")
    private String percent;
}
