package com.easy.boot3.common.generator;

import com.easy.boot3.admin.dataDict.entity.DataDict;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author zoe
 * @date 2023/9/23
 * @description 数据字典Sql生成实体
 */
@Data
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "数据字典Sql生成对象")
public class DictSql {

    @Schema(title = "数据字典域ID")
    private Long domainId;

    @Schema(title = "数据字典域编码")
    private String code;

    @Schema(title = "数据字典域名称")
    private String name;

    @Schema(title = "数据字典域备注")
    private String remarks;

    @Schema(title = "数据字典列表")
    private List<DataDict> list;
}
