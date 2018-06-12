package com.liqi.http;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * HttpDNS查询接口
 *
 */

public interface HttpDns {

    /**
     * 通过域名获取对应的IP地址
     * @param hostname 域名
     * @return 对应的IP地址
     * @throws UnknownHostException
     */
    List<InetAddress> lookup(String hostname) throws UnknownHostException;
}
