package com.easy.boot3.admin.post.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 岗位 DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Post对象", description = "岗位")
public class PostUpdateDTO extends PostCreateDTO {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "ID")
    private Long id;

}
