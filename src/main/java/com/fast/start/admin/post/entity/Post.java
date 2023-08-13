package com.fast.start.admin.post.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.start.common.base.BaseEntity;
import com.fast.start.common.excel.converter.IntegerStatusToStringConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 岗位 实体
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ColumnWidth(20)
@EqualsAndHashCode(callSuper = true)
@TableName("post")
@ApiModel(value = "Post对象", description = "岗位")
public class Post extends BaseEntity {

    @ExcelProperty(value = "岗位编码", index = 0)
    @ApiModelProperty("岗位编码")
    @TableField("code")
    private String code;

    @ExcelProperty(value = "岗位名称", index = 1)
    @ApiModelProperty("岗位名称")
    @TableField("name")
    private String name;

    @ColumnWidth(30)
    @ExcelProperty(value = "岗位状态-正常/禁用", index = 2, converter = IntegerStatusToStringConvert.class)
    @ApiModelProperty("岗位状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ExcelProperty(value = "排序")
    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ExcelProperty(value = "岗位备注")
    @ApiModelProperty("岗位备注")
    @TableField("remarks")
    private String remarks;
}
