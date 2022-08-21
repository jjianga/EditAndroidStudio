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
 * ִ������
 * @author Administrator
 *
 */
public class ExecuteDispose {
	public static String encoding = "UTF-8";
	public static void main(String[] args) throws Exception {
		String tmpProjectPath=null,projectPath=null,config=null;
		//��ʼ�� UI���� ����ʾ
		AndroidStudioUnpackTool.run(600, 800);
		while (true) {
			pause();			//��ִͣ�� �ȴ����ؽ������ִ��
			ValueSUtils.clear();//���ݳ�ʼ��
			try{
				//��ȡ����Ŀ¼���Լ������ļ�appProDirectory����ʱ������Ŀ¼
				projectPath = ReadFileAddModule.getValue("projectPath");
				config = ReadFileAddModule.getValue("config");
				tmpProjectPath = ReadFileAddModule.getValue("tmpProjectPath");
				File file = new File(projectPath);
				File filec = new File(tmpProjectPath);
				//��ʱĿ¼��ԭ����Ŀ¼���л�Ŀ¼
				tmpProjectPath = projectPath;
				projectPath = filec.getAbsolutePath()+File.separator;
				try {
					Log.appendInfo("���ƹ��̵���" + projectPath);
					FileUtils.copyFolderIsDelete(file, filec);
				} catch (Exception e) {
					Log.appendErr("���ƹ��̴��󣡴��������");
					continue;
				}
				//��Ŀ¼����Ϊ��ʱ·��
				ReadFileAddModule.setText("projectPath", projectPath);
				
				//��ȡ�����ļ�����Դ����
				Log.appendInfo("2�� ����ǰ�嵥 �滻�����޸ĵ�����...");
				AndroidManifestEditUtil androidManifestEditUtil = new AndroidManifestEditUtil(
						projectPath,new File(config));
				List<String> arrStr = androidManifestEditUtil.run();
				String propertiesPath = arrStr.get(0);
				
				String srcPath =projectPath + arrStr.get(1);//"����·��"
				String resPath =projectPath + arrStr.get(2);//"��Դ·��"
				
				Log.appendInfo("3�� ��ȡ�ļ�...");
				//������Ҫ    ��ȡjava �ļ� ����Ϊ����
				List<ReadJavaFileMode> redeJavaFileMode 
							= ReflectGetClazzUtils.getClazzS(propertiesPath+"redeJavaFile\\");
				ValueSUtils.clazzObjctTraitS = ReadClazzAll.init().javaSrcFolder(srcPath,encoding, redeJavaFileMode);
				
				//������Ҫ ��ȡ��Դ�ļ� ����Ϊ����
				List<ReadResFileMode> redeResFileMode
					= ReflectGetClazzUtils.getClazzS(propertiesPath+"readResFile\\");
				ReadResAll.init().resFolder(resPath, encoding,redeResFileMode);
				ValueSUtils.resFileS = ReadResAll.resFileS;
				
				//ͳ��һ�¶�ȡ��  û������ ����ɾ��
				Log.appendInfo("��ȡ"+	ValueSUtils.clazzObjctTraitS.size() +"����..");
				Log.appendInfo("��ȡ"+	ValueSUtils.resFileS.size() +"����Դ..");
				
				//��ȡ���� ����ʵ������޸����ļ�
				Log.appendInfo("4�������޸����ļ���MODE...");
				List<EditJavaFileMode> editJavaFileMode 
							= ReflectGetClazzUtils.getClazzS(propertiesPath+"editJavaFile\\");
				EditClazzAll.editClazzByClazzObjectTrait( srcPath,ValueSUtils.clazzObjctTraitS, editJavaFileMode, encoding);
				
				//��ȡ��Դ���� �ֱ��޸�
				Log.appendInfo("5�� �����޸���Դ�ļ�..");
				List<EditResStringMode> editResStringModes 
							= ReflectGetClazzUtils.getClazzS(propertiesPath+"editResFile\\");
				EditResAll.editXmlFile(resPath,ValueSUtils.resFileS, editResStringModes, encoding);
				
				//�ļ�������������ļ�
				Log.appendInfo("6�� ������������..");
				List<TrimmingMode> trimmingCodeS
						= ReflectGetClazzUtils.getClazzS(propertiesPath+"trimming\\");
				for (int i = 0; i < trimmingCodeS.size(); i++) {
					TrimmingMode trimmingMode = trimmingCodeS.get(i);
					trimmingMode.trimming(ValueSUtils.clazzObjctTraitS, ValueSUtils.resFileS, encoding);
				}
				//���
				Log.appendInfo("7�� ���..");
				List<ExecuteMode> executecmdS
						= ReflectGetClazzUtils.getClazzS(propertiesPath+"executecmd\\");
				Collections.sort(executecmdS);
				for (int i = 0; i < executecmdS.size(); i++) {
					ExecuteMode executecmdMode = executecmdS.get(i);
					executecmdMode.execute(projectPath);
				}
			}catch(Exception e){
				e.printStackTrace();
				Log.appendErr("���δ��ʧ�ܣ�");
			}finally {
				//�ָ���Ŀ¼·��
				ReadFileAddModule.setText("projectPath", tmpProjectPath);
			}
		}
	}
	
	public static void pause(){
		synchronized (AndroidStudioUnpackTool.class) {
			try {
				Log.appendInfo("�ȴ����룡");
				AndroidStudioUnpackTool.class.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void goOn(){
		synchronized (AndroidStudioUnpackTool.class) {
			Log.appendInfo("������");
			AndroidStudioUnpackTool.class.notify();
		}
	}
}
