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
 * ���޸ĺ������д�����ļ�
 * @author Administrator
 *
 */
public class EditResAll {
	/**
	 * ���ݶ�ȡ���� ȥ���ļ�  ��  ����ֱ��ɨ���ļ�
	 * �� XmlFileS ��صĲ����޸�
	 * @param resPath	��Դ·��
	 * @param resFileS
	 * @param editResStringModes
	 * @param encoding
	 * @return
	 */
	public static void editXmlFile(String resPath, List<ResFile> resFileS,
			List<EditResStringMode> editResStringModes, String encoding){
		//typeС��100  �ļ�ֻ��������
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
		
		//���������ı���Դ���޸�
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
			// ��ȡ���ļ�   �������ļ�
            StringBuffer strBf = new StringBuffer();
	        BufferedReader reader = null;
	        try {		
	            InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);      
	            reader = new BufferedReader(read);
	            String xmlCode = null;
	    		// ��һ���������� 
	            while ((xmlCode = reader.readLine()) != null) {
	            	strBf.append(xmlCode + "\n");
	            }
	            //�������
	            if(editResStringModes==null){Log.appendErr("EditResStringMode,������");}
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
			// ����������Ϣ�������Edit
			StringBuffer pathEdit = new StringBuffer(resPath)
					.append(File.separator)
					.append(resFile.getParentPath())
					.append(File.separator)
					.append(resFile.getFileEditName())
					.append(resFile.getSuffix());
			// ԭ�е��ļ�ɾ��
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
		System.out.println("�ܼƣ�" + i);

		List<EditResStringMode> editResStringModes 
					= ReflectGetClazzUtils.getClazzS(
							"D:\\lyf\\EditAndroidStudio\\src\\hotplug\\meipian\\editResFile\\");
		EditResAll.editXmlFile("D:\\dabao\\GameBase_jdqs_base\\res\\",
					ReadResAll.resFileS, editResStringModes, "UTF-8");
	}
}
