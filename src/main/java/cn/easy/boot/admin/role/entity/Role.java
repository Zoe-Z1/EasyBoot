package cn.easy.boot.admin.role.entity;

import cn.easy.boot.common.base.BaseEntity;
import cn.easy.boot.common.excel.EasyExcelSelect;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.easy.boot.common.excel.converter.IntegerStatusToStringConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Role对象", description = "角色")
public class Role extends BaseEntity {

    @ExcelProperty(value = "角色名称")
    @ApiModelProperty("角色名称")
    @TableField("name")
    private String name;

    @ExcelProperty(value = "角色编码")
    @ApiModelProperty("角色编码")
    @TableField("code")
    private String code;

    @ColumnWidth(30)
    @EasyExcelSelect(code = "dict_status")
    @ExcelProperty(value = "角色状态", converter = IntegerStatusToStringConvert.class)
    @ApiModelProperty("角色状态 1：正常 2：禁用")
    @TableField("status")
    private Integer status;

    @ExcelProperty(value = "排序")
    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    @TableField("remarks")
    private String remarks;
}
