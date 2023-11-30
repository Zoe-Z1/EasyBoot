package com.easy.boot3.admin.userPost.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/29
* @description 用户岗位关联 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "UserPost对象", description = "用户岗位关联")
public class UserPostCreateDTO {

    @Schema(title = "用户ID")
    private Long userId;

    @Schema(title = "岗位ID")
    private Long postId;
}
