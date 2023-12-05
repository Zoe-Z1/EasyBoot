package cn.easy.boot.admin.home.entity;

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
@ApiModel("热点接口实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HotsApiDO {

    @ApiModelProperty("请求接口地址")
    private String requestUrl;

    @ApiModelProperty("请求方法")
    private String requestWay;

    @ApiModelProperty("请求次数")
    private Long count;

}
