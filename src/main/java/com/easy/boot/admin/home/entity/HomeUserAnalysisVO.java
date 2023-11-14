package com.easy.boot.admin.home.entity;

import com.easy.boot.common.Jackson.ToStringListSerializer;
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
@ApiModel("用户分析视图实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeUserAnalysisVO {

    @ApiModelProperty("时间集合")
    private List<String> dates;

    @JsonSerialize(using = ToStringListSerializer.class)
    @ApiModelProperty("登录人数集合")
    private List<Long> loginNumbers;

    @JsonSerialize(using = ToStringListSerializer.class)
    @ApiModelProperty("访问IP数集合")
    private List<Long> ipNumbers;

    @JsonSerialize(using = ToStringListSerializer.class)
    @ApiModelProperty("操作总数集合")
    private List<Long> operationNumbers;

}
