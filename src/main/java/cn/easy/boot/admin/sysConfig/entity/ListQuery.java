package cn.easy.boot.admin.sysConfig.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @author zoe
 * @date 2023/9/7
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysConfigQuery对象", description = "系统配置")
public class ListQuery {

    @NotBlank(message = "配置域编码不能为空")
    @ApiModelProperty(required = true, value = "配置域编码")
    private String code;
}
