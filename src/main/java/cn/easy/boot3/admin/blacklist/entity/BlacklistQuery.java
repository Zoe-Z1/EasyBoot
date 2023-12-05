package cn.easy.boot3.admin.blacklist.entity;

import cn.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/08/01
 * @description 黑名单 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "BlacklistQuery对象", description = "黑名单")
public class BlacklistQuery extends BasePageQuery {

    @Schema(title = "类型 1：账号 2：IP")
    private Integer type;

    @Schema(title = "关联数据  IP地址或用户账号")
    private String relevanceData;

    @Schema(title = "操作人账号")
    private String createUsername;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

    @Schema(title = "拉黑状态 1：拉黑中 2：已失效")
    private Integer status;

}
