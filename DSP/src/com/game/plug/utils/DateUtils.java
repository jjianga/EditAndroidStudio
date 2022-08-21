package com.game.plug.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

    public class DateUtils {

	 @SuppressLint("SimpleDateFormat") 
	 public static boolean compare_date(String targetDate) {
		 
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        String nowDate=df.format(new Date()) ;
	        try {
	            Date dt1 = df.parse(nowDate);
	            Date dt2 = df.parse(targetDate);
	            if (dt1.getTime() > dt2.getTime()) {
	                return true;
	            }else {
	                return false;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return false;
	    }
	}
	
