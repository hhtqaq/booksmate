package com.ecjtu.hht.booksmate.common;

import java.security.MessageDigest;

/**
 * SHA加密
 */
public class SHAUtil {
    /***
     * SHA加密 生成40位SHA码
     */
    public static String shaEncode(String data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        byte[] byteArray = data.getBytes("UTF-8");
        // md5Bytes的长度为20
        byte[] md5Bytes = sha.digest(byteArray);
        // 转换成16进制字符串
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            // 为了满足40位长度，当值小于16时需要先添加一位0(小于16的话用一位就能表示)
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}