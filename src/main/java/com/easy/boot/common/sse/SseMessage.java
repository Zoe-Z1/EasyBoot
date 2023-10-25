package com.easy.boot.common.sse;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/10/24
 * @description
 */
@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SseMessage {

    @ApiModelProperty(required = true, value = "消息类型 0:新连接 1：新公告 2：新消息")
    private Integer type;

    @ApiModelProperty("消息内容")
    private String message;

    public SseMessage(Integer type) {
        this.type = type;
    }
}
