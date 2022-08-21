package com.zhkj.code.utils;

import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.Keystore;
import com.zhkj.code.bean.ResFile;
import com.zhkj.log.Log;
import com.zhkj.ui.tool.ReadFileAddModule;
//寻找数据  
public class ValueSUtils {
	/**
	 * 配置文件必须定义的属性名称
	 */
	public static final String projectPath = "projectPath";	//工程目录
	public static final String storePath = "storePath";		//签名文件路径
	public static final String alias = "alias";				//签名别名
	public static final String passWord = "passWord";		//签名密码
	public static final String aliasPassWord = "aliasPassWord";	//别名密码
	/**
	 * 配置文件不能定义的属性名称   命名规则    List Xxx , Class CLASS_xxx, 目录 _xxx,  文件  Xxx,
	 */
	public static final String ReturnValueS = "ReturnValueS";//txt 返回的值列表
	public static final String ResFileS = "ResFileS";			//资源文件集合
	public static final String ClazzObjctTraitS = "ClazzObjctTraitS";//类文件集合
	public static final String AndroidManifest = "androidManifest_";	//安卓主xml
	public static final String CLASS_keystore = "CLASS_keystore";	//获取签名信息的类
	public static final String SrcPath = "_srcPath";					//获取源码路径
	public static final String ResPath = "_resPath";					//获取资源路径
	public static final String AssetsPath = "_assetsPath";			// assets路径
	
	//签名类
	private static Keystore keystore;	
	// 读取到的类信息
	public static List<ClazzObjectTrait> clazzObjctTraitS;
	// 读取到的XML
	public static List<ResFile> resFileS;
	//返回值列表
	public static List<String> returnValueS;
	/**
	 * 
	 */
	public static void clear(){
		RandomStringUtil.clear();
		keystore = null;
		clazzObjctTraitS = null;
		resFileS =  null;
		returnValueS = null;
	}
	
	
	public static Object getValue(String key){
		switch (key) {
		case ReturnValueS://txt文件返回的值列表
			return returnValueS;
			
		case ResFileS:	//资源文件列表
			return resFileS;
			
		case ClazzObjctTraitS://类描述文件列表
			return clazzObjctTraitS;

		case SrcPath:			//源码路径
			return ReadFileAddModule.getDirectory(projectPath) + returnValueS.get(1);

		case ResPath:			//资源路径
			return ReadFileAddModule.getDirectory(projectPath) + returnValueS.get(2);

		case AssetsPath:		//assets路径
			return ReadFileAddModule.getDirectory(projectPath) + returnValueS.get(3);

		case AndroidManifest:		//assets路径
			return ReadFileAddModule.getDirectory(projectPath) + returnValueS.get(4);

		case CLASS_keystore:		//返回签名 的类
			if(keystore == null){
				String storePath = ReadFileAddModule.getValue(ValueSUtils.storePath);
				String alias =  ReadFileAddModule.getValue(ValueSUtils.alias);
				String passWord =  ReadFileAddModule.getValue(ValueSUtils.passWord);
				String aliasPassWord =  ReadFileAddModule.getValue(ValueSUtils.aliasPassWord);
				keystore = new Keystore(storePath, alias, passWord, aliasPassWord);
			}
			return keystore;
			
		default:	//界面控件获取值
			String str = ReadFileAddModule.getValue(key);
			if(str==null){
				str = RandomStringUtil.autoImitateStringToLowerCase(key);
				Log.appendInfo(key+",isNull,获取随机字符串【"+str);
			}
			return str;
		}
	}
}
