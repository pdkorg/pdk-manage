package com.pdk.manage.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;



public class DateUtil {
    public static final String FORMAT_DEFAULT = "yyyy-MM-dd";
    private final static TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
    public static final String FORMAT_ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MIN = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_CHINESE = "yyyy年MM月dd日";
    public static final String FORMAT_ALL_M = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_SMALL = "yyyyMMddHHmmss";
    public static final String FORMAT_DEFAULT_SMALL = "yyyyMMdd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_TIME_HOUR = "HH";
    public static final String FORMAT_TIME_MINUTE = "mm";
    public static final String FORMAT_TIME_SECOND = "ss";
    public static final String FORMAT_CHINESE_REMOVE_YEAR = "MM月dd日";
    /**
     * 自定义格式化dateformat类（不可对其赋值）
     */
    private static DateFormat getDateFormat(String patten){
        if(StringUtils.equals(FORMAT_DEFAULT, patten) ){
            return new SimpleDateFormat(FORMAT_DEFAULT);
        }else if(StringUtils.equals(FORMAT_ALL, patten)){
            return new SimpleDateFormat(FORMAT_ALL);
        }else if(StringUtils.equals(FORMAT_ALL_M, patten)){
            return new SimpleDateFormat(FORMAT_ALL_M);
        }else if(StringUtils.equals(FORMAT_SMALL, patten)){
            return new SimpleDateFormat(FORMAT_SMALL);
        }else if(StringUtils.equals(FORMAT_DEFAULT_SMALL, patten)){
            return new SimpleDateFormat(FORMAT_DEFAULT_SMALL);
        }else{
            return new SimpleDateFormat(patten);
        }
    }

    public static int getIntervalDays(String startdate, String enddate) {
        Calendar d1 = new GregorianCalendar();
        Calendar d2 = new GregorianCalendar();
        try {

            d1.setTime(getDateFormat(FORMAT_DEFAULT).parse(startdate));
            d2.setTime(getDateFormat(FORMAT_DEFAULT).parse(enddate));
        } catch (ParseException e) {
            //e.printStackTrace();
        }

        long ei=d2.getTimeInMillis()-d1.getTimeInMillis();
        int dd=(int)(ei/(1000*60*60*24));
        return dd;
    }
    /**
     * 四舍五入
     * @param startdate
     * @param enddate
     * @return
     */
    public static int getIntervalDays(Date startdate, Date enddate){
        Calendar d1 = new GregorianCalendar();
        d1.setTime(startdate);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(enddate);

        long ei=d2.getTimeInMillis()-d1.getTimeInMillis();
        int dd=(int)(ei/(1000*60*60*24));
        return dd;
    }

    public static String getToday(){
        return getDateFormat(FORMAT_ALL).format(Calendar.getInstance().getTime());
    }

    public static String getToday(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        return dateFormat.format(now);
    }



    /**
     * 以当前时间加天数的日期
     * @param day  推移的天数
     * @return
     */
    public static String addCurrentDate(int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(GregorianCalendar.DAY_OF_MONTH, day);
        return getDateFormat(FORMAT_DEFAULT).format(calendar.getTime());
    }

    /**
     * 将一个字符串的日期描述转换为java.util.Date对象
     *
     * @param strDate
     *            字符串的日期描述
     * @param format
     *            字符串的日期格式，比如:“yyyy-MM-dd HH:mm”
     * @return 字符串转换的日期对象java.util.Date
     */
    public static Date getDate(String strDate, String format) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(timeZone);
        Date date=null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param msel 毫秒时间
     * @param format 日期格式
     * @return 毫秒时间 所对应的日期
     */

    public static String formatTime(long msel,String format) {
        Date date = new Date(msel);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /**
     * 解析时间成字符串 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_ALL);
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /**
     * 格式化字符串日期
     * @param strDate
     * @param strOldFormat
     * @param strNewFormat
     * @return
     */
    public static String formatDate(String strDate, String strOldFormat,
                                    String strNewFormat) {
        if(strDate == null || strDate.trim().length() == 0){
            return "";
        }
        if(strOldFormat == null || strOldFormat.trim().length() == 0){
            strOldFormat = FORMAT_ALL;
        }
        if(strNewFormat == null || strNewFormat.trim().length() == 0){
            strNewFormat = FORMAT_DEFAULT;
        }
        Date date = getDate(strDate, strOldFormat);
        SimpleDateFormat formatter = new SimpleDateFormat(strNewFormat);
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /**
     * 解析时间成指定字符串
     * @param date
     * @return
     */
    public static String formatDate(Date date,String patten) {

        return getDateFormat(patten).format(date);
    }
    /**
     * 解析指定字符串为日期
     * @param
     * @return
     */
    public static Date parseDate(String source,String patten) {
        if(StringUtils.isBlank(source) || StringUtils.isBlank(patten)){
            return null;
        }
        try {
            return getDateFormat(patten).parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }
    public static Date plusDays(Date time, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days);
        return cal.getTime();
    }

    /**
     * 距离小时数
     * @param startdate
     * @param enddate
     * @return
     * @throws Exception
     */
    public static int getIntervalHour(String startdate, String enddate) throws Exception {
        Date bDate = getDateFormat(FORMAT_ALL).parse(startdate);
        Date eDate = getDateFormat(FORMAT_ALL).parse(enddate);
        Calendar d1 = new GregorianCalendar();
        d1.setTime(bDate);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(eDate);

        long ei=d2.getTimeInMillis()-d1.getTimeInMillis();
        int dd=(int)(ei/(1000*60*60));
        if (dd < 1) dd=1;
        return dd;
    }


    /**
     * 转换字符串为long型毫秒
     * @param date
     * @return
     * @throws ParseException
     */
    public static long date2Long(String date) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_ALL);
        Date bDate = format.parse(date);
        Calendar d1 = new GregorianCalendar();
        d1.setTime(bDate);
        return d1.getTimeInMillis();
    }

    /**
     * long型毫秒转换为字符串
     * @param msel
     * @param format
     * @return
     */
    public static String long2DateStr(long msel,String format) {
        Date date = new Date(msel);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static long getCurrentMSEL() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.getTimeInMillis();
    }

    /**
     * 格式化字符串日期
     * @param strDate
     * @param strOldFormat
     * @param strNewFormat
     * @return
     */
    public static String getDate(String strDate, String strOldFormat,
                                 String strNewFormat) {
        if(strDate == null || strDate.trim().length() == 0){
            return "";
        }
        if(strOldFormat == null || strOldFormat.trim().length() == 0){
            strOldFormat = FORMAT_ALL;
        }
        if(strNewFormat == null || strNewFormat.trim().length() == 0){
            strNewFormat = FORMAT_DEFAULT;
        }
        Date date = getDate(strDate, strOldFormat);
        SimpleDateFormat formatter = new SimpleDateFormat(strNewFormat);
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /**
     * 日期格式化，自定义输出日期格式
     * @param date
     * @return
     */
    public static String getFormatDate(Date date,String dateFormat){
        SimpleDateFormat formatter=new SimpleDateFormat(dateFormat);
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }

    /**
     * 是否为当天
     * @param startDate
     * @return
     */
    public static boolean isCurrentDay(String startDate, String format) {
        if (startDate == null || startDate.trim().length() == 0) {
            return false;
        }
        Date start = null;
        SimpleDateFormat formatter=new SimpleDateFormat(format);
        try {
            start = formatter.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (start == null) {
            return false;
        }

        Date now = new Date();
        boolean isSameDay = DateUtils.isSameDay(start, now);
        if (isSameDay) {
            return true;
        }
        return false;
    }

    public static int getWeekDayFromCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekDay = 0;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                weekDay = 7;
                break;
            case 2:
                weekDay = 1;
                break;
            case 3:
                weekDay = 2;
                break;
            case 4:
                weekDay = 3;
                break;
            case 5:
                weekDay = 4;
                break;
            case 6:
                weekDay = 5;
                break;
            case 7:
                weekDay = 6;
                break;
            default:
                break;
        }
        return weekDay;
    }

    public static int compareDate(Date beforeDate,Date afterDate){
        return getDateFormat(FORMAT_ALL).format(beforeDate).compareTo(getDateFormat(FORMAT_ALL).format(afterDate));
    }
    public static int compareTime(String beforeTime,String afterTime){//18:00,19:00
        if(beforeTime.length()<6 && afterTime.length()<6){
            int btime = Integer.parseInt(beforeTime.split(":")[0]);
            int etime = Integer.parseInt(afterTime.split(":")[0]);
            if(btime >= etime){
                return 1;
            }else{
                return 0;
            }
        }
        return -1;
    }

    /**
     * 传来的日期增加传来的天数
     * @param date
     * @param day
     * @return
     */
    public static String addDayByNum(String date,int day) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DEFAULT);
        try {
            Date dateTemp = formatter.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateTemp);
            calendar.add(GregorianCalendar.DAY_OF_MONTH, day);
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_DEFAULT);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    /**
     * 判断curdate是否在startDate和endDate之间
     * @param curdate
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isDateInRange(String curdate, String startDate, String endDate) {
        boolean ret = false;
        if(curdate == null || curdate.isEmpty()
                || startDate == null || startDate.isEmpty()
                || endDate == null || endDate.isEmpty()) {
            return ret;
        }
        startDate = addDayOfYear(startDate, -1);
        endDate = addDayOfYear(endDate, 1);

        Date curDateTmp = getDate(curdate, FORMAT_DEFAULT);
        Date sDateTmp = getDate(startDate, FORMAT_DEFAULT);
        Date eDateTmp = getDate(endDate, FORMAT_DEFAULT);
        if(!eDateTmp.after(sDateTmp)) {
            return ret;
        }
        if(curDateTmp.after(sDateTmp) && curDateTmp.before(eDateTmp)) {
            ret = true;
        }
        return ret;
    }

    /**
     * 传来的日期增加传来的天数--DAY_OF_YEAR
     * @param date
     * @param day
     * @return
     */
    public static String addDayOfYear(String date, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DEFAULT);
        try {
            Date dateTemp = formatter.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateTemp);
            calendar.add(GregorianCalendar.DAY_OF_YEAR, day);
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_DEFAULT);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    /**
     * 传来的日期增加传来的天数--DAY_OF_YEAR
     * @param date
     * @param day
     * @return
     */
    public static String addDayOfYearWithTime(String date, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_ALL);
        try {
            Date dateTemp = formatter.parse(date);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(dateTemp);
            calendar.add(GregorianCalendar.DAY_OF_YEAR, day);
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_ALL);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    /**
     * 所给日期，是否早于系统日期之前
     * @param
     * @param
     * @return
     */
    public static boolean isBefore(String dateStr){
        boolean ret = Boolean.FALSE;
        if(dateStr == null || dateStr.trim().length() == 0){
            return ret;
        }

        Date date = DateUtil.getDate(dateStr, FORMAT_ALL);

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        if(date.compareTo(today.getTime()) <= 0){
            return Boolean.TRUE;
        }

        return ret;
    }

    /**
     * 和系统时间相比，是否同一天
     * @param dateStr "yyyy-MM-dd"
     * @author haibo.tang 2012-2-28
     */
    public static boolean isToday(String dateStr) {
        if(StringUtils.isBlank(dateStr)){
            return false;
        }
        Date date = DateUtil.getDate(dateStr, "yyyy-MM-dd");
        if(date == null){
            return false;
        }
        return DateUtils.isSameDay(new Date(), date);
    }

    /**
     * 和系统时间相比，是否昨天
     * @param dateStr "yyyy-MM-dd"
     * @author haibo.tang 2012-2-28
     */
    public static boolean isYesterday(String dateStr) {
        if(StringUtils.isBlank(dateStr)){
            return false;
        }
        Date date = DateUtil.getDate(dateStr, "yyyy-MM-dd");
        if(date == null){
            return false;
        }
        Calendar yest = Calendar.getInstance();
        yest.add(Calendar.DAY_OF_MONTH, -1);
        Calendar compar = Calendar.getInstance();
        compar.setTime(date);

        return yest.get(Calendar.YEAR) == compar.get(Calendar.YEAR)
                && yest.get(Calendar.MONTH) == compar.get(Calendar.MONTH)
                && yest.get(Calendar.DAY_OF_MONTH) == compar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时间差值
     * @param str1
     * @param str2
     * @return
     */
    public static String getIntervalTime(String str1, String str2) {
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuffer result = new StringBuffer();
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            if (day > 0) {
                result.append(day + "天");
            }
            if (hour > 0) {
                result.append(hour + "小时");
            }
            if (min > 0) {
                result.append(min + "分钟");
            }
        } catch (ParseException e) {
            return "";
        }
        return result.toString();
    }

    /**
     * 转换字符串为long型毫秒
     * @param date
     * @return
     * @throws ParseException
     */
    public static long date2Long(String date,String formatStr) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date bDate = format.parse(date);
        Calendar d1 = new GregorianCalendar();
        d1.setTime(bDate);
        return d1.getTimeInMillis();
    }

    /**
     * 搜索日期小于当前日期，则返回true
     * @param startDate
     * @return
     */
    public static boolean invalidDate(String startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now =new Date();
        boolean valid = false;
        try {
            Date start =sdf.parse(startDate);
            String  nowday =sdf.format(now);
            Date curentdate = sdf.parse(nowday);
            valid = start.before(curentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return valid;
    }

    /**
     * 所给时间，是否在系统时间之前
     *
     * @param
     * @param
     * @return
     */
    public static boolean isBeforeCurrent(String dateStr) throws Exception {
        if (dateStr == null || dateStr.trim().length() == 0) {
            return false;
        }
        Date date = DateUtil.getDate(dateStr, FORMAT_ALL);
        Calendar today = Calendar.getInstance();
        if (date.compareTo(today.getTime()) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 订单列表使用，返回格式：8月29日
     * @param date
     * @return
     */
    public static String formatDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            try {
                return DateUtil.formatDate(date, "yyyy-MM-dd", "M月d日");
            } catch (Exception e) {
            }
        }
        return date;
    }
    /**
     * 获取间隔分钟
     * @param startdate
     * @param enddate
     * @return
     * @throws Exception
     */
    public static int getIntervalMinutes(String startdate, String enddate) throws Exception {
        Date bDate = getDateFormat(FORMAT_ALL).parse(startdate);
        Date eDate = getDateFormat(FORMAT_ALL).parse(enddate);
        Calendar d1 = new GregorianCalendar();
        d1.setTime(bDate);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(eDate);

        long ei=d2.getTimeInMillis()-d1.getTimeInMillis();
        int dd=(int)(ei/(1000*60));
        if (dd < 1) dd=1;
        return dd;
    }
}
