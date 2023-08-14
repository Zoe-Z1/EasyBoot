package com.easy.boot.admin.userPost.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "UserPost对象", description = "用户岗位关联")
public class UserPostUpdateDTO {

    @ApiModelProperty(required = false, value = "用户ID")
    private Long userId;

    @ApiModelProperty(required = false, value = "岗位ID")
    private Long postId;
}
