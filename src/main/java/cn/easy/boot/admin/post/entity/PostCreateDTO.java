package cn.easy.boot.admin.post.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

/**
* @author zoe
* @date 2023/07/29
* @description 岗位 DTO
*/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Post对象", description = "岗位")
public class PostCreateDTO {

    @Length(min = 1, max = 20, message = "岗位编码在{min}-{max}个字符之间")
    @NotBlank(message = "岗位编码不能为空")
    @ApiModelProperty(required = true, value = "岗位编码")
    private String code;

    @Length(min = 1, max = 20, message = "岗位名称在{min}-{max}个字符之间")
    @NotBlank(message = "岗位名称不能为空")
    @ApiModelProperty(required = true, value = "岗位名称")
    private String name;

    @Range(min = 1, max = 2, message = "岗位状态不正确")
    @ApiModelProperty(required = false, value = "岗位状态 1：正常 2：禁用")
    private Integer status;

    @ApiModelProperty(required = false, value = "排序")
    private Integer sort;

    @ApiModelProperty(required = false, value = "岗位备注")
    private String remarks;
}
