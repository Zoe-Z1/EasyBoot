package cn.easy.boot.admin.redis.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zoe
 * @date 2023/8/13
 * @description
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Redis对象", description = "缓存")
public class RedisVO {

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "运行模式 standalone：单机，sentinel：主从，cluster：集群")
    private String mode;

    @ApiModelProperty(value = "操作系统版本信息")
    private String os;

    @ApiModelProperty(value = "架构 32/64 位")
    private Integer archBits;

    @ApiModelProperty(value = "GCC版本")
    private String gccVersion;

//    @ApiModelProperty(value = "端口号")
//    private Integer port;

    @ApiModelProperty(value = "运行时间 秒")
    private Long seconds;

    @ApiModelProperty(value = "运行时间 天")
    private Integer day;

    @ApiModelProperty(value = "转换后的运行时间")
    private String runningTime;

    @ApiModelProperty(value = "服务启动路径")
    private String executable;

    @ApiModelProperty(value = "配置文件路径")
    private String configFile;

    @ApiModelProperty(value = "客户端连接数")
    private Integer connectedClients;

    @ApiModelProperty(value = "客户端阻塞数")
    private Integer blockedClients;

    @ApiModelProperty(value = "分配内存大小 /byte")
    private Long usedMemory;

    @ApiModelProperty(value = "分配内存大小")
    private String usedMemoryHuman;

    @ApiModelProperty(value = "占用操作系统的资源大小 /byte")
    private Long usedMemoryRss;

    @ApiModelProperty(value = "占用操作系统的资源大小")
    private String usedMemoryRssHuman;

    @ApiModelProperty(value = "内存消耗峰值 /byte")
    private Long usedMemoryPeak;

    @ApiModelProperty(value = "内存消耗峰值")
    private String usedMemoryPeakHuman;

    @ApiModelProperty(value = "操作系统内存总大小")
    private String totalSystemMemoryHuman;

    @ApiModelProperty(value = "lua占用内存")
    private String usedMemoryLuaHuman;

    @ApiModelProperty(value = "最大内存限制")
    private String maxMemoryHuman;

    @ApiModelProperty(value = "达到最大内存的淘汰策略  volatile-lru：只对设置了过期时间的key进行LRU（默认值）" +
            "allkeys-lru ： 删除lru算法的key" +
            "volatile-random：随机删除即将过期key" +
            "allkeys-random：随机删除" +
            "volatile-ttl ： 删除即将过期的" +
            "noeviction ：永不过期，返回错误")
    private String maxmemoryPolicy;

    @ApiModelProperty(value = "RDB是否成功")
    private String rdbLastBgsaveStatus;

    @ApiModelProperty(value = "是否开启了aof 0：关闭 1：开启")
    private Integer aofEnabled;

    @ApiModelProperty(value = "新创建连接个数")
    private Integer totalConnectionsReceived;

    @ApiModelProperty(value = "处理的命令数")
    private Long totalCommandsProcessed;

    @ApiModelProperty(value = "当前QPS")
    private Long instantaneousOpsPerSec;

    @ApiModelProperty(value = "网络入口流量字节数")
    private Long totalNetInputbytes;

    @ApiModelProperty(value = "网络出口流量字节数")
    private Long totalNetOutputBytes;

    @ApiModelProperty(value = "网络入口速率")
    private String instantaneousInputKbps;

    @ApiModelProperty(value = "网络出口速率")
    private String instantaneousOutputKbps;

    @ApiModelProperty(value = "拒绝的连接数")
    private Integer rejectedConnections;

    @ApiModelProperty(value = "Key过期数量")
    private String expiredKeys;

    @ApiModelProperty(value = "键值统计信息")
    private String keyspace;
}
