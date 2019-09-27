package com.ecjtu.hht.booksmate.common;

/**
 * 验证码生成工具类
 *
 * @author hht
 * @date 2019/8/9 18:13
 */
public class CodeGeneratorUtil {
    public static int generatorCode() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }
}
