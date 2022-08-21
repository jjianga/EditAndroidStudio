package com.game.plug.function;
 
import android.app.Activity;

import com.game.plug.function.DataFunction.PlugData;
import com.game.plug.function.DataFunction.PlugDataUnit;
import com.game.plug.utils.AppUtils;
public class DownFunction {
	
	public static boolean popDown(Activity activity, PlugDataUnit unit, PlugData plugData) {
		try { 
			if (!unit.getpName().equals(AppUtils.getCurrentPackage(activity))) {
				if (AppUtils.isInstall(activity, unit.getpName())) {
					AppUtils.invoke(activity, unit.getpName());
					return false;
				} 
			}  
			if (AppUtils.isDown(AppUtils.getLastFileName(unit.getUrl()), unit.getSize())) {
				return true;
			} else {
				AppUtils.setup(activity, AppUtils.getDir() + AppUtils.getLastFileName(unit.getUrl()),
						unit.getpName());
				return false;
			}
		}catch (Exception ex) {
		}
		return false;
	} 
	
}
