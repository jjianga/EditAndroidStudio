package com.zhkj.code.utils;

import java.io.InputStream;
import java.util.Map;

public class CmdUtils {
	/**
	 * 拼接CMD命令
	 * @param batName    	bat名称   
	 * @param argStrings	参数
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
	 *  拼接cmd 运行命令
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
	 * 执行方法  返回执行结果
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
