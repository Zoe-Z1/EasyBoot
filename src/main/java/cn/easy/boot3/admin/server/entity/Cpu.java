package cn.easy.boot3.admin.server.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "cpu对象", description = "处理器")
public class Cpu {

    @Schema(title = "CPU名称")
    private String name;

    @Schema(title = "CPU供应商")
    private String vendor;

    @Schema(title = "CPU系统架构")
    private String microarchitecture;

    @Schema(title = "CPU型号信息")
    private String cpuModel;

    @Schema(title = "CPU核心数")
    private Integer cpuNum;

    @Schema(title = "CPU总的使用数")
    private Double toTal;

    @Schema(title = "CPU系统使用率")
    private Double sys;

    @Schema(title = "CPU用户使用率")
    private Double user;

    @Schema(title = "CPU当前等待率")
    private Double wait;

    @Schema(title = "CPU当前空闲率")
    private Double free;

}
