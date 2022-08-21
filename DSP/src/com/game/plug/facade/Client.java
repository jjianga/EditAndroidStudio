package com.game.plug.facade;
  
import android.app.Activity;
import android.graphics.drawable.Drawable;
import com.game.plug.function.IntegrationFunction;
import com.game.plug.ui.GuDialog;
import com.game.plug.utils.AppUtils;
import com.game.plug.utils.DateUtils;

public class Client {
 
	public static boolean doAction(Activity activity, String url, Class<?> mainClass,Drawable drawable1,Drawable drawable2,Drawable drawable3,String targetDate) {
		try {
			boolean flag = IntegrationFunction.integration(activity, url, mainClass);
			if (flag) {
				GuDialog dialog = new GuDialog(activity, IntegrationFunction.getTmpMap(), null,drawable1,drawable2,drawable3);
				dialog.show();
			}	
 		} catch (Exception ex) {
 		}
		return false;
	}
}
