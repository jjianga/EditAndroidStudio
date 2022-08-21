package com.zhkj.code.preread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.mode.ReadJavaFileMode;
import com.zhkj.code.utils.ReadJavaUtil;
import com.zhkj.code.utils.ReflectGetClazzUtils;
import com.zhkj.log.Log;

/**
 * 读取类信息 ，  根据类信息  整理
 * @author Administrator
 *
 */
public class ReadClazzAll {
	public static List<ClazzObjectTrait> clazzObjectTraitS;
	public static ReadClazzAll init(){
		ReadClazzAll.clazzObjectTraitS = new ArrayList<ClazzObjectTrait>();
		return new ReadClazzAll();
	}
	/**
	 * 阅读源文件夹	读取所有java文件，  读取类描述信息（修改的部分）
	 * @param path		工程目录
	 * @param encoding	java编码方式
	 * @param convertJavaClazzModeS	解释模板实例
	 * @return
	 */
	public List<ClazzObjectTrait> javaSrcFolder(String path, String encoding,
												List<ReadJavaFileMode> convertJavaClazzModeS){
		return javaSrcFolder(new File(path), encoding, convertJavaClazzModeS); 
	}
	public List<ClazzObjectTrait> javaSrcFolder(File file, String encoding,
												List<ReadJavaFileMode> convertJavaClazzModeS){
		//读取文件夹
		if  (!file .isDirectory()){        
			String fileName = file.getName();
			//java文件 读出来
			String suffix = fileName.substring(fileName.indexOf("."), fileName.length());
			if(".java".equals(suffix)){
				ClazzObjectTrait clazzObjctTrait =
					readJavaFile(file, convertJavaClazzModeS, encoding);
	            if(clazzObjctTrait.getClazzName() == null ||
	            		clazzObjctTrait.getPackageName() == null){
	            	Log.appendErr(fileName+"]ReadClazzUtil 该文件不包含类信息！");
	            }else{
					clazzObjectTraitS.add(clazzObjctTrait);
	            }
			}
		} else{  
			// TODO 阅读下级目录
			for (File files : file.listFiles()) {
				javaSrcFolder(files, encoding, convertJavaClazzModeS);
			}
		} 
		return clazzObjectTraitS;
	}
	
	/**
	 * 阅读java文件  返回类的描述信息 
	 * @param javaFile				//java文件
	 * @param convertJavaClazzModeS	//解释用的模板
	 * @param encoding				//java 编码 UTF-8 GBK 
	 * @return
	 */
	public static ClazzObjectTrait readJavaFile(File javaFile,
												List<ReadJavaFileMode> convertJavaClazzModeS, String encoding){
        ClazzObjectTrait clazzObjctTrait = new ClazzObjectTrait();
		List<String> stringList = ReadJavaUtil.readJavaToString(javaFile, encoding, false);
		stringList.forEach(javaCodeL->{
			for (ReadJavaFileMode convertJavaClazzMode : convertJavaClazzModeS) {
				convertJavaClazzMode.readJavaCodeToClazzObject(javaCodeL, clazzObjctTrait);
			}
		});
		return clazzObjctTrait;
	}
	public static void main(String[] args) {
		List<ReadJavaFileMode> redeJavaFileMode 
			= ReflectGetClazzUtils.getClazzS("D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\redeJavaFile\\");
		ReadClazzAll.init().javaSrcFolder("D:\\dabao\\Android\\originalProject\\cacheProject\\src\\","UTF-8", redeJavaFileMode);
		System.out.println(ReadClazzAll.clazzObjectTraitS.size());
	}
}
