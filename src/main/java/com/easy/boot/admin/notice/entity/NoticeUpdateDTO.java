package com.easy.boot.admin.notice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;


/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告编辑实体
 */
@ApiModel(value = "公告编辑实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
public class NoticeUpdateDTO extends NoticeCreateDTO {

    @ApiModelProperty("id")
    @NotNull(message = "ID不能为空")
    private Long id;

}
