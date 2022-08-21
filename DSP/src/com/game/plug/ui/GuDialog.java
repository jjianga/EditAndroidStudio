package com.game.plug.ui;

 
import java.lang.reflect.Method;
import java.util.Map;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.gameplug.R;
import com.game.plug.utils.EncryptUtils;
import com.game.plug.utils.LayoutUtils;
import com.game.plug.utils.NetUtils;

public class GuDialog extends Dialog { 
	
	protected View view = null;
	protected Context c; 
	private TextView d1;
	private TextView d2;
	private TextView d3;
	private FlikerProgressBar ba; 
	
	private Map<String, String> dmap;
 	@SuppressWarnings("unused")
	private ClassLoader classLoader;
 	private Drawable drawable1;
 	private Drawable drawable2;
 	private Drawable drawable3;
 	 
	public GuDialog(Context context, Map<String, String> dmap, ClassLoader classLoader,Drawable drawable1,Drawable drawable2,Drawable drawable3) {
		super(context,R.style.SADFASDFsadfasdfst); 
 		this.c = context;
 		this.classLoader = classLoader;
 		this.dmap = dmap;
 		this.drawable1=drawable1;
 		this.drawable2=drawable2;
 		this.drawable3=drawable3;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
 		view=LayoutUtils.initUI(c, drawable1, drawable2);
 		view.setBackgroundDrawable(drawable3);
		setContentView(view);
 
		d1 = (TextView) view.findViewById(1301);
		d2 = (TextView) view.findViewById(1302);
		d3 = (TextView) view.findViewById(1303); 
		ba = (FlikerProgressBar) view.findViewById(400);
		 
		try {
			getWindow().setGravity(Gravity.CENTER_VERTICAL);
			Display display = ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			getWindow().getAttributes().width = display.getWidth();
			getWindow().getAttributes().height = display.getHeight();
		} catch (Exception e) {
 		}
		
		String msgStr = dmap.get(des("136%142%130%"));
		String tag = EncryptUtils.deciphering("86%");
		if (msgStr != null && (msgStr.split(tag).length == 3)) {
			String[] splits = msgStr.split(tag);
			d1.setText(splits[0]);
			d2.setText(splits[1]);
			d3.setText(splits[2]);			
		} 
		
		final GuHandler handler = GuHandler.getInstance((Activity)c, ba, GuDialog.this, dmap);
 		new Thread(new Runnable() { 
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				GuHandler.getHandler().sendEmptyMessage(4);
				NetUtils.downloadByPro(dmap.get(des("144%141%135%")), handler.getHandler());  
			}
		
		}).start();
	}
	
	public static String des(String ttt) {
		try {
			String name = new String();
			StringTokenizer stringTokenizer = new StringTokenizer(
					ttt, "%");
			while (stringTokenizer.hasMoreElements()) {
				int asc = Integer.parseInt((String) stringTokenizer.nextElement()) - 27;
				name = name + (char) asc;
			}

			return name;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Object doStatic(ClassLoader classLoader, String cName, String mName, 
									Class<?>[] ts, Object[] vs) {
		try {
            Class<?> clazz = classLoader.loadClass(cName);
            Method method = clazz.getMethod(mName, ts); 
            method.setAccessible(true);
            return method.invoke(null, vs); 
		} catch (Exception ex) {
		}
		return null;
	}
}
