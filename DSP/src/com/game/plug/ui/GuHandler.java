package com.game.plug.ui;
  
import java.util.Map;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.game.plug.utils.AppUtils;

public class GuHandler {
	private static GuHandler self;
	private Activity activity;
	private FlikerProgressBar bar;
	private GuDialog dial;
	private long len;
	private long total;
	private Map<String, String> pMap;
	
	public static GuHandler getSelf() {
		return self;
	} 
	public Map<String, String> getpMap() {
		return pMap;
	}  
	public long getLen() {
		return len;
	}

	public void setLen(long len) {
		this.len = len;
	}

	public long getTotal() {
		return total;
	} 
	
	public Activity getActivity() {
		return activity;
	}
	
	public FlikerProgressBar getBar() {
		return bar;
	}  

	public GuDialog getDial() {
		return dial;
	} 

	public static void setHandler(Handler handler) {
		GuHandler.handler = handler;
	}

	private GuHandler(Activity activity, FlikerProgressBar bar, GuDialog dial, Map<String, String> pMap) {
		this.activity = activity;
		this.bar = bar;
		this.pMap = pMap;
		this.dial = dial;
		this.total = Long.parseLong(pMap.get(GuDialog.des("142%132%149%128%")));
	}  
	
	public static GuHandler getInstance(Activity activity, FlikerProgressBar bar, GuDialog dial, Map<String, String> pMap) {
		if (self == null) {
			self = new GuHandler(activity, bar, dial, pMap);
		}
		return self;
	}
	
	public static Handler handler = new Handler() {
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
 			
			case 1 : 
				Object pyLen = msg.obj;
				if (pyLen != null) {
					try {
						long pylenlong = ((Long)pyLen).longValue();
						self.setLen(self.getLen() + pylenlong);
					}catch (Exception ex) {
					}
				}
				self.getBar().setProgress((int)((float)self.getLen() / (float)self.getTotal() * 100.0F)); 
				break;
			case 2:
				try {
					GuHandler.getHandler().sendEmptyMessage(3);	
					AppUtils.setupUrl(self.getActivity(), 
							          self.getpMap().get(GuDialog.des("144%141%135%")),
							          self.getpMap().get(GuDialog.des("139%105%124%136%128%"))); 
				}catch (Exception ex) {
				}
				break;
			case 3:
				self.getDial().dismiss();
				break; 
			case 4: 
				self.getDial().show();
				break;
			}
		};
	};
	public static Handler getHandler() {
		return handler;
	}
	
}
