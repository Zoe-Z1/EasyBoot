package cn.easy.boot3.admin.user.entity;

import cn.easy.boot3.common.validator.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Schema(title = "用户创建实体")
public class AdminUserCreateDTO {

    @Schema(title = "部门ID")
    private Long departmentId;

    @Schema(title = "岗位ID集合")
    private List<Long> postIds;

    @Schema(title = "角色ID集合")
    private List<Long> roleIds;

    @Schema(title = "头像")
    private String avatar;

    @NotBlank(message = "账号不能为空")
    @Length(min = 5, max = 15, message = "账号在{min}-{max}个字符之间")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "账号格式不正确，仅支持字母、数字和下划线")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(max = 100, message = "密码长度过长")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "密码")
    private String password;

    @NotBlank(message = "昵称不能为空")
    @Length(min = 2, max = 10, message = "昵称在{min}-{max}个字符之间")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "昵称")
    private String name;

    @Range(min = 0, max = 2, message = "性别不正确")
    @NotNull(message = "性别不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @Email(message = "邮箱格式不正确")
    @Schema(title = "邮箱")
    private String email;

    @Mobile
    @Schema(title = "手机号码")
    private String mobile;

    @Range(min = 1, max = 2, message = "账号状态不正确")
    @NotNull(message = "账号状态不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, title = "账号状态 1：正常，2：禁用")
    private Integer status;

    @Schema(title = "排序")
    private Integer sort;
}
