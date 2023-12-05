package cn.easy.boot.admin.post.entity;

import cn.easy.boot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

/**
 * @author zoe
 * @date 2023/07/29
 * @description 岗位 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "PostQuery对象", description = "岗位")
public class PostQuery extends BasePageQuery {

    @ApiModelProperty("岗位编码")
    private String code;

    @ApiModelProperty("岗位名称")
    private String name;

    @Range(min = 1, max = 2, message = "岗位状态不正确")
    @ApiModelProperty("岗位状态 1：正常 2：禁用")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

}
