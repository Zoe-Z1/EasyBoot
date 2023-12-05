package cn.easy.boot3.utils;

import cn.hutool.core.net.Ipv4Util;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author zoe
 *
 * @describe 获取ip工具类
 * @date 2023/7/23
 */
@Slf4j
public class IpUtil extends ServletUtil {
    private IpUtil(){}

    public static final String LOCAL_IP_8 = "0:0:0:0:0:0:0:1";

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = ServletUtil.getClientIP(request);
        if (StrUtil.isNotEmpty(ip) && LOCAL_IP_8.equals(ip)) {
            ip = Ipv4Util.LOCAL_IP;
        }
        return ip;
    }
}
