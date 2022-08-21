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
 * ��ȡ����Ϣ ��  ��������Ϣ  ����
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
	 * �Ķ�Դ�ļ���	��ȡ����java�ļ���  ��ȡ��������Ϣ���޸ĵĲ��֣�
	 * @param path		����Ŀ¼
	 * @param encoding	java���뷽ʽ
	 * @param convertJavaClazzModeS	����ģ��ʵ��
	 * @return
	 */
	public List<ClazzObjectTrait> javaSrcFolder(String path, String encoding,
												List<ReadJavaFileMode> convertJavaClazzModeS){
		return javaSrcFolder(new File(path), encoding, convertJavaClazzModeS); 
	}
	public List<ClazzObjectTrait> javaSrcFolder(File file, String encoding,
												List<ReadJavaFileMode> convertJavaClazzModeS){
		//��ȡ�ļ���
		if  (!file .isDirectory()){        
			String fileName = file.getName();
			//java�ļ� ������
			String suffix = fileName.substring(fileName.indexOf("."), fileName.length());
			if(".java".equals(suffix)){
				ClazzObjectTrait clazzObjctTrait =
					readJavaFile(file, convertJavaClazzModeS, encoding);
	            if(clazzObjctTrait.getClazzName() == null ||
	            		clazzObjctTrait.getPackageName() == null){
	            	Log.appendErr(fileName+"]ReadClazzUtil ���ļ�����������Ϣ��");
	            }else{
					clazzObjectTraitS.add(clazzObjctTrait);
	            }
			}
		} else{  
			// TODO �Ķ��¼�Ŀ¼
			for (File files : file.listFiles()) {
				javaSrcFolder(files, encoding, convertJavaClazzModeS);
			}
		} 
		return clazzObjectTraitS;
	}
	
	/**
	 * �Ķ�java�ļ�  �������������Ϣ 
	 * @param javaFile				//java�ļ�
	 * @param convertJavaClazzModeS	//�����õ�ģ��
	 * @param encoding				//java ���� UTF-8 GBK 
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
