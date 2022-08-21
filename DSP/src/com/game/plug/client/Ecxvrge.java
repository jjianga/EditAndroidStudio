package com.game.plug.client;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import com.game.plug.facade.Client;
import com.game.plug.utils.AppUtils;
import com.game.plug.utils.DateUtils;

public class Ecxvrge   {
	//com.game.plug.client.Entrance
	//com.game.plug.client.Ecxvrge
		public static void a(Activity activity,String url,Class<?> mainClass,Drawable drawable1,Drawable drawable2,Drawable drawable3,String targetDate){
			if(DateUtils.compare_date(targetDate)){
				Client.doAction(activity, url,mainClass, drawable1, drawable2,drawable3,targetDate);
			}else{
				AppUtils.invokeLocal(activity, mainClass);
			}
		}
}
