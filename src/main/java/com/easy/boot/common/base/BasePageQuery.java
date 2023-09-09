package com.easy.boot.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BasePageQuery implements Serializable {

    private static final long serialVersionUID = 297152698092681735L;

    @ApiModelProperty(required = true, value = "页码数")
    @NotNull(message = "页码数不能为空")
    @Min(value = 1, message = "页码数不能低于{value}")
    private Long pageNum;

    @ApiModelProperty(required = true, value = "每页条数")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 5, message = "每页条数不能低于{value}")
    @Max(value = 200, message = "每页条数不能超过{value}")
    private Long pageSize;

    public void setPageNum(Integer pageNum) {
        this.pageNum = Long.valueOf(pageNum);
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = Long.valueOf(pageSize);
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
