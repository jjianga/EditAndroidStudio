package com.zhkj.code.editWrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.zhkj.code.bean.ResFile;
import com.zhkj.code.mode.EditResStringMode;
import com.zhkj.code.mode.ReadResFileMode;
import com.zhkj.code.preread.ReadResAll;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.code.utils.ReflectGetClazzUtils;
import com.zhkj.log.Log;

/**
 * 将修改后的数据写入类文件
 * @author Administrator
 *
 */
public class EditResAll {
	/**
	 * 根据读取的类 去找文件  ，  或者直接扫描文件
	 * 与 XmlFileS 相关的参数修改
	 * @param resPath	资源路径
	 * @param resFileS
	 * @param editResStringModes
	 * @param encoding
	 * @return
	 */
	public static void editXmlFile(String resPath, List<ResFile> resFileS,
			List<EditResStringMode> editResStringModes, String encoding){
		//type小于100  文件只更换名字
		for (int i = 0; i < resFileS.size(); i++) {
			ResFile resFile = resFileS.get(i);
			if(!(resFile.getType() < 100))continue;
			StringBuffer path = new StringBuffer(resPath)
					.append(File.separator)
					.append(resFile.getParentPath())
					.append(File.separator)
					.append(resFile.getFileName())
					.append(resFile.getSuffix());
			StringBuffer pathEdit = new StringBuffer(resPath)
					.append(File.separator)
					.append(resFile.getParentPath())
					.append(File.separator)
					.append(resFile.getFileEditName())
					.append(resFile.getSuffix());
			File file = new File(path.toString());
			file.renameTo(new File(pathEdit.toString()));
		}
		
		//检索所有文本资源作修改
		for (int i = 0; i < resFileS.size(); i++) {
			ResFile resFile = resFileS.get(i);
			if(resFile.getType() < 100)continue;
			StringBuffer path = new StringBuffer(resPath)
					.append(File.separator)
					.append(resFile.getParentPath())
					.append(File.separator)
					.append(resFile.getFileName())
					.append(resFile.getSuffix());
			File file = new File(path.toString());
			// 读取该文件   生成新文件
            StringBuffer strBf = new StringBuffer();
	        BufferedReader reader = null;
	        try {		
	            InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);      
	            reader = new BufferedReader(read);
	            String xmlCode = null;
	    		// 读一条完整语句块 
	            while ((xmlCode = reader.readLine()) != null) {
	            	strBf.append(xmlCode + "\n");
	            }
	            //处理语句
	            if(editResStringModes==null){Log.appendErr("EditResStringMode,不存在");}
	            for (EditResStringMode editResStringMode : editResStringModes) {
	            	strBf = editResStringMode
	            				.editResString(strBf, resFile,resFileS);
				}
	            read.close();
	            reader.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
			// 根据描述信息保存该类Edit
			StringBuffer pathEdit = new StringBuffer(resPath)
					.append(File.separator)
					.append(resFile.getParentPath())
					.append(File.separator)
					.append(resFile.getFileEditName())
					.append(resFile.getSuffix());
			// 原有的文件删除
			FileUtils.deleteFile(file);
			FileUtils.saveFile(new File(pathEdit.toString()), strBf,encoding);
		}
	}
	public static void main(String[] args) {
		List<ReadResFileMode> redeResFileMode
		= ReflectGetClazzUtils.getClazzS(
			"D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\readResFile\\");
		ReadResAll.init().resFolder("D:\\dabao\\GameBase_jdqs_base\\res\\", "UTF-8",redeResFileMode);
		int i = ReadResAll.resFileS.size();
		System.out.println("总计：" + i);

		List<EditResStringMode> editResStringModes 
					= ReflectGetClazzUtils.getClazzS(
							"D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\editResFile\\");
		EditResAll.editXmlFile("D:\\dabao\\GameBase_jdqs_base\\res\\",
					ReadResAll.resFileS, editResStringModes, "UTF-8");
	}
}
