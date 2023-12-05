package cn.easy.boot3.admin.dataDictDomain.entity;

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
 * @description 数据字典域 Query
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "DataDictDomainQuery对象", description = "数据字典域")
public class DataDictDomainQuery extends BasePageQuery {

    @Schema(title = "字典域编码")
    private String code;

    @Schema(title = "字典域名称")
    private String name;

    @Schema(title = "字典域状态 1：正常 2：禁用")
    private Integer status;

    @Schema(title = "开始时间")
    private Long startTime;

    @Schema(title = "结束时间")
    private Long endTime;

}
