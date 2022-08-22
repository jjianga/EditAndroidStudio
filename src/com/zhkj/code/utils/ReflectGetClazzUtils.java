package com.zhkj.code.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zhkj.log.Log;

/**
 * �����ص㣺 �����������ڼ䣬��̬����һ�����������̬newһ������ ��̬�˽�����ڲ��Ľṹ����̬������������ĳһЩ���� ����ô���
 * �������ļ���ֻд������֣����Զ�̬������ؽ���
 * 
 * @author Administrator
 *
 */
public class ReflectGetClazzUtils{
	/**
	 * ��ȡĿ¼�����������ļ�
	 * @param filePath �ļ�·��
	 * @return ���ģ���б�
	 */
	public static <T> List<T> getClazzS(String filePath){
		ArrayList<String> fileS = new ArrayList<>();
		FileUtils.listFilesString(new File(filePath),fileS);
		ArrayList<T> objectS = new ArrayList<>();
		for (String file : fileS) {
			try {
				if (".properties".equals(FileUtils.getFileNameSuffix(file))) {
					objectS.add(getClazz(file));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(file + ":����");
			}
		}
		return objectS;
	}

	/**
	 * ͨ�������ļ���̬������
	 * @param filePath ·��
	 * @param <T> ���ģ��
	 * @return ��Ķ���
	 */
	@SuppressWarnings("unchecked") 
	public static <T> T getClazz(String filePath){
		// ��ȡ��  ��Ҫִ�еķ���  ��  ��Ҫ�ӽ����ȡ�Ĳ���
		ReadPropertiesUtils rpu = ReadPropertiesUtils.init(filePath);
		String clazzStr = rpu.getClazzStr();
		String paramSStr = rpu.getParamSStr();
		// ����load���ڴ棬����Class���͡�
		Class<?> cls = null;
		Object obj = null;
		// new һ������
		try {
			cls = Class.forName(clazzStr);
			obj = cls.newInstance();
		} catch (ClassNotFoundException e) {
			Log.appendErr("�����ļ���������Ŀ¼��"+filePath+ ",�ࡾ" + clazzStr);
			e.printStackTrace();
		}catch (Exception e) {
			Log.appendErr("���ʼ����������Ŀ¼��"+filePath+ ",�ࡾ" + clazzStr);
			e.printStackTrace();
		}
		//����������ֵ
		if(paramSStr != null){
			String[] paramS = paramSStr.split(",");
			for (String param : paramS) {            //�ӽ�����ֵ
				try {
					Field nameField;
					assert cls != null;
					nameField = cls.getDeclaredField(param);// ��ȡ��Ա����:name
					nameField.setAccessible(true);                    // ���ò���Ȩ��Ϊtrue
					nameField.set(obj, ValueSUtils.getValue(param));//��ֵ
				} catch (Exception e) {
					Log.appendErr("����ʼ����������Ŀ¼��" + filePath + ",�ࡾ" +
							clazzStr + ",���ԡ�" + param);
					e.printStackTrace();
				}
			}
		}
		// �õ���ʼ������ �������ʼ������
		try {
			assert cls != null;
			Method method = cls.getMethod("init", HashMap.class);
			//TODO �ӽ����õ���Ӧ��ֵ
			method.invoke(obj, rpu.getMap());
		} catch (Exception e) {
			Log.appendErr("init�������ô�������Ŀ¼��"+filePath+ ",�ࡾ" + clazzStr);
			e.printStackTrace();
		}
		return (T)obj;
	}
	public static void main(String[] args) {
		try {
			ReflectGetClazzUtils.getClazz("edit.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
