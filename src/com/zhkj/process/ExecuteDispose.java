package com.zhkj.process;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.zhkj.code.editWrite.EditClazzAll;
import com.zhkj.code.editWrite.EditResAll;
import com.zhkj.code.mode.EditJavaFileMode;
import com.zhkj.code.mode.EditResStringMode;
import com.zhkj.code.mode.ExecuteMode;
import com.zhkj.code.mode.ReadJavaFileMode;
import com.zhkj.code.mode.ReadResFileMode;
import com.zhkj.code.mode.TrimmingMode;
import com.zhkj.code.preread.ReadClazzAll;
import com.zhkj.code.preread.ReadResAll;
import com.zhkj.code.utils.AndroidManifestEditUtil;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.code.utils.ReflectGetClazzUtils;
import com.zhkj.code.utils.ValueSUtils;
import com.zhkj.log.Log;
import com.zhkj.ui.AndroidStudioUnpackTool;
import com.zhkj.ui.tool.ReadFileAddModule;
/**
 * 执行流程
 * @author Administrator
 *
 */
public class ExecuteDispose {
	public static String encoding = "UTF-8";
	public static void main(String[] args) throws Exception {
		String tmpProjectPath=null,projectPath=null,config=null;
		//初始化 UI界面 并显示
		AndroidStudioUnpackTool.run(600, 800);
		while (true) {
			pause();			//暂停执行 等待返回结果继续执行
			ValueSUtils.clear();//数据初始化
			try{
				//获取工程目录，以及配置文件appProDirectory，临时代码存放目录
				projectPath = ReadFileAddModule.getValue("projectPath");
				config = ReadFileAddModule.getValue("config");
				tmpProjectPath = ReadFileAddModule.getValue("tmpProjectPath");
				File file = new File(projectPath);
				File filec = new File(tmpProjectPath);
				//临时目录与原工程目录，切换目录
				tmpProjectPath = projectPath;
				projectPath = filec.getAbsolutePath()+File.separator;
				try {
					Log.appendInfo("复制工程到：" + projectPath);
					FileUtils.copyFolderIsDelete(file, filec);
				} catch (Exception e) {
					Log.appendErr("复制工程错误！打包结束！");
					continue;
				}
				//根目录设置为临时路径
				ReadFileAddModule.setText("projectPath", projectPath);
				
				//读取流程文件，资源处理
				Log.appendInfo("2】 混淆前清单 替换所有修改的数据...");
				AndroidManifestEditUtil androidManifestEditUtil = new AndroidManifestEditUtil(
						projectPath,new File(config));
				List<String> arrStr = androidManifestEditUtil.run();
				String propertiesPath = arrStr.get(0);
				
				String srcPath =projectPath + arrStr.get(1);//"代码路径"
				String resPath =projectPath + arrStr.get(2);//"资源路径"
				
				Log.appendInfo("3】 读取文件...");
				//根据需要    读取java 文件 保存为描述
				List<ReadJavaFileMode> redeJavaFileMode 
							= ReflectGetClazzUtils.getClazzS(propertiesPath+"redeJavaFile\\");
				ValueSUtils.clazzObjctTraitS = ReadClazzAll.init().javaSrcFolder(srcPath,encoding, redeJavaFileMode);
				
				//根据需要 读取资源文件 保存为描述
				List<ReadResFileMode> redeResFileMode
					= ReflectGetClazzUtils.getClazzS(propertiesPath+"readResFile\\");
				ReadResAll.init().resFolder(resPath, encoding,redeResFileMode);
				ValueSUtils.resFileS = ReadResAll.resFileS;
				
				//统计一下读取数  没有意义 可以删掉
				Log.appendInfo("读取"+	ValueSUtils.clazzObjctTraitS.size() +"个类..");
				Log.appendInfo("读取"+	ValueSUtils.resFileS.size() +"个资源..");
				
				//读取描述 根据实际情况修改类文件
				Log.appendInfo("4】调用修改类文件的MODE...");
				List<EditJavaFileMode> editJavaFileMode 
							= ReflectGetClazzUtils.getClazzS(propertiesPath+"editJavaFile\\");
				EditClazzAll.editClazzByClazzObjectTrait( srcPath,ValueSUtils.clazzObjctTraitS, editJavaFileMode, encoding);
				
				//读取资源数据 分别修改
				Log.appendInfo("5】 调用修改资源文件..");
				List<EditResStringMode> editResStringModes 
							= ReflectGetClazzUtils.getClazzS(propertiesPath+"editResFile\\");
				EditResAll.editXmlFile(resPath,ValueSUtils.resFileS, editResStringModes, encoding);
				
				//文件整理，添加无用文件
				Log.appendInfo("6】 数据最后的整理..");
				List<TrimmingMode> trimmingCodeS
						= ReflectGetClazzUtils.getClazzS(propertiesPath+"trimming\\");
				for (int i = 0; i < trimmingCodeS.size(); i++) {
					TrimmingMode trimmingMode = trimmingCodeS.get(i);
					trimmingMode.trimming(ValueSUtils.clazzObjctTraitS, ValueSUtils.resFileS, encoding);
				}
				//打包
				Log.appendInfo("7】 打包..");
				List<ExecuteMode> executecmdS
						= ReflectGetClazzUtils.getClazzS(propertiesPath+"executecmd\\");
				Collections.sort(executecmdS);
				for (int i = 0; i < executecmdS.size(); i++) {
					ExecuteMode executecmdMode = executecmdS.get(i);
					executecmdMode.execute(projectPath);
				}
			}catch(Exception e){
				e.printStackTrace();
				Log.appendErr("本次打包失败！");
			}finally {
				//恢复根目录路径
				ReadFileAddModule.setText("projectPath", tmpProjectPath);
			}
		}
	}
	
	public static void pause(){
		synchronized (AndroidStudioUnpackTool.class) {
			try {
				Log.appendInfo("等待输入！");
				AndroidStudioUnpackTool.class.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void goOn(){
		synchronized (AndroidStudioUnpackTool.class) {
			Log.appendInfo("启动！");
			AndroidStudioUnpackTool.class.notify();
		}
	}
}
