package cn.easy.boot.admin.home.entity;

import cn.easy.boot.common.Jackson.ToStringListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@ApiModel("热点接口视图实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeHotsApiVO {

    @ApiModelProperty("请求接口地址集合")
    private List<String> urls;

    @JsonSerialize(using = ToStringListSerializer.class)
    @ApiModelProperty("请求次数集合")
    private List<Long> counts;

}
