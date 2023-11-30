package com.easy.boot3.admin.notice.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告编辑实体
 */
@Schema(title = "公告编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class NoticeUpdateDTO extends NoticeCreateDTO {

    @Schema(title = "id")
    @NotNull(message = "ID不能为空")
    private Long id;

}
