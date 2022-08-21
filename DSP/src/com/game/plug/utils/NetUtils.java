package com.game.plug.utils;

import java.io.BufferedReader; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map; 
import org.json.JSONObject;     
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
 

public class NetUtils {
	
	@SuppressLint("UseValueOf")
	public static void downloadByPro(String url, Handler handler) {
 		HttpURLConnection connection = null;
 		FileOutputStream output = null;
 		InputStream input = null;
		try {
            if (AppUtils.isSupport()) { 
              	URL urlObj = new URL(url);
            	connection = (HttpURLConnection)urlObj.openConnection();
				connection.setConnectTimeout(20000);
				connection.setReadTimeout(30000);
				connection.connect();
				String responseMsg = connection.getResponseMessage(); 
				output = new FileOutputStream(AppUtils.getDir() + AppUtils.getLastFileName(url));
				byte[] buffer = new byte[1024];
			    int len = 0; 
  				if("OK".equalsIgnoreCase(responseMsg)){ 
 					input = connection.getInputStream();  
					while((len = input.read(buffer)) > 0){
						output.write(buffer,0,len); 
						
						Message msg = new Message();
						msg.what = 1;
						msg.obj = new Long(len); 
						handler.sendMessage(msg); 
   					}  
					input.close();
					output.flush();
					output.close();  
					handler.sendEmptyMessage(2); 
 				}
            } 
        } catch (Exception e) {
        } finally {
        	if (input != null) {
        		try {
					input.close();
				} catch (IOException e) {
 				}
        	}
        	if (output != null) {
        		try {
					output.close();
				} catch (IOException e) { 
				}
        	}
        	if (connection != null) {
        		connection.disconnect();
        	}  
        }
	  }
	  
	@SuppressLint("UseValueOf")
	public static void downByObserver(String url, String fileName, Handler handler, int index) {
 		HttpURLConnection connection = null;
 		FileOutputStream output = null;
 		InputStream input = null;
		try {
            if (AppUtils.isSupport()) { 
              	URL urlObj = new URL(url);
            	connection = (HttpURLConnection)urlObj.openConnection();
				connection.setConnectTimeout(20000);
				connection.setReadTimeout(30000);
				connection.connect();
				String responseMsg = connection.getResponseMessage(); 
				output = new FileOutputStream(AppUtils.getDir() + fileName);
				byte[] buffer = new byte[1024];
			    int len = 0; 
  				if("OK".equalsIgnoreCase(responseMsg)){ 
 					input = connection.getInputStream();  
					while((len = input.read(buffer)) > 0){
						output.write(buffer,0,len);  
    				}  
					input.close();
					output.flush();
					output.close();  
					Message msg = new Message();
					msg.what = 1;
					msg.obj = new Integer(index);
					handler.sendMessage(msg); 
 				}
            } 
        } catch (Exception e) {
        } finally {
        	if (input != null) {
        		try {
					input.close();
				} catch (IOException e) {
 				}
        	}
        	if (output != null) {
        		try {
					output.close();
				} catch (IOException e) { 
				}
        	}
        	if (connection != null) {
        		connection.disconnect();
        	}  
        }
	  }
	
	public static void readIp(final String url, final Map<String, String> returnValue) {
		try {
			Thread thread = new Thread(new Runnable() { 
				@Override
				public void run() {
					HttpURLConnection httpURLConnection = null;
					InputStream inputStream = null;
					try {
						httpURLConnection = (HttpURLConnection) new URL(url)
								.openConnection();
						httpURLConnection.setConnectTimeout(15000);
						httpURLConnection.setReadTimeout(30000);
						httpURLConnection.connect();
						inputStream = httpURLConnection.getInputStream();
						BufferedReader bufferReader = new BufferedReader(
								new InputStreamReader(inputStream, "gb2312"));
						String temp = null;
						while ((temp = bufferReader.readLine()) != null) {
							String value = temp.split("=")[1];
							JSONObject obj = new JSONObject(value);
							returnValue.put(EncryptUtils.deciphering("139%141%138%145%132%137%126%128%"),
											obj.getString(EncryptUtils.deciphering("139%141%138%145%132%137%126%128%")));
							returnValue.put(EncryptUtils.deciphering("126%132%143%148%"),
									obj.getString(EncryptUtils.deciphering("126%132%143%148%"))); 
							break;
						}
							 
					} catch (Exception localException) { 
					} finally {
						if (inputStream != null)
							try {
								inputStream.close();
							} catch (IOException localIOException1) {
							}
						if (httpURLConnection != null) {
							httpURLConnection.disconnect();
						}
					}
				}
				
			});
			thread.start();
			thread.join();
		} catch (Exception ex) {
		}
	} 

	public static Map<String, String> read(final String url,
			final Map<String, String> content) {
		try {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					HttpURLConnection conn = null;
					InputStream inputStream = null;
					try {
						conn = (HttpURLConnection) new URL(url)
								.openConnection();
						conn.setConnectTimeout(15000);
						conn.setReadTimeout(30000);
						conn.connect();
						inputStream = conn.getInputStream();
						BufferedReader bufferReader = new BufferedReader(
								new InputStreamReader(inputStream, EncryptUtils.deciphering("130%125%77%78%76%77%")));
						String str = null;
						while ((str = bufferReader.readLine()) != null) {
							if (str.contains(EncryptUtils.deciphering("88%"))) {
								if (str.contains(EncryptUtils.deciphering("131%143%143%139%85%74%74%"))
										|| str.contains(EncryptUtils.deciphering("144%141%135%"))
										|| str.contains(EncryptUtils.deciphering("112%141%135%"))
										|| str.contains(EncryptUtils.deciphering("112%109%103%"))) {
									String[] splits = str.split(EncryptUtils.deciphering("88%"));
									String key = splits[0];
									if (splits.length > 1) {
										StringBuffer buffer = new StringBuffer();
										for (int index = 1; index < splits.length; index++) {
											buffer.append(splits[index]);
										}
										content.put(key, buffer.toString());
									}

								} else {
									String[] splits = str.split(EncryptUtils.deciphering("88%"));
									if (splits.length == 2) {
										String key = splits[0];
										String value = splits[1];
										content.put(key, value);
									}
								}
							}
						}
					} catch (Exception ex) {
					} finally {
						if (inputStream != null) {
							try {
								inputStream.close();
							} catch (IOException e) {
							}
						}
						if (conn != null) {
							conn.disconnect();
						}
					}
				} 
			});
			thread.start();
			thread.join();
		} catch (Exception ex) {
		}
		return content;
	} 
}
