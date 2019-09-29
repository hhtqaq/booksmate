package com.ecjtu.hht.booksmate.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: hht
 * @Date: 2019/4/7 13:16
 * @Description:
 */
public class DateUtil {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 创建时间的目录
     *
     * @param date     当前日期
     * @param fileroot 文件根路径
     * @return 文件夹路径
     */
    public static String createDateDir(Date date, String fileroot) {
        //格式化并转换String类型
        String tempPathDir = new SimpleDateFormat("yyyy/MM/dd").format(date);
        String path = fileroot + tempPathDir;
        //创建文件夹
        File f = new File(path);
        if (!f.exists())
            f.mkdirs();
        return tempPathDir;
    }
}
