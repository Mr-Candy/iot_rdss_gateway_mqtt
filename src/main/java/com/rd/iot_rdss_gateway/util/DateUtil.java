package com.rd.iot_rdss_gateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>
 * [功能描述]：日期转换工具
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 老薛
 * @version 1.0, 2016年3月18日下午2:50:54
 * @since EnvDust 1.0.0
 */
public class DateUtil {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DateUtil";
    /**
     * 声明日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 标准日期时间类型
     */
    public static String DATA_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认转换时间戳日期格式(yyyy-MM-dd)
     */
    public static final String DEFUALT_TIME = "yyyy-MM-dd";

    public static final String DEFUALT_DAY = "yyyy/MM/dd";

    /**
     * 默认转换时间戳日期格式(yyyy-MM-dd HH:mm:ss)
     */
    public static final String DEFUALT_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认转换时间戳日期格式(yyyy-MM-dd)
     */
    public static final String YEAR_MONTH = "yyyy-MM";

    /**
     * 序列日期时间类型
     */
    public static String DATA_TIME_SER = "yyyyMMddHHmmss";

    /**
     * <p>
     * [功能描述]：将时间戳转换为标准时间
     * </p>
     *
     * @param timestamp
     * @param format
     * @return
     * @author 老薛, 2016年3月18日下午2:57:37
     * @since EnvDust 1.0.0
     */
    public static String TimestampToString(Timestamp timestamp, String format) {
        try {
            if (timestamp != null) {
                DateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.format(timestamp);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * <p>
     * [功能描述]：将字符串转换成时间戳（yyyy-MM-dd）
     * </p>
     *
     * @param datetime
     * @return
     * @author 老薛, 2016年3月30日下午3:08:50
     * @since EnvDust 1.0.0
     */
    public static Timestamp StringToTimestamp(String datetime) {
        try {
            if (datetime != null && !datetime.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat(DEFUALT_SECOND);
                Date date = sdf.parse(datetime);
                return new Timestamp(date.getTime());
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * local时间转换成UTC时间
     *
     * @param localTime
     * @return
     */
    public static Date localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate = null;
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    /**
     * utc时间转成local时间
     *
     * @param utcTime
     * @return
     */
    public static Timestamp utcToLocal(String utcTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
            return new Timestamp(locatlDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>
     * [功能描述]：将字符串转换成时间戳（yyyy-MM-dd HH:mm:ss）
     * </p>
     *
     * @param datetime
     * @return
     * @author 老薛, 2016年3月30日下午3:08:50
     * @since EnvDust 1.0.0
     */
    public static Timestamp StringToTimestampSecond(String datetime) {
        try {
            if (datetime != null && !datetime.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat(DEFUALT_SECOND);
                Date date = sdf.parse(datetime);
                return new Timestamp(date.getTime());
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * 按指定参数将字符串转换成时间戳
     *
     * @param datetime
     * @param format
     * @return
     */
    public static Timestamp StringToTimestampFormat(String datetime, String format) {
        try {
            if (datetime != null && !datetime.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                Date date = sdf.parse(datetime);
                return new Timestamp(date.getTime());
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * <p>
     * [功能描述]：获取系统时间
     * </p>
     *
     * @param millionsecond ：当前系统时间减去此参数
     * @return
     * @author 老薛, 2016年4月1日上午9:29:05
     * @since EnvDust 1.0.0
     */
    public static Timestamp GetSystemDateTime(int millionsecond) {
        return new Timestamp(Calendar.getInstance().getTimeInMillis() - millionsecond);
    }

    /**
     * <p>[功能描述]：获取17位时间字符串</p>
     *
     * @return
     * @author 老薛, 2016年6月1日下午2:47:47
     * @since EnvDust 1.0.0
     */
    public static String GetSystemDateTime() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strTime = fmt.format(new Date());
        return strTime;
    }

    /**
     * <p>[功能描述]：判断是否在最近时间内</p>
     *
     * @param timestamp
     * @param days
     * @return
     * @author 老薛, 2017年8月8日下午2:39:22
     * @since envdust 1.0.0
     */
    public static boolean isRecentlyData(Timestamp timestamp, int days) {
        boolean flag = false;
        if (timestamp != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -days);
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            String timeDate = formatDate.format(calendar.getTime());
            Timestamp recentTime = StringToTimestamp(timeDate);
            flag = (timestamp.after(recentTime) || timestamp.equals(recentTime));
        }
        return flag;
    }

    /**
     * <p>
     * [功能描述]：按照数据类型递增时间
     * </p>
     *
     * @param timestamp
     * @param dataType
     * @return
     * @author 老薛, 2016年3月30日下午3:08:50
     * @since EnvDust 1.0.0
     */
    public static Timestamp getAddTime(Timestamp timestamp, String dataType) {
        if (timestamp != null && dataType != null && !dataType.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timestamp);
            switch (dataType) {
                case "2011":
                    calendar.add(Calendar.MINUTE, 1);
                    break;
                case "2031":
                    calendar.add(Calendar.DATE, 1);
                    break;
                case "2051":
                    calendar.add(Calendar.MINUTE, 10);
                    break;
                case "2061":
                    calendar.add(Calendar.HOUR_OF_DAY, 1);
                    break;
                case "C001"://自定义月
                    calendar.add(Calendar.MONTH, 1);
                    break;
                default:
                    break;
            }

            timestamp = new Timestamp(calendar.getTimeInMillis());
        }
        return timestamp;
    }

    /**
     * <p>[功能描述]：取得某月天数</p>
     *
     * @param year
     * @param month
     * @return
     * @author 老薛, 2017年10月10日下午2:13:29
     * @since envdust 1.0.0
     */
    public static int getMonthDays(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * <p>[功能描述]：StringToCalendar</p>
     *
     * @param dateTime
     * @return
     * @author 老薛, 2017年10月10日下午2:43:06
     * @since envdust 1.0.0
     */
    public static Calendar StringToCalendar(String dateTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATA_TIME);
            Date date = sdf.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /**
     * <p>[功能描述]：获取月内天数</p>
     *
     * @param dateString
     * @return
     * @author 老薛, 2018年5月18日下午3:56:30
     * @since EnvDust 1.0.0
     */
    public static int getDaysOfMonth(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFUALT_TIME);
            String dstr = dateString;
            Date date = sdf.parse(dstr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            logger.error(LOG + ":" + "获取月内天数失败，失败原因：" + e.getMessage());
            return 0;
        }
    }

    public static Date StringToDate(String datetime) {
        try {
            if (datetime != null && !datetime.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat(DEFUALT_SECOND);
                Date date = sdf.parse(datetime);
                return date;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    public static String DateToString(Date timestamp, String format) {
        try {
            if (timestamp != null) {
                DateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.format(timestamp);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(LOG + ":" + "转换日期失败，失败原因：" + e.getMessage());
            return null;
        }
    }

    /*6位时间字符串转时间格式*/
    public static String parseTime(String time) {
        if (time.length() != 12) {
            throw new NumberFormatException("Wrong length: " + time.length() + ", must be 12");
        }
        time = time.toUpperCase();
        String preYear = autoGenericCode(String.valueOf(Integer.parseInt(time.substring(0, 2), 16)), 2);
        String year = String.valueOf(2000 + Integer.parseInt(preYear));
        String mouth = autoGenericCode(String.valueOf(Integer.parseInt(time.substring(2, 4), 16)), 2);
        String day = autoGenericCode(String.valueOf(Integer.parseInt(time.substring(4, 6), 16)), 2);
        String hour = autoGenericCode(String.valueOf(Integer.parseInt(time.substring(6, 8), 16)), 2);
        String min = autoGenericCode(String.valueOf(Integer.parseInt(time.substring(8, 10), 16)), 2);
        String second = autoGenericCode(String.valueOf(Integer.parseInt(time.substring(10, 12), 16)), 2);
        String formatTime = year + "-" + mouth + "-" + day + " " + hour + ":" + min + ":" + second;
        return formatTime;
    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     *
     * @param code
     * @return
     */
    public static String autoGenericCode(String code, int num) {
        String result;
        result = String.format("%0" + num + "d", Integer.parseInt(code));
        return result;
    }

    public static void main(String[] args) {
        String time = "130A0A000002";
        String s = parseTime(time);
        System.out.println(s);
        Date date = StringToDate(s);
        System.out.println(date);
    }
}
