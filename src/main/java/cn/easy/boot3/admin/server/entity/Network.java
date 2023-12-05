package cn.easy.boot3.admin.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/12
 * @description 网络
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Network对象", description = "网络")
public class Network {

    @Schema(title = "发送数据量/byte")
    private Long sent;

    @Schema(title = "接收数据量/byte")
    private Long recv;

    @Schema(title = "发送的数据包数量")
    private Long packetsSent;

    @Schema(title = "接收的数据包数量")
    private Long packetsRecv;

    @Schema(title = "接收的数据包错误数")
    private Long inErrors;

    @Schema(title = "发送的数据包错误数")
    private Long outErrors;

    @Schema(title = "丢包数")
    private Long inDrops;

    @Schema(title = "最大传输速率 byte/s")
    private Long speed;

    @Schema(title = "上传速率")
    private String sentStr;

    @Schema(title = "下载速率")
    private String recvStr;

    @Schema(title = "最大传输速率")
    private String speedStr;
}
