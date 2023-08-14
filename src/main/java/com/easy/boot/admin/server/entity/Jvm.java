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
 * @description JVM
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "jvm对象", description = "JVM")
public class Jvm {

    @ApiModelProperty(value = "JVM名称")
    private String name;

    @ApiModelProperty(value = "JVM规范名称")
    private String specName;

    @ApiModelProperty(value = "JVM最大内存/byte")
    private Long maxMemory;

    @ApiModelProperty(value = "JVM已分配内存/byte")
    private Long totalMemory;

    @ApiModelProperty(value = "JVM已分配内存中的剩余空间/byte")
    private Long freeMemory;

    @ApiModelProperty(value = "JVM最大可用内存/byte")
    private Long usableMemory;

    @ApiModelProperty(value = "JVM已使用内存/byte")
    private Long usedMemory;

    @ApiModelProperty(value = "JVM最大内存")
    private String maxMemoryStr;

    @ApiModelProperty(value = "JVM已分配内存")
    private String totalMemoryStr;

    @ApiModelProperty(value = "JVM已分配内存中的剩余空间")
    private String freeMemoryStr;

    @ApiModelProperty(value = "JVM最大可用内存")
    private String usableMemoryStr;

    @ApiModelProperty(value = "JVM已使用内存")
    private String usedMemoryStr;

    @ApiModelProperty(value = "JVM内存使用率")
    private String percent;

    @ApiModelProperty(value = "Java版本")
    private String javaVersion;

    @ApiModelProperty(value = "JDK路径")
    private String javaHome;
}
