package com.easy.boot.admin.generate.entity;

import com.easy.boot.admin.generateColumn.entity.GenerateColumnUpdateDTO;
import com.easy.boot.admin.generateConfig.entity.GenerateConfigUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


/**
 * @author zoe
 * @date 2023/09/10
 * @description 代码生成预览实体
 */
@Schema(title = "代码生成预览实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GeneratePreviewVO {

    @Schema(title = "文件名")
    private String filename;

    @Schema(title = "文件内容")
    private String fileContent;

}
