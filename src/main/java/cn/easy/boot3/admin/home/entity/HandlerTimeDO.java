package cn.easy.boot3.admin.home.entity;

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
@Schema(title = "接口处理时长实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HandlerTimeDO {

    @Schema(title = "请求接口地址")
    private String requestUrl;

    @Schema(title = "请求方法")
    private String requestWay;

    @Schema(title = "处理时长")
    private Long handleTime;

}
