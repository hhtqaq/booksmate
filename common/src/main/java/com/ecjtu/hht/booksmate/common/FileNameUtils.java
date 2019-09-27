package com.ecjtu.hht.booksmate.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

public class FileNameUtils {
    public static String generatorFileName(){
        Date date = new Date();
        String yyyyMMdd = DateFormatUtils.format(date, "yyyyMMdd");
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString().replaceAll("-", "");
        return yyyyMMdd+fileName;
    }
}
