package com.zb.secondary_market.register;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

public class PhoneInfo {

	private static String tao;
	private static Context context;
	public PhoneInfo(Context context){
		PhoneInfo.context=context;
	}
	public String GetTAO(){
		TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
		String s=tm.getDeviceId();
		String pre_time=getTime();
		tao=s+" "+pre_time;
		return tao;
	}
	@SuppressLint("SimpleDateFormat")
	public static String getTime(){
		 Date d = new Date();
		 String time;
		 SimpleDateFormat sdf1 =new SimpleDateFormat("yyyyMMdd-HHmmss");
		 time=sdf1.format(d);
		 return time;
	}
	//得到两个日期之间的天数
	public static int getDaysBetween (Calendar d1, Calendar d2){
        if (d1.after(d2)){
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2){
            d1 = (Calendar) d1.clone();
            do{
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }//传进的是Date
	public static int getIntervalDays(Date startday,Date endday){       
        if(startday.after(endday)){
            Date cal=startday;
            startday=endday;
            endday=cal;
        }       
        long sl=startday.getTime();
        long el=endday.getTime();      
        long ei=el-sl;          
        return (int)(ei/(1000*60*60*24));
    }
	/* 获取机器唯一标识  
	 * @param _context  
	 * @return  
	 */  
	public static String getMacInfo(Context _context){  
	    TelephonyManager tm = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);  
	    String deviceId = tm.getDeviceId();  
	    if (deviceId == null|| deviceId.trim().length() == 0) {  
	        deviceId = String.valueOf(System.currentTimeMillis());  
	    }  
	    return  deviceId ;  
	}
	
	public static String getSIMId(Context _context){//得到SIM卡的信息
		TelephonyManager tm = (TelephonyManager)_context.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = tm.getSubscriberId();
		return imsi;
	}
	public static String getPhoneType(Context _context){
		return Build.MODEL;
	}
	
	public static String getPhoneNum(Context _context){
		TelephonyManager tm = (TelephonyManager)_context.getSystemService(Context.TELEPHONY_SERVICE);
		String phonenum = tm.getLine1Number();
		return phonenum;
	}
	
	public static void hideSoftkeyboard(Activity activity) {
	     try {
	          ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
	            .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
	                    InputMethodManager.HIDE_NOT_ALWAYS);
	        } catch (NullPointerException e) {
	            
	        }
	 }
	// 获取ROOT权限
	public static boolean getRoot(){
	    if (!is_root()){
	        try{
	            Runtime.getRuntime().exec("su");
	            return true;
	        }
	        catch (Exception e){
	        	return false;
	        }
	    }
	    else 
	    	return false;
	}
	// 判断是否具有ROOT权限
	public static boolean is_root(){
	    boolean res = false;
	    try{ 
	        if ((!new File("/system/bin/su").exists()) && 
	            (!new File("/system/xbin/su").exists())){
	        	res = false;
	        } 
	        else {
	        	res = true;
	        };
	    } 
	    catch (Exception e) {  
	    	e.printStackTrace();
	    }
	    return res;
	} 
	    


}
