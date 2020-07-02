/*
 * Copyright 2013 Focus Technology, Co., Ltd. All rights reserved.
 */
package cn.muses.utils.ip;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author miaoqiang
 * @date 2020/4/8.
 */
public class IpUtils {

    /**
     * 获取本机的ip
     *
     * @return
     */
    public static String getIp() {
        Enumeration<NetworkInterface> e1;
        try {
            e1 = NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = e1.nextElement();
                Enumeration<InetAddress> e2 = ni.getInetAddresses();
                if (!e2.hasMoreElements()) {
                    continue;
                }
                while (e2.hasMoreElements()) {
                    InetAddress ia = e2.nextElement();
                    if (ia instanceof Inet6Address || ia.isLoopbackAddress()) {
                        continue;
                    } else if (ia.isSiteLocalAddress()) {
                        String ip = ia.getHostAddress();
                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException("获取ip地址失败");
        }
        return null;
    }

}
