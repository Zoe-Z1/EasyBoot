package cn.easy.boot.admin.user.entity;

import cn.easy.boot.common.validator.Mobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
@ApiModel("用户创建实体")
public class AdminUserCreateDTO {

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty("岗位ID集合")
    private List<Long> postIds;

    @ApiModelProperty("角色ID集合")
    private List<Long> roleIds;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @NotBlank(message = "账号不能为空")
    @Length(min = 5, max = 15, message = "账号在{min}-{max}个字符之间")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$", message = "账号格式不正确，仅支持字母、数字和下划线")
    @ApiModelProperty(required = true, value = "账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(max = 100, message = "密码长度过长")
    @ApiModelProperty(required = true, value = "密码")
    private String password;

    @NotBlank(message = "昵称不能为空")
    @Length(min = 2, max = 10, message = "昵称在{min}-{max}个字符之间")
    @ApiModelProperty(required = true, value = "昵称")
    private String name;

    @Range(min = 0, max = 2, message = "性别不正确")
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(required = true, value = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Mobile
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @Range(min = 1, max = 2, message = "账号状态不正确")
    @NotNull(message = "账号状态不能为空")
    @ApiModelProperty(required = true, value = "账号状态 1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
