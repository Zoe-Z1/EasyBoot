package cn.easy.boot3.admin.operationLog.entity;

import cn.easy.boot3.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * @author zoe
 * @date 2023/07/30
 * @description 操作日志 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "OperationLogQuery对象", description = "操作日志")
public class OperationLogQuery extends BasePageQuery {

    @Schema(title = "操作模块")
    private String operateModule;

    @Schema(title = "操作人")
    private String createUsername;

    @Schema(title = "操作类别")
    private String operateType;

    @Schema(title = "操作状态 SUCCESS：成功，FAIL：失败")
    private String operateStatus;

    @Schema(title = "处理时间/ms")
    private Long handleTime;

    @Range(min = 1, max = 6, message = "处理时间操作符不正确")
    @Schema(title = "处理时间操作符 1：≥ 2：≤ 3：＞ 4：＜ 5：= 6：≠")
    private Integer handleTimeOperator;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

}
