package com.easy.boot.admin.user.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easy.boot.common.base.BaseEntity;
import com.easy.boot.common.excel.converter.IntegerSexToStringConvert;
import com.easy.boot.common.excel.converter.IntegerStatusToStringConvert;
import com.easy.boot.common.sensitive.EasySensitive;
import com.easy.boot.common.sensitive.EasySensitiveStrategyEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户实体")
@TableName("admin_user")
@Data
@ColumnWidth(20)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class AdminUser extends BaseEntity {

    @ExcelIgnore
    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ExcelIgnore
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ExcelProperty(value = "账号")
    @ApiModelProperty(value = "账号")
    private String username;

    @ExcelProperty(value = "密码")
    @EasySensitive(strategy = EasySensitiveStrategyEnum.NONE)
    @ApiModelProperty(value = "密码")
    private String password;

    @ExcelProperty(value = "昵称")
    @ApiModelProperty(value = "昵称")
    private String name;

    @ExcelProperty(value = "性别-保密/男/女", converter = IntegerSexToStringConvert.class)
    @ApiModelProperty(value = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @ExcelProperty(value = "邮箱")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "手机号码")
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ExcelIgnore
    @EasySensitive(strategy = EasySensitiveStrategyEnum.NONE)
    @ApiModelProperty(value = "密码盐")
    private String salt;

    @ExcelProperty(value = "账号状态-正常/禁用", converter = IntegerStatusToStringConvert.class)
    @ApiModelProperty(value = "账号状态 1：正常，2：禁用")
    private Integer status;

    @ExcelProperty(value = "排序")
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
