package com.easy.boot.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告实体
 */
@TableName("notice")
@ApiModel(value = "公告实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Notice extends BaseEntity {


    @ApiModelProperty("公告标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("公告内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("公告状态 #1：正常，2：禁用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("开始时间")
    @TableField("startTime")
    private Long startTime;

    @ApiModelProperty("结束时间")
    @TableField("endTime")
    private Long endTime;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;
}
