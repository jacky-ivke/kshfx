package com.kshfx.core.utils;


import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName: IPUtils
 * @Author: jacky
 * @Version: V1.0
 */
public class IPUtils {
    private IPUtils() {
    }

    /**
     * @Title: getIpAddr @Description: 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是: 1、有可能用户使用了代理软件方式避免真实IP地址
     * 2、如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值 @param request @return String @throws
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if ((ip != null) && (ip.length() != 0) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();

            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }

        return ip;
    }

    /**
     * @Title: ipToLong @Description: ip地址转成long型数字,将IP地址转化成整数的方法如下 1、通过String的split方法按.分隔得到4个长度的数组
     * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1 @param strIp @return long @throws
     */
    public static long ipToLong(String strIp) {
        long result = 0L;
        if (StringUtils.isEmpty(strIp)) {
            return result;
        }
        if (StringUtils.isEmpty(strIp)) {
            return result;
        }
        String[] ip = strIp.split("\\.");
        if (!ObjectUtils.isEmpty(ip)) {
            result = (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8)
                    + Long.parseLong(ip[3]);
        }
        return result;
    }
}
