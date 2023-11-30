package com.easy.boot3.admin.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/12
 * @description 内存
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "memory对象", description = "内存")
public class Memory {

    @Schema(title = "总内存/byte")
    private Long total;

    @Schema(title = "可用内存/byte")
    private Long available;

    @Schema(title = "已用内存/byte")
    private Long used;

    @Schema(title = "单页内存/byte")
    private Long pageSize;

    @Schema(title = "总内存")
    private String totalStr;

    @Schema(title = "可用内存")
    private String availableStr;

    @Schema(title = "已用内存")
    private String usedStr;

    @Schema(title = "单页内存")
    private String pageSizeStr;

    @Schema(title = "内存使用率")
    private String percent;
}
