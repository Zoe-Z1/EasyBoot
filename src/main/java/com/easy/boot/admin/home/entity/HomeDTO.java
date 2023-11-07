package com.easy.boot.admin.home.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @author zoe
 * @date 2023/7/29
 * @description
 */
@ApiModel("首页DTO")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HomeDTO {

    @NotBlank(message = "类型")
    @ApiModelProperty(required = true, value = "类型")
    private Integer type;

}
