package com.easy.boot.admin.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/12
 * @description 操作系统
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "os对象", description = "操作系统")
public class Os {

    @Schema(title = "系统名称")
    private String name;

    @Schema(title = "系统内核")
    private String arch;

    @Schema(title = "操作系统")
    private String family;

    @Schema(title = "厂商")
    private String manufacturer;

    @Schema(title = "型号")
    private String model;

    @Schema(title = "系统位数 32/64位")
    private Integer bitness;

    @Schema(title = "系统启动时间")
    private Long systemBootTime;

    @Schema(title = "系统运行时间")
    private Long systemUptime;

    @Schema(title = "转化后的系统运行时间")
    private String systemUptimeStr;

    @Schema(title = "运行进程数")
    private Integer processCount;

    @Schema(title = "运行线程数")
    private Integer threadCount;

    @Schema(title = "版本")
    private String version;

    @Schema(title = "版本号")
    private String buildNumber;

    @Schema(title = "版本代号")
    private String codeName;

    @Schema(title = "全称")
    private String allName;

}
