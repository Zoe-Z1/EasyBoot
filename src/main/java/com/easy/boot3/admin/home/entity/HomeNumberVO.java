package com.easy.boot3.admin.home.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Schema(title = "首页统计数量视图")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeNumberVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "当前在线人数")
    private Long onlineNumber;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "今日登录人数")
    private Long loginNumber;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "今日访问IP数")
    private Long ipNumber;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "今日操作总数")
    private Long operationNumber;

}
