package com.easy.boot.admin.home.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/11/14
 * @description
 */
@ApiModel("接口处理时长实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HandlerTimeDO {

    @ApiModelProperty("请求接口地址")
    private String requestUrl;

    @ApiModelProperty("请求方法")
    private String requestWay;

    @ApiModelProperty("处理时长")
    private Long handleTime;

}
