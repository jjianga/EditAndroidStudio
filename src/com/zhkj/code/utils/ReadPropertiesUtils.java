package com.zhkj.code.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class ReadPropertiesUtils {
	public static final String CLAZZ = "Class";
	public static final String PARAMS = "ParamS";
	private String clazzStr;
	private String paramSStr;
	private HashMap<String, Object> map = new HashMap<String, Object>();
	public static ReadPropertiesUtils init(String filePath) {
		ReadPropertiesUtils readPropertiesUtils = new ReadPropertiesUtils();
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (Exception e) {
			System.err.println("不能读取属性文件. " + "请确保配置文件在CLASSPATH指定的路径中");
		}
		for (String key : properties.stringPropertyNames()) {  
			if(CLAZZ.equals(key)){
				readPropertiesUtils.clazzStr = properties.getProperty(CLAZZ);
			}else if(PARAMS.equals(key)){
				readPropertiesUtils.paramSStr = properties.getProperty(PARAMS);
			}else{
				readPropertiesUtils.map.put(key, properties.getProperty(key));
			}
	    }  
		return readPropertiesUtils;
	}
	public String getClazzStr() {
		return clazzStr;
	}
	public String getParamSStr() {
		return paramSStr;
	}
	public HashMap<String, Object> getMap() {
		return map;
	}
	

}