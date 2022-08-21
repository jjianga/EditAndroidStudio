package com.zhkj.code.utils;

import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
import com.zhkj.code.bean.ClazzPackageTrait;

/**
 * ��֤ List<ClazzObjctTrait>�� �Ƿ�����ظ������� �ȹ���
 * 
 * @author Administrator
 *
 */
public class VerificationUtil {
	/**
	 * clazzObjctTraitThis �Ƿ������� �������� clazzName �� clazzObjctTrait
	 * 
	 * @param clazzName
	 * @param clazzObjctTraitThis
	 * @param clazzObjctTrait
	 * @return
	 */
	public static boolean isQuoteClazz(String clazzName, ClazzObjectTrait clazzObjctTraitThis,
			ClazzObjectTrait clazzObjctTrait) {
		// ������ƥ�� û�����ù�ϵ
		if (clazzName == null)
			return false;
		if (!clazzName.equals(clazzObjctTrait.getClazzName())) {
			return false;
		}
		List<ClazzPackageTrait> yinYongDe = clazzObjctTraitThis.getClazzPackageTraitS();
		// �����õ�·��
		String packageName = clazzObjctTrait.getPackageName() + "." + clazzObjctTrait.getClazzName();
		// ������ʽ���� ����� ���� �ж����ù�ϵ���
		for (ClazzPackageTrait clazzPackageTrait : yinYongDe) {
			// ��ʾ���õ���
			String clazz = clazzPackageTrait.getPackagePath();
			clazz = clazz.substring(clazz.lastIndexOf(".") + 1, clazz.length());
			// ������ͬ
			if (clazz.equals(clazzName)) {
				// ����·��һ�� �������ù�ϵ
				if (clazzPackageTrait.getPackagePath().equals(packageName)) {
					return true;
				}else{
					return false;
				}
			}
		}
		// û���@ʽ���� ���ж� �Ƿ� ͬ����
		if (clazzObjctTraitThis.getPackageName().equals(clazzObjctTrait.getPackageName())) {
			//��ȡ������ļ�
			return true;
		}
		return false;
	}

	/**
	 * �ַ����Ƿ�Ϊ��
	 */
	public static boolean isBlankString(String string) {
		return string == null || string.isEmpty();
	}

	/**
	 * �ַ����Ƿ�Ϊ��
	 */
	public static boolean isNotBlankString(String string) {
		return string != null && string.length() > 0;
	}

}
