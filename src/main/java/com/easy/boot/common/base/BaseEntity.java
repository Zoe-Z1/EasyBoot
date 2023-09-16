package com.easy.boot.common.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;
import com.easy.boot.common.excel.converter.LongTimeToStingTimeConvert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author zoe
 * @date 2023/7/22
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -7074837948698285044L;

    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    @ApiModelProperty("主键id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 创建者 */
    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建者")
    private Long createBy;

    /** 创建者账号 */
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建者账号")
    private String createUsername;

    /** 创建时间 */
    @ColumnWidth(20)
    @ExcelProperty(value = "创建时间", converter = LongTimeToStingTimeConvert.class)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Long createTime;

    /** 更新者 */
    @JsonIgnore
//    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新者")
    private Long updateBy;

    /** 更新者账号 */
    @JsonIgnore
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新者账号")
    private String updateUsername;

    /** 更新时间 */
    @JsonIgnore
    @ExcelIgnore
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private Long updateTime;

    /** 删除状态 */
    @JsonIgnore
    @ExcelIgnore
    @ApiModelProperty("删除状态：0：未删除，1：已删除")
    @TableLogic
    private Integer isDel;
}
