package com.easy.boot.admin.redis.entity;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(title = "Redis对象", description = "缓存")
public class RedisVO {

    @Schema(title = "版本号")
    private String version;

    @Schema(title = "运行模式 standalone：单机，sentinel：主从，cluster：集群")
    private String mode;

    @Schema(title = "操作系统版本信息")
    private String os;

    @Schema(title = "架构 32/64 位")
    private Integer archBits;

    @Schema(title = "GCC版本")
    private String gccVersion;

//    @Schema(title = "端口号")
//    private Integer port;

    @Schema(title = "运行时间 秒")
    private Long seconds;

    @Schema(title = "运行时间 天")
    private Integer day;

    @Schema(title = "转换后的运行时间")
    private String runningTime;

    @Schema(title = "服务启动路径")
    private String executable;

    @Schema(title = "配置文件路径")
    private String configFile;

    @Schema(title = "客户端连接数")
    private Integer connectedClients;

    @Schema(title = "客户端阻塞数")
    private Integer blockedClients;

    @Schema(title = "分配内存大小 /byte")
    private Long usedMemory;

    @Schema(title = "分配内存大小")
    private String usedMemoryHuman;

    @Schema(title = "占用操作系统的资源大小 /byte")
    private Long usedMemoryRss;

    @Schema(title = "占用操作系统的资源大小")
    private String usedMemoryRssHuman;

    @Schema(title = "内存消耗峰值 /byte")
    private Long usedMemoryPeak;

    @Schema(title = "内存消耗峰值")
    private String usedMemoryPeakHuman;

    @Schema(title = "操作系统内存总大小")
    private String totalSystemMemoryHuman;

    @Schema(title = "lua占用内存")
    private String usedMemoryLuaHuman;

    @Schema(title = "最大内存限制")
    private String maxMemoryHuman;

    @Schema(title = "达到最大内存的淘汰策略  volatile-lru：只对设置了过期时间的key进行LRU（默认值）" +
            "allkeys-lru ： 删除lru算法的key" +
            "volatile-random：随机删除即将过期key" +
            "allkeys-random：随机删除" +
            "volatile-ttl ： 删除即将过期的" +
            "noeviction ：永不过期，返回错误")
    private String maxmemoryPolicy;

    @Schema(title = "RDB是否成功")
    private String rdbLastBgsaveStatus;

    @Schema(title = "是否开启了aof 0：关闭 1：开启")
    private Integer aofEnabled;

    @Schema(title = "新创建连接个数")
    private Integer totalConnectionsReceived;

    @Schema(title = "处理的命令数")
    private Long totalCommandsProcessed;

    @Schema(title = "当前QPS")
    private Long instantaneousOpsPerSec;

    @Schema(title = "网络入口流量字节数")
    private Long totalNetInputbytes;

    @Schema(title = "网络出口流量字节数")
    private Long totalNetOutputBytes;

    @Schema(title = "网络入口速率")
    private String instantaneousInputKbps;

    @Schema(title = "网络出口速率")
    private String instantaneousOutputKbps;

    @Schema(title = "拒绝的连接数")
    private Integer rejectedConnections;

    @Schema(title = "Key过期数量")
    private String expiredKeys;

    @Schema(title = "键值统计信息")
    private String keyspace;
}
