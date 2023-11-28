package com.easy.boot.admin.post.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;

/**
* @author zoe
* @date 2023/07/29
* @description 岗位 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Post对象", description = "岗位")
public class PostCreateDTO {

    @Length(min = 1, max = 20, message = "岗位编码在{min}-{max}个字符之间")
    @NotBlank(message = "岗位编码不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "岗位编码")
    private String code;

    @Length(min = 1, max = 20, message = "岗位名称在{min}-{max}个字符之间")
    @NotBlank(message = "岗位名称不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "岗位名称")
    private String name;

    @Range(min = 1, max = 2, message = "岗位状态不正确")
    @Schema(title = "岗位状态 1：正常 2：禁用")
    private Integer status;

    @Schema(title = "排序")
    private Integer sort;

    @Schema(title = "岗位备注")
    private String remarks;
}
