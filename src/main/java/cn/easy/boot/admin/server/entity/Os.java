package cn.easy.boot.admin.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "os对象", description = "操作系统")
public class Os {

    @ApiModelProperty(value = "系统名称")
    private String name;

    @ApiModelProperty(value = "系统内核")
    private String arch;

    @ApiModelProperty(value = "操作系统")
    private String family;

    @ApiModelProperty(value = "厂商")
    private String manufacturer;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "系统位数 32/64位")
    private Integer bitness;

    @ApiModelProperty(value = "系统启动时间")
    private Long systemBootTime;

    @ApiModelProperty(value = "系统运行时间")
    private Long systemUptime;

    @ApiModelProperty(value = "转化后的系统运行时间")
    private String systemUptimeStr;

    @ApiModelProperty(value = "运行进程数")
    private Integer processCount;

    @ApiModelProperty(value = "运行线程数")
    private Integer threadCount;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "版本号")
    private String buildNumber;

    @ApiModelProperty(value = "版本代号")
    private String codeName;

    @ApiModelProperty(value = "全称")
    private String allName;

}
