package cn.easy.boot3.admin.notice.entity;

import cn.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;


/**
 * @author zoe
 * @date 2023/10/22
 * @description 公告查询实体
 */
@Schema(title = "公告查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NoticeQuery extends BasePageQuery {

    @Schema(title = "公告状态 #1：正常，2：禁用")
    private Integer status;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

}
