package com.zhkj.code.mode;

import com.zhkj.code.bean.ClazzObjectTrait;
/**
 * ��ȡģ��
 * @author Administrator
 *
 */
public interface ReadJavaFileMode extends Mode {
	/**
	 * ����java����  ���ͳ� �������ļ�  ��ÿ�ν�����һ���������飩     
	 * @param javaCode
	 * @param clazzObjectTrait
	 */
	public void readJavaCodeToClazzObject(String javaCode, ClazzObjectTrait clazzObjectTrait);
}
