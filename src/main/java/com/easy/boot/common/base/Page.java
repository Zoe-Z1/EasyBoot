package com.easy.boot.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zoe
 * @date 2023/9/9
 * @description 自定义分页实体
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Page<T> {

    @Schema(title = "当前页码数")
    private Long current;

    @Schema(title = "每页条数")
    private Long size;

    @Schema(title = "总条数")
    private Long total;

    @Schema(title = "分页数据")
    private List<T> records;
}
