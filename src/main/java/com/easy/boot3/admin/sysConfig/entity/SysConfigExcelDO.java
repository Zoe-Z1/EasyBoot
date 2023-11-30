package com.easy.boot3.admin.sysConfig.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author zoe
* @date 2023/07/29
* @description 系统配置 实体
*/
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_config")
@Schema(title = "SysConfig对象", description = "系统配置")
public class SysConfigExcelDO extends SysConfig {

    @ColumnWidth(20)
    @ExcelProperty(value = "系统配置域编码")
    @Schema(title = "系统配置域编码")
    private String domainCode;

}
