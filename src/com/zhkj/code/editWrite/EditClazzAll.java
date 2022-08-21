package com.zhkj.code.editWrite;

import java.io.File;
import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.mode.EditJavaFileMode;
import com.zhkj.code.mode.ReadJavaFileMode;
import com.zhkj.code.preread.ReadClazzAll;
import com.zhkj.code.utils.FileUtils;
import com.zhkj.code.utils.ReadJavaUtil;
import com.zhkj.code.utils.ReflectGetClazzUtils;

/**
 * 将修改后的数据写入类文件
 * @author Administrator
 *
 */
public class EditClazzAll {
	/**
	 * 根据读取的类 去找文件  ，  或者直接扫描文件
	 * 与 clazzObjectTraitS 相关的参数修改
	 * @param prefixPath 文件夹路径
	 * @param clazzObjectTraitS //类描述信息
	 * @param convertClazzModeToJavaS //转化类
	 * @return
	 */
	public static void editClazzByClazzObjectTrait(String prefixPath, List<ClazzObjectTrait> clazzObjectTraitS,
												   List<EditJavaFileMode> convertClazzModeToJavaS, String encoding){
		for (int i = 0; i < clazzObjectTraitS.size(); i++) {
			ClazzObjectTrait clazzObjctTrait = clazzObjectTraitS.get(i);
			String path = clazzObjctTrait.getPackageName().replace(".", "/")+
					"/"+clazzObjctTrait.getClazzName()+".java";
			File file = new File(prefixPath+"/" + path);
			List<String> stringList = ReadJavaUtil.readJavaToString(file, encoding);
			// 读取该文件   生成新文件
	        StringBuffer codeBuf = new StringBuffer();
			stringList.forEach(javaCodeL->{
				//TODO 处理语句
				for (EditJavaFileMode convertClazzModeToJava : convertClazzModeToJavaS) {
					javaCodeL = convertClazzModeToJava.
							editJavaCode(javaCodeL, clazzObjctTrait,clazzObjectTraitS);
				}
				codeBuf.append(javaCodeL).append("\r\n");
			});
			// 根据描述信息保存该类Edit
			String pathEdit = clazzObjctTrait.getPackageEditName().replace(".", "/")+"/"+clazzObjctTrait.getClazzEditName();
			pathEdit = prefixPath+"/"+pathEdit+".java";
			// 原有的文件删除
			FileUtils.deleteFile(file);
			FileUtils.saveFile(new File(pathEdit), codeBuf,encoding);
		}
	}
	public static void main(String[] args) {
		List<ReadJavaFileMode> readJavaFileMode
			= ReflectGetClazzUtils.getClazzS("D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\redeJavaFile\\");
		ReadClazzAll.init().javaSrcFolder("D:\\dabao\\GameBase_jdqs_base\\src\\","UTF-8", readJavaFileMode);
		System.out.println(ReadClazzAll.clazzObjectTraitS.size());

		List<EditJavaFileMode> editJavaFileMode 
					= ReflectGetClazzUtils
					.getClazzS("D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\editJavaFile\\");//TODO 配置文件目录占时没有设计
		editClazzByClazzObjectTrait("D:\\dabao\\GameBase_jdqs_base\\src\\",
				ReadClazzAll.clazzObjectTraitS, editJavaFileMode, "UTF-8");
	}
}
