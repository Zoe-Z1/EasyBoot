package com.fast.start.admin.user.entity;

import com.fast.start.admin.menu.entity.MenuTree;
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

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "账号状态 1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty(value = "角色编码集合")
    private List<String> roles;

    @ApiModelProperty(value = "权限字符集合")
    private List<String> permissions;

    @ApiModelProperty(value = "树形菜单集合")
    private List<MenuTree> menus;

}
