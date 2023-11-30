package com.easy.boot3.admin.role.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot3.common.base.BaseEntity;
import com.easy.boot3.common.excel.EasyExcelSelect;
import com.easy.boot3.common.excel.converter.IntegerStatusToStringConvert;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/30
* @description 角色 实体
*/
@Data
@ColumnWidth(20)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("role")
@Schema(title = "Role对象", description = "角色")
public class Role extends BaseEntity {

    @ExcelProperty(value = "角色名称")
    @Schema(title = "角色名称")
    @TableField("name")
    private String name;

    @ExcelProperty(value = "角色编码")
    @Schema(title = "角色编码")
    @TableField("code")
    private String code;

    @ColumnWidth(30)
    @EasyExcelSelect(code = "dict_status")
    @ExcelProperty(value = "角色状态", converter = IntegerStatusToStringConvert.class)
    @Schema(title = "角色状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ExcelProperty(value = "排序")
    @Schema(title = "排序")
    @TableField("sort")
    private Integer sort;

    @ExcelProperty(value = "备注")
    @Schema(title = "备注")
    @TableField("remarks")
    private String remarks;
}
