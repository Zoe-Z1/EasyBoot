package cn.easy.boot.admin.login.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/7/21
 * @description
 */
@ApiModel("登录后处理器返回实体")
@Data
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginHandlerAfterDO {

    @ApiModelProperty("操作状态")
    private Boolean status;

    @ApiModelProperty("返回内容")
    private String message;
}
