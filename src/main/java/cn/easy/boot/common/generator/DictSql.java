package cn.easy.boot.common.generator;

import cn.easy.boot.admin.dataDict.entity.DataDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("数据字典Sql生成对象")
public class DictSql {

    @ApiModelProperty("数据字典域ID")
    private Long domainId;

    @ApiModelProperty("数据字典域编码")
    private String code;

    @ApiModelProperty("数据字典域名称")
    private String name;

    @ApiModelProperty("数据字典域备注")
    private String remarks;

    @ApiModelProperty("数据字典列表")
    private List<DataDict> list;
}
