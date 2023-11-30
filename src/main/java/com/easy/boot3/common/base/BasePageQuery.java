package com.easy.boot3.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @describe 列表查询分页入参基类
 * @author zoe
 * @date 2023/7/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BasePageQuery implements Serializable {

    private static final long serialVersionUID = 297152698092681735L;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "页码数")
    @NotNull(message = "页码数不能为空")
    @Min(value = 1, message = "页码数不能低于{value}")
    private Long pageNum;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "每页条数")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 5, message = "每页条数不能低于{value}")
    @Max(value = 200, message = "每页条数不能超过{value}")
    private Long pageSize;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "关键词")
    private String keyword;
}
