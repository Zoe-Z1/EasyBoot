package cn.easy.boot.admin.blacklist.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import cn.easy.boot.common.excel.converter.IntegerBlacklistTypeToStringConvert;
import cn.easy.boot.common.excel.converter.LongTimeToStingTimeConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/08/01
* @description 黑名单导出 实体
*/
@Data
@ColumnWidth(20)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("黑名单导出DO")
public class BlacklistExportDO {

    @ExcelProperty(value = "拉黑类型", converter = IntegerBlacklistTypeToStringConvert.class)
    @ApiModelProperty("类型 1：账号 2：IP")
    private Integer type;

    @ColumnWidth(40)
    @ExcelProperty(value = "关联信息")
    @ApiModelProperty("关联数据  IP地址或用户账号")
    private String relevanceData;

    @ColumnWidth(20)
    @ExcelProperty(value = "拉黑开始时间", converter = LongTimeToStingTimeConvert.class)
    @ApiModelProperty("创建时间")
    private Long startTime;

    @ColumnWidth(20)
    @ExcelProperty(value = "拉黑结束时间")
    @ApiModelProperty("结束时间")
    private String endTime;

    @ExcelProperty(value = "操作人")
    @ApiModelProperty("操作人")
    private String createUsername;
}
