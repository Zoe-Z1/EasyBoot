package com.easy.boot.admin.notice.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告创建实体
 */
@Schema(title = "公告创建实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class NoticeCreateDTO {


    @Length(max = 20, message = "公告标题不能超过{max}个字")
    @Schema(title = "公告标题")
    @NotBlank(message = "公告标题不能为空")
    private String title;

    @Length(max = 500, message = "公告内容不能超过{max}个字")
    @Schema(title = "公告内容")
    @NotBlank(message = "公告内容不能为空")
    private String content;

    @Range(min = 1, max = 2, message = "公告状态不正确")
    @Schema(title = "状态 #1：正常，2：禁用")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

    @Schema(title = "排序")
    private Integer sort;

}
