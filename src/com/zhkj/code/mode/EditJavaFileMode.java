package com.zhkj.code.mode;

import java.util.List;

import com.zhkj.code.bean.ClazzObjectTrait;
	/**
	 * �޸�ģ�� �޸�java���룡  �����޸ĺ������
	 * @author Administrator
	 *
	 */
	public interface EditJavaFileMode extends Mode{
	/**
	 * ����java����  ����������ص���Ϣȫ���޸�     ��  �����޸ĺ�Ĵ���
	 * @param javaCode				//Ҫ�޸ĵĴ����
	 * @param clazzObjectTraitThis	//�޸Ĵ�����������
	 * @param clazzObjectTraitS		//��Ҫ�޸ĵ��������
	 * @return 
	 */
	public String editJavaCode(String javaCode, ClazzObjectTrait clazzObjectTraitThis, List<ClazzObjectTrait> clazzObjectTraitS);
}
