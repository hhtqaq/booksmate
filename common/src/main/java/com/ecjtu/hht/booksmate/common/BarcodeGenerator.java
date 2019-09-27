package com.ecjtu.hht.booksmate.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Auther: hht
 * @Date: 2019/3/17 17:53
 * @Description:
 * 条形码生成器
 */
public class BarcodeGenerator {
    /**
     * 生成条形码
     * @param date  录入日期
     * @param type   类型id
     * @return
     */
    public static String generatorCode(Date date,Integer type){
        //前8位录入日期
        SimpleDateFormat smt=new SimpleDateFormat("yyyyMMdd");
        Random random = new Random();
        StringBuilder code=new StringBuilder(smt.format(date));
        //类型2位
        code=type<10?code.append("0"+type): code.append(type);
        //随机数3位
        for(int i=0;i<5;i++){
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
