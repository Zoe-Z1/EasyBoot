package cn.easy.boot3.admin.generateConfig.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/9/7
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "代码生成UpdateDTO参数对象")
public class UpdateDTO {

    @Builder.Default
    @Schema(title = "是否生成")
    private Boolean enable = true;

    @Builder.Default
    @Schema(title = "是否继承createDTO")
    private Boolean enableExtendsCreateDTO = true;

}
