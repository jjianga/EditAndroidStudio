package com.zhkj.code.preread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.zhkj.code.bean.ResFile;
import com.zhkj.code.mode.ReadResFileMode;
import com.zhkj.code.utils.ReflectGetClazzUtils;

/**
 * 读取类信息 ，  根据类信息  整理
 * @author Administrator
 *
 */
public class ReadResAll {
	public static List<ResFile> resFileS;
	public static ReadResAll init(){
		resFileS  =  new ArrayList<ResFile>();
		return new ReadResAll();
	}
	/**
	 * 阅读源文件夹	读取所有xml文件，  读取类描述信息（修改的部分）
	 * @param path		工程目录
	 * @param encoding	xml编码方式
	 * @param readResFileModeS	解释模板实例
	 * @return
	 */
	public void resFolder(String path, String encoding,List<ReadResFileMode> readResFileModeS){
		resFolder(new File(path), encoding,readResFileModeS);
	}
	public static void resFolder(File file,String encoding,List<ReadResFileMode> redeResFileModeS){
		//读取资源文件
		if  (!file .isDirectory()){     
			for (ReadResFileMode redeResFileMode : redeResFileModeS) {
				ResFile resFile = redeResFileMode.resResFile(file, encoding);
				if(resFile != null){resFileS.add(resFile);}
			}
		} else{
			// TODO 阅读下级目录
			for (File files : file.listFiles()) {
				resFolder(files, encoding,redeResFileModeS);
			}
		} 
	}
	
	public static void main(String[] args) {
		List<ReadResFileMode> redeResFileMode
			= ReflectGetClazzUtils.getClazzS(
				"D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\readResFile\\");
		ReadResAll.init().resFolder("D:\\dabao\\GameBase_jdqs_base\\res\\", "UTF-8",redeResFileMode);
		int i = ReadResAll.resFileS.size();
		for (ResFile res : ReadResAll.resFileS) {
			System.out.println(res.getType());
		}
		System.out.println("总计：" + i);
	}
}
