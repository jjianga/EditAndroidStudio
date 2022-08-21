package com.zhkj.code.utils;

import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.Keystore;
import com.zhkj.code.bean.ResFile;
import com.zhkj.log.Log;
import com.zhkj.ui.tool.ReadFileAddModule;
//Ѱ������  
public class ValueSUtils {
	/**
	 * �����ļ����붨�����������
	 */
	public static final String projectPath = "projectPath";	//����Ŀ¼
	public static final String storePath = "storePath";		//ǩ���ļ�·��
	public static final String alias = "alias";				//ǩ������
	public static final String passWord = "passWord";		//ǩ������
	public static final String aliasPassWord = "aliasPassWord";	//��������
	/**
	 * �����ļ����ܶ������������   ��������    List Xxx , Class CLASS_xxx, Ŀ¼ _xxx,  �ļ�  Xxx,
	 */
	public static final String ReturnValueS = "ReturnValueS";//txt ���ص�ֵ�б�
	public static final String ResFileS = "ResFileS";			//��Դ�ļ�����
	public static final String ClazzObjctTraitS = "ClazzObjctTraitS";//���ļ�����
	public static final String AndroidManifest = "androidManifest_";	//��׿��xml
	public static final String CLASS_keystore = "CLASS_keystore";	//��ȡǩ����Ϣ����
	public static final String SrcPath = "_srcPath";					//��ȡԴ��·��
	public static final String ResPath = "_resPath";					//��ȡ��Դ·��
	public static final String AssetsPath = "_assetsPath";			// assets·��
	
	//ǩ����
	private static Keystore keystore;	
	// ��ȡ��������Ϣ
	public static List<ClazzObjectTrait> clazzObjctTraitS;
	// ��ȡ����XML
	public static List<ResFile> resFileS;
	//����ֵ�б�
	public static List<String> returnValueS;
	/**
	 * 
	 */
	public static void clear(){
		RandomStringUtil.clear();
		keystore = null;
		clazzObjctTraitS = null;
		resFileS =  null;
		returnValueS = null;
	}
	
	
	public static Object getValue(String key){
		switch (key) {
		case ReturnValueS://txt�ļ����ص�ֵ�б�
			return returnValueS;
			
		case ResFileS:	//��Դ�ļ��б�
			return resFileS;
			
		case ClazzObjctTraitS://�������ļ��б�
			return clazzObjctTraitS;

		case SrcPath:			//Դ��·��
			return ReadFileAddModule.getDirectory(projectPath) + returnValueS.get(1);

		case ResPath:			//��Դ·��
			return ReadFileAddModule.getDirectory(projectPath) + returnValueS.get(2);

		case AssetsPath:		//assets·��
			return ReadFileAddModule.getDirectory(projectPath) + returnValueS.get(3);

		case AndroidManifest:		//assets·��
			return ReadFileAddModule.getDirectory(projectPath) + returnValueS.get(4);

		case CLASS_keystore:		//����ǩ�� ����
			if(keystore == null){
				String storePath = ReadFileAddModule.getValue(ValueSUtils.storePath);
				String alias =  ReadFileAddModule.getValue(ValueSUtils.alias);
				String passWord =  ReadFileAddModule.getValue(ValueSUtils.passWord);
				String aliasPassWord =  ReadFileAddModule.getValue(ValueSUtils.aliasPassWord);
				keystore = new Keystore(storePath, alias, passWord, aliasPassWord);
			}
			return keystore;
			
		default:	//����ؼ���ȡֵ
			String str = ReadFileAddModule.getValue(key);
			if(str==null){
				str = RandomStringUtil.autoImitateStringToLowerCase(key);
				Log.appendInfo(key+",isNull,��ȡ����ַ�����"+str);
			}
			return str;
		}
	}
}
