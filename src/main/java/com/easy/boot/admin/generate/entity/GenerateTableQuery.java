package com.easy.boot.admin.generate.entity;

import cn.hutool.core.date.DatePattern;
import com.easy.boot.common.base.BasePageQuery;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "数据库Table查询对象")
public class GenerateTableQuery extends BasePageQuery {

    @Schema(title = "表名")
    private String tableName;

    @Schema(title = "表备注")
    private String comment;

    @Schema(hidden = true, title = "数据库名")
    @JsonIgnore
    private String dbName;

    @Schema(hidden = true, title = "表类型")
    @JsonIgnore
    private String tableType;

    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @Schema(title = "开始时间")
    private Date startTime;

    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @Schema(title = "结束时间")
    private Date endTime;
}
