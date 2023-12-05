package cn.easy.boot.admin.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/12
 * @description 内存
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "memory对象", description = "内存")
public class Memory {

    @ApiModelProperty(value = "总内存/byte")
    private Long total;

    @ApiModelProperty(value = "可用内存/byte")
    private Long available;

    @ApiModelProperty(value = "已用内存/byte")
    private Long used;

    @ApiModelProperty(value = "单页内存/byte")
    private Long pageSize;

    @ApiModelProperty(value = "总内存")
    private String totalStr;

    @ApiModelProperty(value = "可用内存")
    private String availableStr;

    @ApiModelProperty(value = "已用内存")
    private String usedStr;

    @ApiModelProperty(value = "单页内存")
    private String pageSizeStr;

    @ApiModelProperty(value = "内存使用率")
    private String percent;
}
