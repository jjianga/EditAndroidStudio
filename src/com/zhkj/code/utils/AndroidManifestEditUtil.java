package com.zhkj.code.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zhkj.code.mode.ReplaceFileStringMode;
import com.zhkj.code.replaceSours.ReplaceForRegular;
import com.zhkj.code.replaceSours.ReplaceImage;
import com.zhkj.code.replaceSours.ReplaceRegular;
import com.zhkj.log.Log;

/**
 * �����嵥��ͼƬ���ļ�  ..  �����Ϣ�ı�ʱ�޸��嵥ͼƬ�ļ�
 * @author Administrator
 *
 */

public class AndroidManifestEditUtil {
	private File javaFile = null;
	private String projectPath = "����Ŀ¼";
	private String value = "ͨ��key��ȡ��ֵ";
	public AndroidManifestEditUtil(String projectPath, File javaFile) {
		this.javaFile = javaFile;
		this.projectPath = projectPath;
	}
	public List<String> run(){
		ValueSUtils.returnValueS = new ArrayList<String>();
		String encoding = "UTF-8";
		BufferedReader reader = null;
        InputStreamReader read = null;
        FileInputStream fis = null;
        String fileLine = null;
        try {
        	fis = new FileInputStream(javaFile);
            read = new InputStreamReader(fis,encoding);      
            reader = new BufferedReader(read);
            ReplaceFileStringMode replaceMode = null;
            File file = null;
            StringBuffer stringBuf = null;
            // һ�ζ���һ��
            while ((fileLine = readLine(reader)) != null) {
            	switch (fileLine) {
					case "CD FILE;"://���ļ�  ���� �� stringBuf  
						fileLine = readLine(reader);
						file = new File(projectPath + fileLine);
						stringBuf = FileUtils.readString(file, encoding);
						break;
					case "SAVE FILE;"://�����ļ� stringBuf ����Ϊ�ļ�
						FileUtils.saveFile(file, stringBuf, encoding);
						file = null;
						stringBuf = null;
						break;
					case "SAVE AS NEW FILE;":
						//�ļ����   projectPath,fileLine ��value  ���ڵ�ʱ���������·�� ��
						//����value����Ϊ�� projectPath, fileLine  �������·��
						//����fileLine����Ϊ�� projectPath,  value �������·��
						String path = projectPath;
						fileLine = readLine(reader);
						if(fileLine!=null){
							path = path + fileLine;
						}
						if(value!=null){
							path = path + value;
						}
						FileUtils.saveFile(
								new File(path), stringBuf, encoding);
						break;
					case "SET VALUE;":
						//����ֵ  Ϊ��һ������ִ�����ó�ʼֵ ��ֵͨ��key����UIȡ��
						fileLine = readLine(reader);
						if("NULL".equals(fileLine.toUpperCase())){value = null;}
						//����ʾ�����ȡֵ UI.get(key)
						value = (String)ValueSUtils.getValue(fileLine);
						break;
					case "REPLACE REGULAR;":
						//�����滻String�ļ�  ��ȡ���� �ѷ���Ҫ����滻�� value
						replaceMode = new ReplaceRegular(value, stringBuf);
						break;
					case "REPLACE FOR REGULAR;":
						//ѭ���ı����򣬲�ʹ�������滻String�ļ��� �ѷ���Ҫ����滻�� value
						fileLine = readLine(reader);//regularValue ������ʽѭ���滻��ֵ
						replaceMode = new ReplaceForRegular(value, stringBuf,fileLine);
						break;
					case "IMAG EREPLACE;"://�滻ͼƬ��ԴimgRoot
						replaceMode = new ReplaceImage(readLine(reader),value, projectPath);
						break;
					case "ENCRYPTION;"://�ļ�����
						stringBuf = new StringBuffer(EncryptionUtil.encrypt(stringBuf.toString()));
						break;
					case "EXIT;":		//�˳� ����ִ�е���Ŀ�ļ���
						replaceMode = new ReplaceFileStringMode() {
							@Override
							public void init(HashMap<String, Object> map) {
							}
							@Override
							public void lineExecute(String Line) {
								ValueSUtils.returnValueS.add(Line);
							}
						};
						break;
					default:
		            	replaceMode.lineExecute(fileLine);
						break;
	            }
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        	Log.appendErr("ִ��ʧ�ܡ�"  + fileLine);
        } finally {
        	if (reader != null) {
        		try {
        			reader.close();
        		} catch (IOException e1) {
        		}
        	}
        	if (read != null) {
        		try {
        			read.close();
        		} catch (IOException e1) {
        		}
        	}
        	if (fis != null) {
        		try {
        			fis.close();
        		} catch (IOException e1) {
        		}
        	}
        }
		return ValueSUtils.returnValueS;
	}
	/**
	 * ����ע������  �Ϳ���
	 * @param reader
	 * @return
	 */
	private static String readLine(BufferedReader reader){
		String str = null;
		try {
			do {
				str = reader.readLine();
				if(str == null)return null;
			} while (VerificationUtil.isBlankString(str)||'#'==str.charAt(0));
		} catch (IOException e) {
			e.printStackTrace();
			Log.appendErr("��ȡһ���������");
		}
		return str;
	}
	
	public static void main(String[] args) {
		AndroidManifestEditUtil androidManifestEditUtil = 
				new AndroidManifestEditUtil("D:\\dabao\\GameBase_jdqs_base\\",new File("meipian.txt"));
		androidManifestEditUtil.run();
	}
	
}
