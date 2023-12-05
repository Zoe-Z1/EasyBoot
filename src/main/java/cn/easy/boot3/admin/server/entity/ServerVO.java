package cn.easy.boot3.admin.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "Server对象", description = "服务器")
public class ServerVO {

    @Schema(title = "项目信息")
    private Project project;

    @Schema(title = "处理器信息")
    private Cpu cpu;

    @Schema(title = "操作系统信息")
    private Os os;

    @Schema(title = "内存信息")
    private Memory memory;

    @Schema(title = "磁盘信息")
    private Disk disk;

    @Schema(title = "网络信息")
    private Network network;

    @Schema(title = "jvm信息")
    private Jvm jvm;
}
