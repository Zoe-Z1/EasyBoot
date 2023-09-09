package com.easy.boot.admin.generate.entity;

import cn.hutool.core.date.DatePattern;
import com.easy.boot.common.base.BasePageQuery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zoe
 * @date 2023/9/7
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("数据库Table查询对象")
public class GenerateTableQuery extends BasePageQuery {

    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("表备注")
    private String comment;

    @ApiModelProperty(hidden = true, value = "数据库名")
    @JsonIgnore
    private String dbName;

    @ApiModelProperty(hidden = true, value = "表类型")
    @JsonIgnore
    private String tableType;

    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @ApiModelProperty("开始时间")
    private Date startTime;

    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @ApiModelProperty("结束时间")
    private Date endTime;
}
