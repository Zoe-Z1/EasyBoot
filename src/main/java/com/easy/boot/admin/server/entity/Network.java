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
 * @description 网络
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Network对象", description = "网络")
public class Network {

    @ApiModelProperty(value = "发送数据量/byte")
    private Long sent;

    @ApiModelProperty(value = "接收数据量/byte")
    private Long recv;

    @ApiModelProperty(value = "发送的数据包数量")
    private Long packetsSent;

    @ApiModelProperty(value = "接收的数据包数量")
    private Long packetsRecv;

    @ApiModelProperty(value = "接收的数据包错误数")
    private Long inErrors;

    @ApiModelProperty(value = "发送的数据包错误数")
    private Long outErrors;

    @ApiModelProperty(value = "丢包数")
    private Long inDrops;

    @ApiModelProperty(value = "最大传输速率 byte/s")
    private Long speed;

    @ApiModelProperty(value = "上传速率")
    private String sentStr;

    @ApiModelProperty(value = "下载速率")
    private String recvStr;

    @ApiModelProperty(value = "最大传输速率")
    private String speedStr;
}
