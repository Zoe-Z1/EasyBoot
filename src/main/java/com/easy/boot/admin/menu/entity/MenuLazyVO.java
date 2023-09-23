package com.easy.boot.admin.menu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/29
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("菜单懒加载返回数据对象")
public class MenuLazyVO extends Menu {

    @ApiModelProperty("是否为叶子节点")
    private Boolean isLeaf;
}
