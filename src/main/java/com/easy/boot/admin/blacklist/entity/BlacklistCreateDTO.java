package com.easy.boot.admin.blacklist.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/01
* @description 黑名单 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Blacklist对象", description = "黑名单")
public class BlacklistCreateDTO {

    @NotNull(message = "拉黑类型不能为空")
    @Range(min = 1, max = 2, message = "拉黑类型不正确")
    @ApiModelProperty(required = true, value = "类型 1：账号 2：IP")
    private Integer type;

    @NotBlank(message = "关联数据不能为空")
    @ApiModelProperty(required = true, value = "关联数据  IP地址或用户账号")
    private String relevanceData;

    @NotNull(message = "拉黑结束时间不能为空")
    @ApiModelProperty(required = true, value = "拉黑结束时间 0代表永久")
    private Long endTime;
}
