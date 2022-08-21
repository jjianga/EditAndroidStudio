package com.zhkj.code.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zhkj.code.mode.ReplaceFileStringMode;
import com.zhkj.code.replaceSours.ReplaceForRegular;
import com.zhkj.code.replaceSours.ReplaceImage;
import com.zhkj.code.replaceSours.ReplaceRegular;
import com.zhkj.log.Log;

/**
 * 当与清单，图片等文件  ..  相关信息改变时修改清单图片文件
 * @author Administrator
 *
 */

public class AndroidManifestEditUtil {
	private File javaFile = null;
	private String projectPath = "工程目录";
	private String value = "通过key获取的值";
	public AndroidManifestEditUtil(String projectPath, File javaFile) {
		this.javaFile = javaFile;
		this.projectPath = projectPath;
	}
	public List<String> run(){
		ValueSUtils.returnValueS = new ArrayList<String>();
		String encoding = "UTF-8";
		BufferedReader reader = null;
        InputStreamReader read = null;
        FileInputStream fis = null;
        String fileLine = null;
        try {
        	fis = new FileInputStream(javaFile);
            read = new InputStreamReader(fis,encoding);      
            reader = new BufferedReader(read);
            ReplaceFileStringMode replaceMode = null;
            File file = null;
            StringBuffer stringBuf = null;
            // 一次读入一行
            while ((fileLine = readLine(reader)) != null) {
            	switch (fileLine) {
					case "CD FILE;"://打开文件  缓存 到 stringBuf  
						fileLine = readLine(reader);
						file = new File(projectPath + fileLine);
						stringBuf = FileUtils.readString(file, encoding);
						break;
					case "SAVE FILE;"://保存文件 stringBuf 保存为文件
						FileUtils.saveFile(file, stringBuf, encoding);
						file = null;
						stringBuf = null;
						break;
					case "SAVE AS NEW FILE;":
						//文件另存   projectPath,fileLine 和value  存在的时候组成完整路径 ，
						//或者value设置为空 projectPath, fileLine  组成完整路径
						//或者fileLine设置为空 projectPath,  value 组成完整路径
						String path = projectPath;
						fileLine = readLine(reader);
						if(fileLine!=null){
							path = path + fileLine;
						}
						if(value!=null){
							path = path + value;
						}
						FileUtils.saveFile(
								new File(path), stringBuf, encoding);
						break;
					case "SET VALUE;":
						//设置值  为下一条数据执行设置初始值 ，值通过key，从UI取！
						fileLine = readLine(reader);
						if("NULL".equals(fileLine.toUpperCase())){value = null;}
						//从显示对象获取值 UI.get(key)
						value = (String)ValueSUtils.getValue(fileLine);
						break;
					case "REPLACE REGULAR;":
						//正则替换String文件  读取正则 把符合要求的替换成 value
						replaceMode = new ReplaceRegular(value, stringBuf);
						break;
					case "REPLACE FOR REGULAR;":
						//循环改变正则，并使用正则替换String文件， 把符合要求的替换成 value
						fileLine = readLine(reader);//regularValue 正则表达式循环替换的值
						replaceMode = new ReplaceForRegular(value, stringBuf,fileLine);
						break;
					case "IMAG EREPLACE;"://替换图片资源imgRoot
						replaceMode = new ReplaceImage(readLine(reader),value, projectPath);
						break;
					case "ENCRYPTION;"://文件加密
						stringBuf = new StringBuffer(EncryptionUtil.encrypt(stringBuf.toString()));
						break;
					case "EXIT;":		//退出 返回执行的项目文件夹
						replaceMode = new ReplaceFileStringMode() {
							@Override
							public void init(HashMap<String, Object> map) {
							}
							@Override
							public void lineExecute(String Line) {
								ValueSUtils.returnValueS.add(Line);
							}
						};
						break;
					default:
		            	replaceMode.lineExecute(fileLine);
						break;
	            }
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        	Log.appendErr("执行失败【"  + fileLine);
        } finally {
        	if (reader != null) {
        		try {
        			reader.close();
        		} catch (IOException e1) {
        		}
        	}
        	if (read != null) {
        		try {
        			read.close();
        		} catch (IOException e1) {
        		}
        	}
        	if (fis != null) {
        		try {
        			fis.close();
        		} catch (IOException e1) {
        		}
        	}
        }
		return ValueSUtils.returnValueS;
	}
	/**
	 * 过滤注释内容  和空行
	 * @param reader
	 * @return
	 */
	private static String readLine(BufferedReader reader){
		String str = null;
		try {
			do {
				str = reader.readLine();
				if(str == null)return null;
			} while (VerificationUtil.isBlankString(str)||'#'==str.charAt(0));
		} catch (IOException e) {
			e.printStackTrace();
			Log.appendErr("读取一行命令错误！");
		}
		return str;
	}
	
	public static void main(String[] args) {
		AndroidManifestEditUtil androidManifestEditUtil = 
				new AndroidManifestEditUtil("D:\\dabao\\GameBase_jdqs_base\\",new File("meipian.txt"));
		androidManifestEditUtil.run();
	}
	
}
