package com.easy.boot3.admin.home.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/11/14
 * @description
 */
@Schema(title = "热点接口实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HotsApiDO {

    @Schema(title = "请求接口地址")
    private String requestUrl;

    @Schema(title = "请求方法")
    private String requestWay;

    @Schema(title = "请求次数")
    private Long count;

}
