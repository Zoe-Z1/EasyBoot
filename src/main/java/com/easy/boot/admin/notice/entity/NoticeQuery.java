package com.easy.boot.admin.notice.entity;

import com.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;


/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告查询实体
 */
@ApiModel(value = "公告查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NoticeQuery extends BasePageQuery {

    @ApiModelProperty("公告状态 #1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

}
