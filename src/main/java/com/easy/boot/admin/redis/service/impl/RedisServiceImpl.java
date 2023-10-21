package com.easy.boot.admin.redis.service.impl;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import com.easy.boot.admin.redis.entity.RedisVO;
import com.easy.boot.admin.redis.service.RedisService;
import com.easy.boot.common.redis.EasyRedisManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @author zoe
 * @date 2023/8/13
 * @description
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private EasyRedisManager redisManager;

    @Value("${spring.redis.database}")
    private Integer database;

    @Override
    public RedisVO getRedisInfo() {
        Properties info = redisManager.info();
        String version = info.getProperty("redis_version");
        String mode = info.getProperty("redis_mode");
        String os = info.getProperty("os");
        String archBits = info.getProperty("arch_bits");
        String gccVersion = info.getProperty("gcc_version");
        String port = info.getProperty("tcp_port");
        String seconds = info.getProperty("uptime_in_seconds");
        String days = info.getProperty("uptime_in_days");
        String executable = info.getProperty("executable");
        String configFile = info.getProperty("config_file");
        String connectedClients = info.getProperty("connected_clients");
        String blockedClients = info.getProperty("blocked_clients");
        String usedMemory = info.getProperty("used_memory");
        String usedMemoryHuman = info.getProperty("used_memory_human");
        String usedMemoryRss = info.getProperty("used_memory_rss");
        String usedMemoryRssHuman = info.getProperty("used_memory_rss_human");
        String usedMemoryPeak = info.getProperty("used_memory_peak");
        String usedMemoryPeakHuman = info.getProperty("used_memory_peak_human");
        String totalSystemMemoryHuman = info.getProperty("total_system_memory_human");
        String usedMemoryLuaHuman = info.getProperty("used_memory_lua_human");
        String maxMemoryHuman = info.getProperty("maxmemory_human");
        String maxmemoryPolicy = info.getProperty("maxmemory_policy");
        String rdbLastBgsaveStatus = info.getProperty("rdb_last_bgsave_status");
        String aofEnabled = info.getProperty("aof_enabled");
        String totalConnectionsReceived = info.getProperty("total_connections_received");
        String totalCommandsProcessed = info.getProperty("total_commands_processed");
        String instantaneousOpsPerSec = info.getProperty("instantaneous_ops_per_sec");
        String totalNetInputbytes = info.getProperty("total_net_input_bytes");
        String totalNetOutputBytes = info.getProperty("total_net_output_bytes");
        String instantaneousInputKbps = info.getProperty("instantaneous_input_kbps");
        String instantaneousOutputKbps = info.getProperty("instantaneous_output_kbps");
        String rejectedConnections = info.getProperty("rejected_connections");
        String expiredKeys = info.getProperty("expired_keys");
        String keyspace = info.getProperty("db" + database);
        String runningTime = DateUtil.formatBetween(Long.parseLong(seconds) * 1000, BetweenFormatter.Level.SECOND);


        return RedisVO.builder()
                .version(version)
                .mode(mode)
                .os(os)
                .archBits(Integer.valueOf(archBits))
                .gccVersion(gccVersion)
//                .port(Integer.valueOf(port))
                .seconds(Long.valueOf(seconds))
                .day(Integer.valueOf(days))
                .runningTime(runningTime)
                .executable(executable)
                .configFile(configFile)
                .connectedClients(Integer.valueOf(connectedClients))
                .blockedClients(Integer.valueOf(blockedClients))
                .usedMemory(Long.valueOf(usedMemory))
                .usedMemoryHuman(usedMemoryHuman)
                .usedMemoryRss(Long.valueOf(usedMemoryRss))
                .usedMemoryRssHuman(usedMemoryRssHuman)
                .usedMemoryPeak(Long.valueOf(usedMemoryPeak))
                .usedMemoryPeakHuman(usedMemoryPeakHuman)
                .totalSystemMemoryHuman(totalSystemMemoryHuman)
                .usedMemoryLuaHuman(usedMemoryLuaHuman)
                .maxMemoryHuman(maxMemoryHuman)
                .maxmemoryPolicy(maxmemoryPolicy)
                .rdbLastBgsaveStatus(rdbLastBgsaveStatus)
                .aofEnabled(Integer.valueOf(aofEnabled))
                .totalConnectionsReceived(Integer.valueOf(totalConnectionsReceived))
                .totalCommandsProcessed(Long.valueOf(totalCommandsProcessed))
                .instantaneousOpsPerSec(Long.valueOf(instantaneousOpsPerSec))
                .totalNetInputbytes(Long.valueOf(totalNetInputbytes))
                .totalNetOutputBytes(Long.valueOf(totalNetOutputBytes))
                .instantaneousInputKbps(instantaneousInputKbps + "KB/s")
                .instantaneousOutputKbps(instantaneousOutputKbps + "KB/s")
                .rejectedConnections(Integer.valueOf(rejectedConnections))
                .expiredKeys(expiredKeys)
                .keyspace(keyspace)
                .build();
    }
}
