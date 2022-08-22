package com.zhkj.code.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zhkj.log.Log;

/**
 * 反射特点： 可以在运行期间，动态加载一个类进来，动态new一个对象 动态了解对象内部的结构，动态调用这个对象的某一些方法 反射好处：
 * 在配置文件里只写类的名字，可以动态把类加载进来
 * 
 * @author Administrator
 *
 */
public class ReflectGetClazzUtils{
	/**
	 * 读取目录下所有配置文件
	 * @param filePath 文件路径
	 * @return 类的模版列表
	 */
	public static <T> List<T> getClazzS(String filePath){
		ArrayList<String> fileS = new ArrayList<>();
		FileUtils.listFilesString(new File(filePath),fileS);
		ArrayList<T> objectS = new ArrayList<>();
		for (String file : fileS) {
			try {
				if (".properties".equals(FileUtils.getFileNameSuffix(file))) {
					objectS.add(getClazz(file));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(file + ":错误！");
			}
		}
		return objectS;
	}

	/**
	 * 通过配置文件动态加载类
	 * @param filePath 路径
	 * @param <T> 类的模版
	 * @return 类的对象
	 */
	@SuppressWarnings("unchecked") 
	public static <T> T getClazz(String filePath){
		// 读取类  ，要执行的方法  ，  需要从界面获取的参数
		ReadPropertiesUtils rpu = ReadPropertiesUtils.init(filePath);
		String clazzStr = rpu.getClazzStr();
		String paramSStr = rpu.getParamSStr();
		// 把类load到内存，返回Class类型。
		Class<?> cls = null;
		Object obj = null;
		// new 一个对象
		try {
			cls = Class.forName(clazzStr);
			obj = cls.newInstance();
		} catch (ClassNotFoundException e) {
			Log.appendErr("配置文件错误！配置目录【"+filePath+ ",类【" + clazzStr);
			e.printStackTrace();
		}catch (Exception e) {
			Log.appendErr("类初始化错误！配置目录【"+filePath+ ",类【" + clazzStr);
			e.printStackTrace();
		}
		//给变量设置值
		if(paramSStr != null){
			String[] paramS = paramSStr.split(",");
			for (String param : paramS) {            //从界面拿值
				try {
					Field nameField;
					assert cls != null;
					nameField = cls.getDeclaredField(param);// 获取成员变量:name
					nameField.setAccessible(true);                    // 设置操作权限为true
					nameField.set(obj, ValueSUtils.getValue(param));//赋值
				} catch (Exception e) {
					Log.appendErr("变量始化错误！配置目录【" + filePath + ",类【" +
							clazzStr + ",属性【" + param);
					e.printStackTrace();
				}
			}
		}
		// 得到初始化方法 在这里初始化对象
		try {
			assert cls != null;
			Method method = cls.getMethod("init", HashMap.class);
			//TODO 从界面拿到对应的值
			method.invoke(obj, rpu.getMap());
		} catch (Exception e) {
			Log.appendErr("init方法调用错误！配置目录【"+filePath+ ",类【" + clazzStr);
			e.printStackTrace();
		}
		return (T)obj;
	}
	public static void main(String[] args) {
		try {
			ReflectGetClazzUtils.getClazz("edit.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
