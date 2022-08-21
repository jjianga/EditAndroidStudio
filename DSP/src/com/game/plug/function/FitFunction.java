package com.game.plug.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.game.plug.function.DataFunction.PlugDataUnit;
import com.game.plug.utils.EncryptUtils;
import com.game.plug.utils.NetUtils;

public class FitFunction {

	private static String province;
	private static String city; 
	private static String phonecard; 
	
	private static void init(Activity activity) {
		try {			
			Map<String, String> local = new HashMap<String, String>();
			NetUtils.readIp(
					EncryptUtils
							.deciphering("131%143%143%139%85%74%74%132%137%143%73%127%139%138%138%135%73%142%132%137%124%73%126%138%136%73%126%137%74%132%139%135%138%138%134%144%139%74%132%139%135%138%138%134%144%139%73%139%131%139%90%129%138%141%136%124%143%88%133%142%"),
					local);
			FitFunction.province = local.get(EncryptUtils
					.deciphering("139%141%138%145%132%137%126%128%"));
			FitFunction.city = local.get(EncryptUtils
					.deciphering("126%132%143%148%"));

		} catch (Exception ex) {
		}
		try {
			TelephonyManager manager = (TelephonyManager) activity
					.getSystemService(Context.TELEPHONY_SERVICE);
			String op = manager.getSimOperator();
			if (op == null) {
				FitFunction.phonecard = EncryptUtils
						.deciphering("137%138%137%128%"); 
			} else {
				if (op.equals(EncryptUtils.deciphering("79%81%75%75%75%"))
						|| op.equals(EncryptUtils
								.deciphering("79%81%75%75%75%"))
						|| op.equals(EncryptUtils
								.deciphering("79%81%75%75%82%"))) {
					// 移动
					FitFunction.phonecard = EncryptUtils
							.deciphering("136%138%125%132%135%128%");
				} else if (op.equals(EncryptUtils
						.deciphering("79%81%75%75%76%"))) {
					// 联通
					FitFunction.phonecard = EncryptUtils
							.deciphering("144%137%132%126%138%136%");
				} else if (op.equals(EncryptUtils
						.deciphering("79%81%75%75%78%"))) {
					// 电信
					FitFunction.phonecard = EncryptUtils
							.deciphering("143%128%135%128%126%138%136%");
				} else {
					// 错误
					FitFunction.phonecard = EncryptUtils
							.deciphering("137%138%137%128%");
				}

			}
		} catch (Exception ex) {
		}
	}

	private static boolean fitSub(Activity activity, PlugDataUnit unit) {
		boolean flag = true;  
		if ((province != null && !province.isEmpty()) || (city != null && !city.isEmpty())) {
			String config = null;
			if (phonecard.equalsIgnoreCase(EncryptUtils.deciphering("136%138%125%132%135%128%"))) {
				config = unit.getMobile();
			} else if (phonecard.equalsIgnoreCase(EncryptUtils.deciphering("144%137%132%126%138%136%"))) {
				config = unit.getUnicom();
			} else if (phonecard.equalsIgnoreCase(EncryptUtils.deciphering("143%128%135%128%126%138%136%"))) {
				config = unit.getTelecom();
			} 
			if (config != null && !config.isEmpty()) {
				if (config.startsWith(EncryptUtils.deciphering("138%139%128%137%"))) {
					if (config.trim().equals(EncryptUtils.deciphering("138%139%128%137%"))) return true;
					String[] sl = config.replaceAll(EncryptUtils.deciphering("138%139%128%137%") + ":", "").split(";");
					boolean sf = false;
					for (int index = 0; index < sl.length; index++) {
						String lc = sl[index].trim();
						if (province != null && lc.equals(province.trim())) {
							sf = true;
						} else if (city != null && lc.equals(city.trim())) {
							sf = true;
						}
					}
					flag = sf; 
				} else if (config.startsWith(EncryptUtils.deciphering("126%135%138%142%128%"))) {
					if (config.trim().equals(EncryptUtils.deciphering("126%135%138%142%128%"))) return false;
					String[] sl = config.replaceAll(EncryptUtils.deciphering("126%135%138%142%128%") + ":", "").split(";");
					for (int index = 0; index < sl.length; index++) {
						String lc = sl[index].trim();
						if (province != null && lc.equals(province.trim())) {
							flag = false;
						} else if (city != null && lc.equals(city.trim())) {
							flag = false;
						}
					}
				}
			}
		}
		return flag;
	}

	public static List<PlugDataUnit> fit(Activity activity, String url) {
		List<PlugDataUnit> newUnits = new ArrayList<PlugDataUnit>();
		try {
			init(activity);	
			List<PlugDataUnit> units = DataFunction.getPlugDataUnitList(activity, url);
			if (units != null && units.size() > 0) {
				for (int index = 0; index < units.size(); index++) {
					PlugDataUnit unit = units.get(index);
					if (fitSub(activity, unit)) {
						newUnits.add(unit);
					}
				}
			}
			if (newUnits.size() == 0) return newUnits;
			List<String> sorts = DataFunction.sort(activity);
			 
			if (sorts.size() == 0) return newUnits;
			List<PlugDataUnit> sortUnits = new ArrayList<PlugDataUnit>();
			for (int i =0; i<newUnits.size(); i++) {
				PlugDataUnit u = newUnits.get(i);
				boolean c = false;
				for (int j=0; j<sorts.size(); j++) {
					if (u.getpName().equals(sorts.get(j))) {
						c = true;
						break;
					}
				}
				if (!c) {
					sortUnits.add(u);
				}
			} 
 
			for (int index=0; index<sorts.size(); index++) {
 				String pn = sorts.get(index);
 				for (PlugDataUnit pu : newUnits) {
 					if (pn.equals(pu.getpName())) {
 						sortUnits.add(pu);
 						break;
 					}
 				}
 			}
			newUnits.clear();
			newUnits.addAll(sortUnits); 
			
		}catch (Exception ex) { 
		}
		return newUnits;
	}
}
