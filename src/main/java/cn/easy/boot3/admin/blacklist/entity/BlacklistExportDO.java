package cn.easy.boot3.admin.blacklist.entity;

import cn.easy.boot3.common.excel.converter.IntegerBlacklistTypeToStringConvert;
import cn.easy.boot3.common.excel.converter.LongTimeToStingTimeConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "黑名单导出DO")
public class BlacklistExportDO {

    @ExcelProperty(value = "拉黑类型", converter = IntegerBlacklistTypeToStringConvert.class)
    @Schema(title = "类型 1：账号 2：IP")
    private Integer type;

    @ColumnWidth(40)
    @ExcelProperty(value = "关联信息")
    @Schema(title = "关联数据  IP地址或用户账号")
    private String relevanceData;

    @ColumnWidth(20)
    @ExcelProperty(value = "拉黑开始时间", converter = LongTimeToStingTimeConvert.class)
    @Schema(title = "创建时间")
    private Long startTime;

    @ColumnWidth(20)
    @ExcelProperty(value = "拉黑结束时间")
    @Schema(title = "结束时间")
    private String endTime;

    @ExcelProperty(value = "操作人")
    @Schema(title = "操作人")
    private String createUsername;
}
