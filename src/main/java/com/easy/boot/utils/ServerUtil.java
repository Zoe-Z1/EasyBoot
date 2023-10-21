package com.easy.boot.utils;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataSizeUtil;
import cn.hutool.core.io.unit.DataUnit;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.easy.boot.admin.server.entity.*;
import lombok.extern.slf4j.Slf4j;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

/**
 * @author zoe
 * @date 2023/8/12
 * @description 服务器信息工具类
 */
@Slf4j
public class ServerUtil extends OshiUtil {

    private ServerUtil() {
    }


    /**
     * 获取cpu信息
     *
     * @return
     */
    public static Cpu getMyCpu() {
        CentralProcessor centralProcessor = getProcessor();
        CentralProcessor.ProcessorIdentifier identifier = centralProcessor.getProcessorIdentifier();
        Cpu cpu = BeanUtil.copyBean(getCpuInfo(), Cpu.class);
        cpu.setName(identifier.getName());
        cpu.setVendor(identifier.getVendor());
        cpu.setMicroarchitecture(identifier.getMicroarchitecture());
        return cpu;
    }

    /**
     * 获取操作系统信息
     *
     * @return
     */
    public static Os getMyOs() {
        OperatingSystem operatingSystem = getOs();
        Os os = BeanUtil.copyBean(operatingSystem, Os.class);
        os.setModel(getSystem().getModel());
        os.setVersion(operatingSystem.getVersionInfo().getVersion());
        os.setBuildNumber(operatingSystem.getVersionInfo().getBuildNumber());
        os.setCodeName(operatingSystem.getVersionInfo().getCodeName());
        os.setAllName(operatingSystem.getVersionInfo().toString());
        String systemUptimeStr = DateUtil.formatBetween(os.getSystemUptime() * 1000, BetweenFormatter.Level.SECOND);
        os.setSystemUptimeStr(systemUptimeStr);
        Properties props = System.getProperties();
        os.setName(props.getProperty("os.name"));
        os.setArch(props.getProperty("os.arch"));
        return os;
    }

    /**
     * 获取内存信息
     *
     * @return
     */
    public static Memory getMyMemory() {
        GlobalMemory globalMemory = getMemory();
        Memory memory = Memory.builder()
                .total(globalMemory.getTotal())
                .available(globalMemory.getAvailable())
                .used(globalMemory.getTotal() - globalMemory.getAvailable())
                .pageSize(globalMemory.getPageSize())
                .totalStr(DataSizeUtil.format(globalMemory.getTotal()))
                .availableStr(DataSizeUtil.format(globalMemory.getAvailable()))
                .pageSizeStr(DataSizeUtil.format(globalMemory.getPageSize()))
                .build();
        memory.setUsedStr(DataSizeUtil.format(memory.getUsed()));
        String percent = NumberUtil.div(memory.getUsed(), memory.getTotal(), 2)
                .multiply(new BigDecimal("100")).toString();
        memory.setPercent(percent);
        return memory;
    }

    /**
     * 获取JVM内存信息
     *
     * @return
     */
    public static Jvm getMyJvm() {
        Runtime runtime = Runtime.getRuntime();
        Jvm jvm = Jvm.builder()
                .maxMemory(runtime.maxMemory())
                .totalMemory(runtime.totalMemory())
                .freeMemory(runtime.freeMemory())
                .maxMemoryStr(DataSizeUtil.format(runtime.maxMemory()))
                .totalMemoryStr(DataSizeUtil.format(runtime.totalMemory()))
                .freeMemoryStr(DataSizeUtil.format(runtime.freeMemory()))
                .build();
        Long usableMemory = jvm.getMaxMemory() - jvm.getTotalMemory() + jvm.getFreeMemory();
        Long usedMemory = jvm.getTotalMemory() - jvm.getFreeMemory();
        jvm.setUsableMemory(usableMemory);
        jvm.setUsableMemoryStr(DataSizeUtil.format(usableMemory));
        jvm.setUsedMemory(usedMemory);
        jvm.setUsedMemoryStr(DataSizeUtil.format(usedMemory));
        String percent = NumberUtil.div(usedMemory, jvm.getTotalMemory(), 4)
                .multiply(new BigDecimal("100")).toString();
        jvm.setPercent(percent);

        Properties props = System.getProperties();
        jvm.setJavaVersion(props.getProperty("java.version"));
        jvm.setJavaHome(props.getProperty("java.home"));

        RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
        jvm.setName(mxBean.getVmName() + " " + mxBean.getVmVersion() + " " + mxBean.getVmVendor());
        jvm.setSpecName(mxBean.getSpecName() + " " + mxBean.getSpecVersion() + " " + mxBean.getSpecVendor());
        return jvm;
    }

    /**
     * 获取磁盘信息
     *
     * @return
     */
    public static Disk getMyDisk() {
        String path = "/";
        File file = new File(path);
        if (file.exists()) {
            Long totalSpace = file.getTotalSpace();
            long freeSpace = file.getFreeSpace();
            long usableSpace = file.getUsableSpace();
            Long usedSpace = totalSpace - freeSpace;
            String percent = NumberUtil.div(usedSpace, totalSpace, 4)
                    .multiply(new BigDecimal("100")).toString();

            return Disk.builder()
                    .path(path)
                    .totalSpace(totalSpace)
                    .freeSpace(freeSpace)
                    .usableSpace(usableSpace)
                    .usedSpace(usedSpace)
                    .totalSpaceStr(DataSizeUtil.format(totalSpace))
                    .freeSpaceStr(DataSizeUtil.format(freeSpace))
                    .usableSpaceStr(DataSizeUtil.format(usableSpace))
                    .usedSpaceStr(DataSizeUtil.format(usedSpace))
                    .percent(percent)
                    .build();
        }
        return null;
    }

    /**
     * 获取网络信息
     *
     * @return
     */
    public static Network getMyNetwork() {
        List<NetworkIF> networkIFs = getNetworkIFs();
        NetworkIF networkIF = networkIFs.get(networkIFs.size() - 1);
        long startSent = networkIF.getBytesSent();
        long startRecv = networkIF.getBytesRecv();
        long startPacketsSent = networkIF.getPacketsSent();
        long startPacketsRecv = networkIF.getPacketsRecv();
        long startInErrors = networkIF.getInErrors();
        long startOutErrors = networkIF.getOutErrors();
        long startInDrops = networkIF.getInDrops();
        long startTime = networkIF.getTimeStamp();
        ThreadUtil.sleep(1000);
        networkIF.updateAttributes();
        long endSent = networkIF.getBytesSent();
        long endRecv = networkIF.getBytesRecv();
        long endPacketsSent = networkIF.getPacketsSent();
        long endPacketsRecv = networkIF.getPacketsRecv();
        long endInErrors = networkIF.getInErrors();
        long endOutErrors = networkIF.getOutErrors();
        long endInDrops = networkIF.getInDrops();
        long endTime = networkIF.getTimeStamp();

        long sent = endSent - startSent;
        long recv = endRecv - startRecv;
        long packetsSent = endPacketsSent - startPacketsSent;
        long packetsRecv = endPacketsRecv - startPacketsRecv;
        long inErrors = endInErrors - startInErrors;
        long outErrors = endOutErrors - startOutErrors;
        long inDrops = endInDrops - startInDrops;
        long speed = networkIF.getSpeed() / 8;
        long time = endTime - startTime;

        recv = recv / time * 1000;
        sent = sent / time * 1000;

        return Network.builder()
                .recv(recv)
                .sent(sent)
                .packetsRecv(packetsRecv)
                .packetsSent(packetsSent)
                .inErrors(inErrors)
                .outErrors(outErrors)
                .inDrops(inDrops)
                .speed(speed)
                .recvStr(formatData(recv) + "/s")
                .sentStr(formatData(sent)  + "/s")
                .speedStr(formatData(speed / 8)  + "/s")
                .build();
    }

    /**
     * 格式化输出大小 B/KB/MB...
     *
     * @param size
     * @return
     */
    public static String formatData(long size) {
        if (size <= 0L) {
            return "0B";
        } else {
            int digitGroups = Math.min(DataUnit.UNIT_NAMES.length - 1, (int) (Math.log10((double) size) / Math.log10(1024.0D)));
            return (new DecimalFormat("#,##0.##")).format((double) size / Math.pow(1024.0D, (double) digitGroups)) + " " + DataUnit.UNIT_NAMES[digitGroups];
        }
    }

}
