package cn.easy.boot.admin.generateColumn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;


/**
 * @author zoe
 * @date 2023/09/15
 * @description 代码生成列配置查询实体
 */
@TableName("generate_column")
@ApiModel(value = "代码生成列配置查询实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode
public class GenerateColumnQuery {


    @NotEmpty(message = "表名称不能为空")
    @ApiModelProperty(required = true, value = "表名称")
    private String tableName;

}
