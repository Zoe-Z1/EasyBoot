package com.easy.boot3.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot3.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告实体
 */
@TableName("notice")
@Schema(title = "公告实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseEntity {


    @Schema(title = "公告标题")
    @TableField("title")
    private String title;

    @Schema(title = "公告内容")
    @TableField("content")
    private String content;

    @Schema(title = "公告状态 #1：正常，2：禁用")
    @TableField("status")
    private Integer status;

    @Schema(title = "开始时间")
    @TableField("startTime")
    private Long startTime;

    @Schema(title = "结束时间")
    @TableField("endTime")
    private Long endTime;

    @Schema(title = "排序")
    @TableField("sort")
    private Integer sort;
}
