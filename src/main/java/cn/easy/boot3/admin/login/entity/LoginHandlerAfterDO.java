package cn.easy.boot3.admin.login.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "登录后处理器返回实体")
@Data
@SuperBuilder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginHandlerAfterDO {

    @Schema(title = "操作状态")
    private Boolean status;

    @Schema(title = "返回内容")
    private String message;
}
