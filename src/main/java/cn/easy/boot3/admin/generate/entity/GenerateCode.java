package cn.easy.boot3.admin.generate.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成实体
 */
@Schema(title = "代码生成实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateCode {

    @Schema(title = "作者")
    private String author;

    @Schema(title = "文件名")
    private String filename;

    @Schema(title = "文件生成路径")
    private String genPath;

    @Schema(title = "文件内容")
    private String fileContent;

    @Schema(title = "是否执行SQL")
    private Boolean execute;

}
