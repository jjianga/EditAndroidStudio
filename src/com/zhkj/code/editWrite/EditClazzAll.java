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
 * ���޸ĺ������д�����ļ�
 * @author Administrator
 *
 */
public class EditClazzAll {
	/**
	 * ���ݶ�ȡ���� ȥ���ļ�  ��  ����ֱ��ɨ���ļ�
	 * �� clazzObjectTraitS ��صĲ����޸�
	 * @param prefixPath �ļ���·��
	 * @param clazzObjectTraitS //��������Ϣ
	 * @param convertClazzModeToJavaS //ת����
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
			// ��ȡ���ļ�   �������ļ�
	        StringBuffer codeBuf = new StringBuffer();
			stringList.forEach(javaCodeL->{
				//TODO �������
				for (EditJavaFileMode convertClazzModeToJava : convertClazzModeToJavaS) {
					javaCodeL = convertClazzModeToJava.
							editJavaCode(javaCodeL, clazzObjctTrait,clazzObjectTraitS);
				}
				codeBuf.append(javaCodeL).append("\r\n");
			});
			// ����������Ϣ�������Edit
			String pathEdit = clazzObjctTrait.getPackageEditName().replace(".", "/")+"/"+clazzObjctTrait.getClazzEditName();
			pathEdit = prefixPath+"/"+pathEdit+".java";
			// ԭ�е��ļ�ɾ��
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
					.getClazzS("D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\editJavaFile\\");//TODO �����ļ�Ŀ¼ռʱû�����
		editClazzByClazzObjectTrait("D:\\dabao\\GameBase_jdqs_base\\src\\",
				ReadClazzAll.clazzObjectTraitS, editJavaFileMode, "UTF-8");
	}
}
