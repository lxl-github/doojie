package com.doojie.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.doojie.domain.bo.TimeSpan;

public class DateUtil {
	
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");
	
	static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	
	static SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MM月dd日");
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	/**
	 * 根据字符串时间获取对应的时间戳
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Integer getTimespan(String date){
		try {
			Long timespan = simpleDateFormat.parse(date).getTime();
			return Integer.valueOf(String.valueOf(timespan).substring(0, 10));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 根据字符串时间获取对应的时间戳
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static Integer getTimespan2(String date){
		try {
			Long timespan = simpleDateFormat1.parse(date).getTime();
			return Integer.valueOf(String.valueOf(timespan).substring(0, 10));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 根据时间戳获取字符串时间
	 * @param timespan
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDatetime(Integer timespan){
		Long time = Long.valueOf(String.valueOf(timespan));
		return simpleDateFormat.format(new Date(time * 1000L));
	}
	
	/**
	 * 根据时间戳获取字符串时间
	 * @param timespan 
	 * @return yyyy-MM-dd
	 */
	public static String getDatetime1(Integer timespan){
		Long time = Long.valueOf(String.valueOf(timespan));
		return simpleDateFormat1.format(new Date(time * 1000L));
	}
	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static Integer getCurrentTimespan(){
		return Integer.valueOf(String.valueOf(new Date().getTime()).substring(0, 10));
	}
	
	/**
	 * 获取当前1分钟后的时间戳
	 * @return
	 */
	public static Integer getCurrentAfterOneTimespan(){
		Calendar now=Calendar.getInstance();
		now.add(Calendar.SECOND, 3);
		return Integer.valueOf(String.valueOf(now.getTime().getTime()).substring(0, 10));
	}

	/**
	 * 获取当前年份
	 * <br>
	 * 创建时间：2015年6月3日下午2:19:52
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @return
	 *
	 */
	public static Integer getYear(){
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR);
		return year;
	}
	
	public static Integer getHour(){
		Calendar cal = Calendar.getInstance();
		Integer hour = cal.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
	
	public static Integer getMinute(){
		Calendar cal = Calendar.getInstance();
		Integer minute = cal.get(Calendar.MINUTE);
		return minute;
	}
	
	/**
	 * 获取日期 yyyyMMdd
	 * <br>
	 * 创建时间：2015年6月8日下午2:42:47
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @return
	 *
	 */
	public static String getDate(){
		return simpleDateFormat2.format(new Date());
	}
	
	/**
	 * 获取日期 yyyy-MM-dd
	 * <br>
	 * 创建时间：2015年6月8日下午2:42:47
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @return
	 *
	 */
	public static String getDate2(){
		return simpleDateFormat1.format(new Date());
	}
	
	/**
     * 获取当前时间一周后日期
     * @param mdate
     * @return
     */
    public static Map<String,String> dateToWeek() {
        Date fdate;
       Map<String,String> map = new LinkedHashMap<String, String>();
        for (int i = 1; i <= 7; i++) {
            fdate = getAfterDate(i);
            String weekDays = getWeekOfDate(fdate);
            map.put(simpleDateFormat3.format(fdate).concat("(").concat(weekDays).concat(")"), simpleDateFormat1.format(fdate));
        }
        
        return map;
    }
    
    /**
     * 获取日期对应的时段
     * <br>
     * 创建时间：2015年8月24日下午4:26:30
     * <br>
     * @author lixiaoliang
     * <br>
     * @return
     *
     */
    public static Map<String, List<TimeSpan>> getTimeSpan(){
    	Map<String, List<TimeSpan>> map = new LinkedHashMap<String, List<TimeSpan>>();
    	 Date fdate;
          for (int i = 1; i <= 7; i++) {
              fdate = getAfterDate(i);
              //如果是同一天则需要判断8点到晚上20点时段不可选
              if(simpleDateFormat1.format(fdate).equals(getDate2())){
            	  List<TimeSpan> timesList = new ArrayList<TimeSpan>();
            	  for(int j = 8;j < 20;j++){
            		  int tmp = j+2;
        			  if(getMinute().intValue() >= 0 &&  (getHour().intValue() < tmp && tmp >= j)){
        				  TimeSpan timeSpan = new TimeSpan();
                		  timeSpan.setTime(j +":00~"+ tmp +":00");
                		  timeSpan.setIsShow(0);
                		  timesList.add(timeSpan);
        			  }else{
            			  TimeSpan timeSpan = new TimeSpan();
            			  timeSpan.setTime(j +":00~"+ tmp +":00");
                		  timeSpan.setIsShow(1);
                		  timesList.add(timeSpan);
        			  }
        			  j = j+1;
            	  }
            	  map.put(simpleDateFormat1.format(fdate), timesList);
              }else{//初始化这天的8点到晚上20点所有时段，并可选
            	  List<TimeSpan> timesList = new ArrayList<TimeSpan>();
            	  for(int j = 8;j < 20;j++){
            		  int tmp = j+2;
            		  TimeSpan timeSpan = new TimeSpan();
            		  timeSpan.setTime(j +":00~"+ tmp +":00");
            		  timeSpan.setIsShow(0);
            		  timesList.add(timeSpan);
            		  j = j+1;
            	  }
            	  map.put(simpleDateFormat1.format(fdate), timesList);
              }
          }
          return map;
    }
    
    /**
     * 获取当前日期n天后的日期
     * @param n:返回当天后的第N天
     * @return 返回的日期
     */
    public static Date getAfterDate(int n) {
        Calendar c = Calendar.getInstance();
        if(n == 1){
        	return c.getTime();
        }
//        n = n - 1;
        c.add(Calendar.DAY_OF_MONTH, n);
        return c.getTime();
    }
    
    /**
     * 获取当前日期n月后的日期
     * @param n:返回当天后的第N月
     * @return 返回的日期
     */
    public static Date getMonthAfterDate(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, n);
        c.add(Calendar.DAY_OF_MONTH, 1);
	    //将小时至0  
	    c.set(Calendar.HOUR_OF_DAY, 0);  
	    //将分钟至0  
	    c.set(Calendar.MINUTE, 0);  
	    //将秒至0  
	    c.set(Calendar.SECOND,0);  
	    //将毫秒至0  
	    c.set(Calendar.MILLISECOND, 0);
        //在n月基础上减去1毫秒  
        c.add(Calendar.MILLISECOND, -1); 
        return c.getTime();
    }
    
    /**
     * 获取几个月后的时间
     * <br>
     * 创建时间：2015年8月28日下午5:20:49
     * <br>
     * @author lixiaoliang
     * <br>
     * @param n
     * @return
     *
     */
    public static Integer getMonthAfterTime(int n){
    	Date date = getMonthAfterDate(n);
    	return Integer.valueOf(String.valueOf(date.getTime()).substring(0, 10));
    }
    
    /**
    * 获取当前日期是星期几<br>
    *
    * @param dt
    * @return 当前日期是星期几
    */
    public static String getWeekOfDate(Date dt) {
	    String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(dt);
	    int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	    if (w < 0) w = 0;
	    return weekDays[w];
    }
    
    /**
     * 判断当前时段是否可选
     * <br>
     * 创建时间：2015年8月25日下午2:10:43
     * <br>
     * @author lixiaoliang
     * <br>
     * @param times
     * @return
     *
     */
    public static Boolean isTimeSpanChoose(String times){
    	Map<String, List<TimeSpan>> timesMap = getTimeSpan();
		List<TimeSpan> timeSpanList = timesMap.get(getDate2());
		Map<String,TimeSpan> map = new LinkedHashMap<String, TimeSpan>();
		for(TimeSpan timeSpan : timeSpanList){
			map.put(timeSpan.getTime(), timeSpan);
		}
		
		TimeSpan timeSpan = map.get(times);
		return timeSpan.getIsShow() != 1;
    }
	
    /**
     * 获取当前小时在区间中的哪个时段
     * <br>
     * 创建时间：2015年8月25日下午2:10:43
     * <br>
     * @author lixiaoliang
     * <br>
     * @param times
     * @return
     *
     */
    public static String getTimeSpanByCurrentHour(){
    	Integer hour = getHour();
    	Map<String, List<TimeSpan>> timesMap = getTimeSpan();
		List<TimeSpan> timeSpanList = timesMap.get(getDate2());
		String returnTime = "";
		for(TimeSpan timeSpan : timeSpanList){
			String times = timeSpan.getTime();
			String[] timesArry = times.split("~");
			Integer startTime = Integer.valueOf(timesArry[0].substring(0,1));
			Integer endTime = Integer.valueOf(timesArry[1].substring(0,1));
			if(hour >= startTime && hour <= endTime){
				returnTime = times;
				break;
			}
		}
		return returnTime;
		
		
    }
    
	public static void main(String[] args){
//		Map<String, String> dates = dateToWeek();
//		Map<String, List<TimeSpan>> timesMap = getTimeSpan();
//		for(Entry<String, String> entry:dates.entrySet()){
//			System.out.println(entry.getKey()+","+entry.getValue());
//			List<TimeSpan> timeSpansList = timesMap.get(entry.getValue());
//			for(TimeSpan timeSpan : timeSpansList){
//				System.out.println(timeSpan.getTime());
//				System.out.println(timeSpan.getIsShow() == 1 ? "不可选" : "可选");
//			}
//			break;
//		}
		
//		System.out.println(getAfterDate(30));
            String a = "广东省 深圳市 龙华新区 广东省深圳市龙华新区民治街道人民南路水榭春天花园一期116号铺";
            int b = "abc".getBytes().length;
            System.out.println(a.length()+","+b);
	}
	
}
