package cn.easy.boot.admin.role.entity;

import cn.easy.boot.common.Jackson.ToStringListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
* @author zoe
* @date 2023/07/30
* @description 角色 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("角色视图")
public class RoleVO extends Role {

    @JsonSerialize(using = ToStringListSerializer.class)
    @ApiModelProperty("菜单ID集合")
    private List<Long> menuIds;
}
