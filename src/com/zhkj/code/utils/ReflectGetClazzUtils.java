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
	 * @param filePath
	 * @return
	 */
	public static <T> List<T> getClazzS(String filePath){
		ArrayList<String> fileS = new ArrayList<String>();
		FileUtils.listFilesString(new File(filePath),fileS);
		ArrayList<T> objectS = new ArrayList<T>();
		for (int i = 0; i < fileS.size(); i++) {
			try {
				if(".properties".equals(FileUtils.getFileNameSuffix(fileS.get(i)))){
					objectS.add(getClazz(fileS.get(i)));
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(fileS.get(i) + ":����");
			}
		}
		return objectS;
	}
	
	/**
	 * ͨ�������ļ���̬������
	 * @param filePath
	 * @throws Exception
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
			for (int i = 0; i < paramS.length; i++) {			//�ӽ�����ֵ
				try {
			        Field nameField;
						nameField = cls.getDeclaredField(paramS[i]);// ��ȡ��Ա����:name
			        nameField.setAccessible(true);					// ���ò���Ȩ��Ϊtrue
			        nameField.set(obj, ValueSUtils.getValue(paramS[i]));//��ֵ
				} catch (Exception e) {
					Log.appendErr("����ʼ����������Ŀ¼��"+filePath+ ",�ࡾ" +
												clazzStr+",���ԡ�" + paramS[i]);
					e.printStackTrace();
				} 
			}
		}
		// �õ���ʼ������ �������ʼ������
		try {
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


class All{
	private String a = "c";
	public String c = "c";
	public All() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(HashMap<String, Object> map){
		System.out.println(map.get("a") + "ִ����a��");
		System.out.println(map.get("b") + "ִ����b��");
		System.out.println(map.get("c") + "ִ����c��");
		System.out.println(a);
		System.out.println(c);
	}
}
