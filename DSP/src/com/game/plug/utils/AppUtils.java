package com.game.plug.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import com.game.plug.function.DataFunction;

@SuppressLint("NewApi")
public class AppUtils {
	
	public static class InvokeThread implements Runnable { 
		private Activity currentActivity;
		private Class<?> targetClazz;
		
		public InvokeThread(Activity activity, Class<?> clazz) {
			currentActivity = activity;
			targetClazz = clazz;
		} 
		public void run() {
			Intent intent = new Intent(currentActivity, targetClazz);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			currentActivity.startActivity(intent);
			currentActivity.finish();
		}
	}
	public static void invokeLocal(Activity activity, Class<?> clazz) {
		try {
			new Handler().post(new InvokeThread(activity, clazz)); 
		} catch (Exception ex) {
		}
	}  
	public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
        	file.delete();
        } 
    }

	public static void setupUrl(final Activity activity, final String url, final String packageName) { 
		setup(activity, AppUtils.getDir() + File.separator + AppUtils.getLastFileName(url), packageName);
	}
	

	public static void setup(final Activity activity, final String path, final String packageName) {
		try {
 			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(new File(path)),
					"application/vnd.android.package-archive");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			activity.startActivity(intent); 
			
			new Thread(new Runnable(){ 
				@Override
				public void run() {
					int checkTimes = 240;
						while (checkTimes-- > 0) {
							try {
								Thread.sleep(200);
								if (AppUtils.isInstall(activity, packageName)) {
									deleteFile(path);
									AppUtils.invoke(activity, packageName);
 									break;
								} 
							} catch (Exception ex) {
							}
						}
				}
				
			}).start();
		} catch (Exception ex) {
			
		}
	}
	
	public static boolean isFull(String path, double size) {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(path);
			int available = inputStream.available();
			if (available >= size) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) { 
				}
			}
		}
	}
	public static boolean exists(String fileName) {
		if (fileName == null) {
			return false;
		}
		File file = new File(fileName);
		return file.exists();
	}
	
	public static boolean validate(String apkPath, long apkSize) {
		if (AppUtils.exists(apkPath) && 
				AppUtils.isFull(apkPath, apkSize - 100)) {
			return true;
  		}  else {
  			return false;
  		}
	}
	
	public static String getDir() {
		try {
			if (isSupport()) {
				String path = Environment.getExternalStorageDirectory() + 
						File.separator + EncryptUtils.deciphering("101%106%102%96%") + File.separator;
 				File file = new File(path);
 				
				if (!file.exists()) {
					file.mkdir();
				}
				return path;
			}
		} catch (Exception ex) {
		}
		return null;
	}
	public static boolean isSupport() {
		try {
			return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		} catch (Exception ex) {
			return false;
		}
	}
	public static boolean isContainsFile(String fileName) {
		try {
			if (isSupport()) {
				String path = Environment.getExternalStorageDirectory() +
						File.separator + EncryptUtils.deciphering("101%106%102%96%") + File.separator + fileName;
				File file = new File(path);
				return file.exists();
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}
	
	public static synchronized boolean isDown(String fileName, long size) {
		if (AppUtils.isContainsFile(fileName) && 
				AppUtils.validate(
						AppUtils.getDir() + fileName, size)) {
			return false;
		} else {
			return true;
		}
	}
	public static void invoke(Activity activity, String packageName) { 
  		try {
  			DataFunction.start(activity, packageName);
 			Intent mainIntent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
 	 		activity.startActivity(mainIntent); 
 		}catch (Exception ex) {
 		}
 		
 	}
	
	public static String getCurrentPackage(Activity activity) {
		String pN = null;
		try {
			PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);  
			pN = info.packageName;
		} catch (Exception ex) { 
		}
		return pN; 
	}
	public static String getLastFileName(String url) {
		if (url != null && !url.isEmpty() && url.contains("/")) {
			String[] splits = url.split("/");
			return splits[splits.length - 1];
		}
		return null; 
	}
	
	public static boolean isInstall(Activity activity, String packageName) {
	    try {
	      PackageManager packageManager = activity.getPackageManager();
	      List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
	      List<String> pName = new ArrayList<String>();
	      if (pinfo != null) {
	        for (int i = 0; i < pinfo.size(); ++i) {
	          String pn = ((PackageInfo)pinfo.get(i)).packageName;
	          pName.add(pn);
	        }
	      }
	      return pName.contains(packageName);
	    } catch (Exception localException) {
	    }
	    return false;
	  }
}
