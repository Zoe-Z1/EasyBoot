package com.easy.boot.admin.post.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.EasyExcelSelect;
import com.easy.boot.common.excel.converter.IntegerStatusToStringConvert;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "Post对象", description = "岗位")
public class Post extends BaseEntity {

    @ExcelProperty(value = "岗位编码")
    @Schema(title = "岗位编码")
    @TableField("code")
    private String code;

    @ExcelProperty(value = "岗位名称")
    @Schema(title = "岗位名称")
    @TableField("name")
    private String name;

    @ColumnWidth(30)
    @ExcelProperty(value = "岗位状态", converter = IntegerStatusToStringConvert.class)
    @EasyExcelSelect(code = "dict_status")
    @Schema(title = "岗位状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ExcelProperty(value = "排序")
    @Schema(title = "排序")
    @TableField("sort")
    private Integer sort;

    @ExcelProperty(value = "岗位备注")
    @Schema(title = "岗位备注")
    @TableField("remarks")
    private String remarks;
}
