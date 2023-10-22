package com.easy.boot.admin.notice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告创建实体
 */
@ApiModel(value = "公告创建实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class NoticeCreateDTO {


    @Length(max = 20, message = "公告标题不能超过{max}个字")
    @ApiModelProperty("公告标题")
    @NotBlank(message = "公告标题不能为空")
    private String title;

    @Length(max = 500, message = "公告内容不能超过{max}个字")
    @ApiModelProperty("公告内容")
    @NotBlank(message = "公告内容不能为空")
    private String content;

    @Range(min = 1, max = 2, message = "公告状态不正确")
    @ApiModelProperty("状态 #1：正常，2：禁用")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

    @ApiModelProperty("排序")
    private Integer sort;

}
