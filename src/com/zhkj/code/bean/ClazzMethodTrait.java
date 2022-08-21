package com.zhkj.code.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhkj.code.utils.RandomStringUtil;

public class ClazzMethodTrait {
	private String methodName;	//������
	private String returnValue;	//����ֵ
	private String methodEditName;	//�޸ĺ�ķ�����
	private String clazzParamTraitStr;	//���� ���ַ���
	private List<ClazzParamTrait> clazzParamTraitS = new ArrayList<ClazzParamTrait>();	//��ʱ���� ��
	public String getMethodName() {
		return methodName;
	}
	public String getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
		this.methodEditName = RandomStringUtil.autoImitateString(methodName);
	}
	public String getMethodEditName() {
		return methodEditName;
	}
	public void setMethodEditName(String methodEditName) {
		this.methodEditName = methodEditName;
	}
	public List<ClazzParamTrait> getClazzParamTraitS() {
		return clazzParamTraitS;
	}
	public void setClazzParamTraitS(List<ClazzParamTrait> clazzParamTraitS) {
		this.clazzParamTraitS = clazzParamTraitS;
	}
	public String getClazzParamTraitStr() {
		return clazzParamTraitStr;
	}
	public void setClazzParamTraitStr(String clazzParamTraitStr) {
		this.clazzParamTraitStr = clazzParamTraitStr;
	}
}
