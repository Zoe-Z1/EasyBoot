package com.easy.boot.admin.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/12
 * @description cpu
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "cpu对象", description = "处理器")
public class Cpu {

    @ApiModelProperty(value = "CPU名称")
    private String name;

    @ApiModelProperty(value = "CPU供应商")
    private String vendor;

    @ApiModelProperty(value = "CPU系统架构")
    private String microarchitecture;

    @ApiModelProperty(value = "CPU型号信息")
    private String cpuModel;

    @ApiModelProperty(value = "CPU核心数")
    private Integer cpuNum;

    @ApiModelProperty(value = "CPU总的使用数")
    private Double toTal;

    @ApiModelProperty(value = "CPU系统使用率")
    private Double sys;

    @ApiModelProperty(value = "CPU用户使用率")
    private Double user;

    @ApiModelProperty(value = "CPU当前等待率")
    private Double wait;

    @ApiModelProperty(value = "CPU当前空闲率")
    private Double free;

}
