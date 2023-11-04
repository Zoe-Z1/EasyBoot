package com.easy.boot.admin.user.entity;

import com.easy.boot.admin.department.entity.Department;
import com.easy.boot.admin.menu.entity.MenuTree;
import com.easy.boot.admin.post.entity.Post;
import com.easy.boot.admin.role.entity.Role;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息实体")
public class AdminUserInfo {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "账号状态 1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty(value = "是否超级管理员")
    private Boolean isAdmin;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty(value = "角色编码集合")
    private List<String> roleCodes;

    @ApiModelProperty(value = "权限字符集合")
    private List<String> permissions;

    @ApiModelProperty(value = "树形菜单集合")
    private List<MenuTree> menus;

    @ApiModelProperty(value = "角色信息集合")
    private List<Role> roles;

    @ApiModelProperty(value = "岗位信息集合")
    private List<Post> posts;

    @ApiModelProperty(value = "部门信息")
    private Department department;

}
