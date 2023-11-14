package com.easy.boot.admin.home.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@ApiModel("首页统计数量视图")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeNumberVO {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("当前在线人数")
    private Long onlineNumber;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("今日登录人数")
    private Long loginNumber;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("今日访问IP数")
    private Long ipNumber;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("今日操作总数")
    private Long operationNumber;

}
