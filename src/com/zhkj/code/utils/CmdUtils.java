package com.zhkj.code.utils;

import java.io.InputStream;
import java.util.Map;

public class CmdUtils {
	/**
	 * ƴ��CMD����
	 * @param batName    	bat����   
	 * @param argStrings	����
	 * @return
	 */
	public static String getCmdBat(String batName, String... argStrings) {// /b
        String cmd = "cmd /c start /b " + batName + " ";
        if (argStrings != null && argStrings.length > 0) {
            for (String string : argStrings) {
                cmd += string + " ";
            }
        }
        return cmd;
    }
    
	/**
	 *  ƴ��cmd ��������
	 * @param cmdPath
	 * @param argStrings
	 * @return
	 */
	public static String getCmd(String cmdPath, String... argStrings) {// /b
        String cmd = "cmd /c cd /d " + cmdPath + "&&";
        if (argStrings != null && argStrings.length > 0) {
            for (String string : argStrings) {
                cmd += string + "&&";
            }
        }
        cmd.substring(0,cmd.length()-2);
        return cmd;
    }
	/**
	 * ִ�з���  ����ִ�н��
	 * @param cmd
	 * @param returnVerdict
	 * @return
	 */
	public static Object executeCmd(String cmd,Map<String, Object> returnVerdict){
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            InputStream inputStream = ps.getInputStream();
            byte[] by = new byte[1024];
            while (inputStream.read(by) != -1) {
            	String str = new String(by,"GBK");
            	if(returnVerdict !=null)
            	for (String key: returnVerdict.keySet()) {
					if(str.contains(key)){
						return returnVerdict.get(key);
					}
				}
	            ps.waitFor();
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return null;
	}
}
