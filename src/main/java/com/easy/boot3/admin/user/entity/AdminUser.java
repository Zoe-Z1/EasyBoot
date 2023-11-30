package com.easy.boot3.admin.user.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot3.common.base.BaseEntity;
import com.easy.boot3.common.excel.EasyExcelSelect;
import com.easy.boot3.common.excel.converter.IntegerSexToStringConvert;
import com.easy.boot3.common.excel.converter.IntegerStatusToStringConvert;
import com.easy.boot3.common.sensitive.EasySensitive;
import com.easy.boot3.common.sensitive.EasySensitiveStrategyEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Schema(title = "用户实体")
@TableName("admin_user")
@Data
@ColumnWidth(20)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class AdminUser extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    @Schema(title = "部门ID")
    private Long departmentId;

    @ExcelIgnore
    @Schema(title = "头像")
    private String avatar;

    @ExcelProperty(value = "账号")
    @Schema(title = "账号")
    private String username;

    @ExcelProperty(value = "密码")
    @EasySensitive(strategy = EasySensitiveStrategyEnum.NONE)
    @Schema(title = "密码")
    private String password;

    @ExcelProperty(value = "昵称")
    @Schema(title = "昵称")
    private String name;

    @EasyExcelSelect(code = "dict_sex")
    @ExcelProperty(value = "性别", converter = IntegerSexToStringConvert.class)
    @Schema(title = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @ExcelProperty(value = "邮箱")
    @Schema(title = "邮箱")
    private String email;

    @ExcelProperty(value = "手机号码")
    @Schema(title = "手机号码")
    private String mobile;

    @ExcelIgnore
    @EasySensitive(strategy = EasySensitiveStrategyEnum.NONE)
    @Schema(title = "密码盐")
    private String salt;

    @EasyExcelSelect(code = "dict_status")
    @ExcelProperty(value = "账号状态", converter = IntegerStatusToStringConvert.class)
    @Schema(title = "账号状态 1：正常，2：禁用")
    private Integer status;

    @ExcelProperty(value = "排序")
    @Schema(title = "排序")
    private Integer sort;

}
