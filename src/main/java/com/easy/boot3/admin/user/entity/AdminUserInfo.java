package com.easy.boot3.admin.user.entity;

import com.easy.boot3.admin.department.entity.Department;
import com.easy.boot3.admin.menu.entity.MenuTree;
import com.easy.boot3.admin.post.entity.Post;
import com.easy.boot3.admin.role.entity.Role;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "用户信息实体")
public class AdminUserInfo {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "id")
    private Long id;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "账号")
    private String username;

    @Schema(title = "昵称")
    private String name;

    @Schema(title = "性别 0：保密 1：男 2：女")
    private Integer sex;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "手机号码")
    private String mobile;

    @Schema(title = "账号状态 1：正常，2：禁用")
    private Integer status;

    @Schema(title = "是否超级管理员")
    private Boolean isAdmin;

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(title = "部门ID")
    private Long departmentId;

    @Schema(title = "角色编码集合")
    private List<String> roleCodes;

    @Schema(title = "权限字符集合")
    private List<String> permissions;

    @Schema(title = "树形菜单集合")
    private List<MenuTree> menus;

    @Schema(title = "角色信息集合")
    private List<Role> roles;

    @Schema(title = "岗位信息集合")
    private List<Post> posts;

    @Schema(title = "部门信息")
    private Department department;

}
