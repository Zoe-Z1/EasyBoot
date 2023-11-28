package com.easy.boot.admin.user.entity;

import com.easy.boot.common.validator.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zoe
 * @date 2023/7/29
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "用户编辑实体")
public class AdminUserUpdateDTO {

    @NotNull(message = "ID不能为空")
    @Schema(title = "ID")
    private Long id;

    @Schema(title = "部门ID")
    private Long departmentId;

    @Schema(title = "岗位ID集合")
    private List<Long> postIds;

    @Schema(title = "角色ID集合")
    private List<Long> roleIds;

    @Schema(title = "头像")
    private String avatar;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "昵称")
    @Length(min = 2, max = 10, message = "昵称在{min}-{max}个字符之间")
    private String name;

    @Range(min = 0, max = 2, message = "性别不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @Email(message = "邮箱格式不正确")
    @Schema(title = "邮箱")
    private String email;

    @Mobile
    @Schema(title = "手机号码")
    private String mobile;

    @Range(min = 1, max = 2, message = "账号状态不正确")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "账号状态 1：正常，2：禁用")
    private Integer status;

    @Schema(title = "排序")
    private Integer sort;

    @NotNull(message = "是否为状态切换不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "是否为状态切换")
    private Boolean isStatusChange;
}
