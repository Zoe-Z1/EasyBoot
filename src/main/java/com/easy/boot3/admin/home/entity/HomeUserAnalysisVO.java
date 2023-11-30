package com.easy.boot3.admin.home.entity;

import com.easy.boot3.common.Jackson.ToStringListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "用户分析视图实体")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeUserAnalysisVO {

    @Schema(title = "时间集合")
    private List<String> dates;

    @JsonSerialize(using = ToStringListSerializer.class)
    @Schema(title = "登录人数集合")
    private List<Long> loginNumbers;

    @JsonSerialize(using = ToStringListSerializer.class)
    @Schema(title = "访问IP数集合")
    private List<Long> ipNumbers;

    @JsonSerialize(using = ToStringListSerializer.class)
    @Schema(title = "操作总数集合")
    private List<Long> operationNumbers;

}
