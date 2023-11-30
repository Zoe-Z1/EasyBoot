package com.easy.boot3.admin.blacklist.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
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
public class BlacklistCreateDTO {

    @NotNull(message = "拉黑类型不能为空")
    @Range(min = 1, max = 2, message = "拉黑类型不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "类型 1：账号 2：IP")
    private Integer type;

    @NotBlank(message = "关联数据不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "关联数据  IP地址或用户账号")
    private String relevanceData;

    @NotNull(message = "拉黑结束时间不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "拉黑结束时间 0代表永久")
    private Long endTime;
}
