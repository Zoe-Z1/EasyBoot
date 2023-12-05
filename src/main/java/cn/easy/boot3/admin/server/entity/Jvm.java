package cn.easy.boot3.admin.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "jvm对象", description = "JVM")
public class Jvm {

    @Schema(title = "JVM名称")
    private String name;

    @Schema(title = "JVM规范名称")
    private String specName;

    @Schema(title = "JVM最大内存/byte")
    private Long maxMemory;

    @Schema(title = "JVM已分配内存/byte")
    private Long totalMemory;

    @Schema(title = "JVM已分配内存中的剩余空间/byte")
    private Long freeMemory;

    @Schema(title = "JVM最大可用内存/byte")
    private Long usableMemory;

    @Schema(title = "JVM已使用内存/byte")
    private Long usedMemory;

    @Schema(title = "JVM最大内存")
    private String maxMemoryStr;

    @Schema(title = "JVM已分配内存")
    private String totalMemoryStr;

    @Schema(title = "JVM已分配内存中的剩余空间")
    private String freeMemoryStr;

    @Schema(title = "JVM最大可用内存")
    private String usableMemoryStr;

    @Schema(title = "JVM已使用内存")
    private String usedMemoryStr;

    @Schema(title = "JVM内存使用率")
    private String percent;

    @Schema(title = "Java版本")
    private String javaVersion;

    @Schema(title = "JDK路径")
    private String javaHome;
}
