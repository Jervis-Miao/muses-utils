/**
 * Copyright 2022 All rights reserved.
 */

package cn.muses.utils.token;

import cn.muses.utils.encrypt.MD5Utils;

/**
 * @author jervis
 * @date 2022/6/22.
 */
public class TokenUtils {

    public static final String TOKEN_SEPARATOR = "-";
    public static final String TOKEN_KEY = "token";

    private TokenUtils() {}

    /**
     * 生成token
     * 
     * @param data
     * @return
     */
    public static String generator(String data) {
        return generator("", data, TOKEN_KEY);
    }

    /**
     * 生成token
     *
     * @param ext
     * @param data
     * @return
     */
    public static String generator(String ext, String data) {
        return generator(ext, data, TOKEN_KEY);
    }

    /**
     * 生成token
     *
     * @param data
     * @param ext
     * @param tokenKey
     * @return
     */
    public static String generator(String ext, String data, String tokenKey) {
        String md5Token = MD5Utils.md5(data + System.currentTimeMillis() + tokenKey);
        StringBuilder sb = new StringBuilder();
        sb.append(md5Token);
        sb.append(ext);
        return encodeToken(sb.toString());
    }

    /**
     * 验证token合法性
     *
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        String temp = token.substring(0, token.length() - 2);
        String sum = sumCheck(temp);
        return token.substring(token.length() - 2).equals(sum);
    }

    /**
     * 获取token扩展项
     *
     * @param token
     * @return
     */
    public static String getTokenExt(String token) {
        int l = token.length();
        return token.substring(32, l - 2);
    }

    /**
     * 加密token
     *
     * @param token
     * @return
     */
    private static String encodeToken(String token) {
        String sum = sumCheck(token);
        return token + sum;
    }

    /**
     * token sign
     *
     * @param text
     * @return
     */
    private static String sumCheck(String text) {
        byte[] b = text.getBytes();
        int sum = 0;
        int len = b.length;

        for (int i = 0; i < len; ++i) {
            sum += b[i];
        }

        if (sum > 255) {
            sum = ~sum;
            ++sum;
        }

        byte temp = (byte)(sum & 255);
        return String.format("%02x", temp);
    }

    public static void main(String[] args) {
        final String x = generator("11111");
        System.out.println(x);
        System.out.println(getTokenExt(x));
        System.out.println(checkToken(x));
    }
}
