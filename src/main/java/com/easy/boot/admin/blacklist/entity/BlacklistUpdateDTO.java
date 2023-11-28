package com.easy.boot.admin.blacklist.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

/**
* @author zoe
* @date 2023/08/01
* @description 黑名单 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Blacklist对象", description = "黑名单")
public class BlacklistUpdateDTO {

    @NotNull(message = "黑名单ID不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "黑名单ID")
    private Long id;

    @NotNull(message = "拉黑结束时间不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "拉黑结束时间 0代表永久")
    private Long endTime;

}
