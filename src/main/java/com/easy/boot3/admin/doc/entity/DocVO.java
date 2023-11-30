package com.easy.boot3.admin.doc.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Schema(title = "文档视图实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class DocVO {

    @Schema(title = "是否开启")
    private Boolean enable;

    @Schema(title = "文档地址")
    private String url;

}
