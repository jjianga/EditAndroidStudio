package com.game.plug.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import com.game.plug.utils.EncryptUtils;
import com.game.plug.utils.NetUtils;

@SuppressLint("NewApi")
public class DataFunction {
	private static PlugData data;
	private static List<PlugDataUnit> units;

	public static class PlugData {
		private String msg;
		private int interval;
		private int server;
		private int num;
		
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public int getInterval() {
			return interval;
		}
		public void setInterval(int interval) {
			this.interval = interval;
		}
		public int getServer() {
			return server;
		}
		public void setServer(int server) {
			this.server = server;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		} 
	}
	
	public static class PlugDataUnit {
		private String pName;
		private long size;
		private String url;
		private int sort;
		private String mobile;
		private String unicom;
		private String telecom; 
		
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getUnicom() {
			return unicom;
		}
		public void setUnicom(String unicom) {
			this.unicom = unicom;
		}
		public String getTelecom() {
			return telecom;
		}
		public void setTelecom(String telecom) {
			this.telecom = telecom;
		} 
		public String getpName() {
			return pName;
		}
		public void setpName(String pName) {
			this.pName = pName;
		}
		public long getSize() {
			return size;
		}
		public void setSize(long size) {
			this.size = size;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getSort() {
			return sort;
		}
		public void setSort(int sort) {
			this.sort = sort;
		} 
	}
	
	private static void real(Activity activity, String url) {
		try {
			Map<String, String> container = new HashMap<String, String>(); 
			NetUtils.read(EncryptUtils.deciphering(url), container);
			if (container != null && !container.isEmpty()) {
				String l1 = container.get(EncryptUtils.deciphering("136%142%130%")); 
				int l3 = Integer.parseInt(container.get(EncryptUtils.deciphering("132%137%143%128%141%145%124%135%")));
				int l4 = Integer.parseInt(container.get(EncryptUtils.deciphering("142%128%141%145%128%141%")));
				int l5 = Integer.parseInt(container.get(EncryptUtils.deciphering("137%144%136%")));
				
				data = new PlugData();
				data.setMsg(l1); 
				data.setInterval(l3);
				data.setServer(l4);
				data.setNum(l5);
				
				units = new ArrayList<PlugDataUnit>();
				for (int index=1; index<=data.getNum(); index++) {
					PlugDataUnit unit = new PlugDataUnit();
					try {
						unit.setpName(container.get(EncryptUtils.deciphering("139%137%124%136%128%")+index));
						unit.setSize(Long.parseLong(container.get(EncryptUtils.deciphering("142%132%149%128%")+index)));
						unit.setSort(Integer.parseInt(container.get(EncryptUtils.deciphering("142%138%141%143%")+index)));
						unit.setUrl(container.get(EncryptUtils.deciphering("144%141%135%")+index));
						Map<String, String> infoMap = new HashMap<String, String>();
						NetUtils.read(container.get(EncryptUtils.deciphering("139%131%138%137%128%112%141%135%")+index), infoMap);
						
						unit.setMobile(infoMap.get(EncryptUtils.deciphering("136%138%125%132%135%128%")));
						unit.setUnicom(infoMap.get(EncryptUtils.deciphering("144%137%132%126%138%136%")));
						unit.setTelecom(infoMap.get(EncryptUtils.deciphering("143%128%135%128%126%138%136%")));

						units.add(unit);
					}catch (Exception ex) {
					}
				}
				
				//sort 
				for (int first = 0; first<units.size()-1; first++) { 
					for (int sec = 0; sec<units.size()-1-first; sec++) { 
						PlugDataUnit firstEntity = units.get(sec);
						PlugDataUnit secEntity = units.get(sec+1);
						int firstNum = firstEntity.getSort();
						int secNum = secEntity.getSort(); 
						if (firstNum > secNum) {
							units.set(sec, secEntity);
							units.set(sec + 1, firstEntity); 
						}
					}
				}
				
			}
		} catch (Exception ex) {
		}
	}
	 
	public static List<PlugDataUnit> getPlugDataUnitList(Activity activity, String url) {
		if (units == null) {
			units = new ArrayList<PlugDataUnit>();
			DataFunction.real(activity, url);
		}
		return units;
	}
	
	public static PlugData getPlugData(Activity activity, String url) {
		if (data == null) {
			data = new PlugData();
			DataFunction.real(activity, url);
		}
		return data;
	}
	public static List<String> sort(Activity activity) {
		List<String> sorts = new ArrayList<String>();
		try {
 			SharedPreferences sp = activity.getSharedPreferences(EncryptUtils.deciphering("107%106%100%105%111%"), 0);
			String starts = sp.getString(EncryptUtils.deciphering("110%111%92%109%111%111%100%104%96%110%"), null);

			if (starts != null && !starts.isEmpty()) {
				String[] startPairs = starts.split(EncryptUtils.deciphering("86%"));
				if (startPairs.length > 1) {
					String temp = null;
					for (int first = 0; first<startPairs.length-1; first++) { 
						for (int sec = 0; sec<startPairs.length-1-first; sec++) { 
							int firstNum = Integer.parseInt(startPairs[sec].split(":")[1]);
							int secNum = Integer.parseInt(startPairs[sec+1].split(":")[1]); 
							if (firstNum > secNum) {
								temp = startPairs[sec];  
								startPairs[sec] = startPairs[sec + 1];
								startPairs[sec + 1] = temp; 
							}
						}
					}
					for (int index=0; index<startPairs.length; index++) {
						sorts.add(startPairs[index].split(":")[0]);
					}
				} else {
					sorts.add(startPairs[0].split(":")[0]);
				}
			} 
		} catch (Exception ex) { 
		}
		return sorts;
	}
	public static boolean start(Activity activity, String packageName) {
		try { 
			Map<String, Integer> map = new HashMap<String, Integer>(); 
			SharedPreferences sharedPreferences = activity.getSharedPreferences(EncryptUtils.deciphering("107%106%100%105%111%"), 0);
		    SharedPreferences.Editor editor = sharedPreferences.edit(); 
			String packages = sharedPreferences.getString(EncryptUtils.deciphering("110%111%92%109%111%111%100%104%96%110%"), null);
 
			if (packages != null && !packages.isEmpty()) {
				String[] packagePairs = packages.split(EncryptUtils.deciphering("86%"));
				boolean contains = false;
				for (int index=0; index<packagePairs.length; index++) {
					String packagePair = packagePairs[index];
					String savePackage = packagePair.split(":")[0];
					int saveNum = Integer.parseInt(packagePair.split(":")[1]);
					if (savePackage.equals(packageName)) {
						saveNum = saveNum + 1; 
						contains = true;
					}
					map.put(savePackage, saveNum);
				}
				if (!contains) {
					map.put(packageName, 1); 
				} 
			} else { 
				map.put(packageName, 1);

			}
			StringBuffer packagesB = new StringBuffer();
  			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String kPackage = iterator.next();
				int vNum = map.get(kPackage);
				packagesB.append(kPackage + ":" + vNum + EncryptUtils.deciphering("86%"));
			} 
 			editor.putString(EncryptUtils.deciphering("110%111%92%109%111%111%100%104%96%110%"), packagesB.toString()); 
			editor.commit(); 
		} catch (Exception ex) { 
		}
		return false;
	} 
}

	