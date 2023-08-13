package com.fast.start.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @describe 列表查询分页入参基类
 * @author zoe
 * @date 2023/7/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class BasePageQuery implements Serializable {

    private static final long serialVersionUID = 297152698092681735L;

    @ApiModelProperty(required = true, value = "页码数")
    @NotNull(message = "页码数不能为空")
    @Min(value = 0, message = "页码数不能低于{value}")
    private Integer pageNum;

    @ApiModelProperty(required = true, value = "每页条数")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 5, message = "每页条数不能低于{value}")
    @Max(value = 200, message = "每页条数不能超过{value}")
    private Integer pageSize;
}
