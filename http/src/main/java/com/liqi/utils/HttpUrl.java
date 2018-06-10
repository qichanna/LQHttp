package com.liqi.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUrl {

    /**
     * utf-8编码
     */
    private static final String ENCODING = "utf-8";


    /**
     * 对post参数进行http编码处理
     *
     * @param map 要编码的数据
     * @return http 编码后的数据
     * @throws IOException IO异常
     */
    public static String encodedForm(Map<String, String> map) throws IOException {
        if (map == null || map.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        return encoded(builder, map);
    }


    /**
     * 对get请求进行http编码处理
     *
     * @param url 请求的url
     * @param map 要编码的数据
     * @return http 编码后请求的地址
     * @throws IOException IO异常
     */
    public static String encodedQuery(String url, Map<String, String> map) throws IOException {
        if (map == null || map.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(url).append("?");
        return encoded(builder, map);
    }


    /**
     * http编码处理
     *
     * @param builder 数据缓存
     * @param map     要编码的数据
     * @return http编码后数据
     * @throws IOException IO异常
     */
    private static String encoded(StringBuilder builder, Map<String, String> map) throws IOException {
        int index = 0;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), ENCODING));
            if (index != map.size() - 1) {
                builder.append("&");
            }
            index++;
        }
        return builder.toString();
    }
}
