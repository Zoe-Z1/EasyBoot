package com.easy.boot.admin.blacklist.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
public class BlacklistUpdateDTO {

    @NotNull(message = "黑名单ID不能为空")
    @ApiModelProperty(required = true, value = "黑名单ID")
    private Long id;

    @NotNull(message = "拉黑时长不能为空")
    @ApiModelProperty(required = true, value = "拉黑时长/分钟 0代表永久")
    private Long duration;

}
