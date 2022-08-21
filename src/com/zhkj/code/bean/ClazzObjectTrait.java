package com.zhkj.code.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zhkj.code.utils.RandomStringUtil;
/**
 * ��������Ϣ
 * @author Administrator
 *
 */
public class ClazzObjectTrait {
	public int state = 0;			//��ǰɨ��״̬ ���ⲿ -1    ��ɨ�� 0�������ڲ� 1����    
	private String abstractClazz;	//�Ƿ� �̳� ���̳��ĸ���*
	private String clazzName;		//����*
	private String clazzEditName;
	private String packageName;		//����*
	private String packageEditName;
	private String[] implementsString;//ʵ���� �б�
	
	private List<ClazzPackageTrait> ClazzPackageTraitS
		= new ArrayList<ClazzPackageTrait>();//���õ��İ�����*
	private List<ClazzParamTrait> clazzParamTraitS 
		= new ArrayList<ClazzParamTrait>();//����*
	private List<ClazzMethodTrait> clazzMethodTraitS
		= new ArrayList<ClazzMethodTrait>();//����*
	private List<ClazzObjectTrait> clazzObjectTraitS
		= new ArrayList<ClazzObjectTrait>();//�ڲ���*
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String[] getImplementsString() {
		return implementsString;
	}
	public void setImplementsString(String implementsString) {
//		System.out.println("implementsString:"+implementsString);
		this.implementsString = implementsString.split(",");
		this.clazzEditName = this.clazzName;
		for (int i = 0; i < this.implementsString.length; i++) {
			this.implementsString[i] = this.implementsString[i].trim();
		}
	}
	public void setImplementsString(String[] implementsString) {
		this.implementsString = implementsString;
	}
	
	public List<ClazzPackageTrait> getClazzPackageTraitS() {
		return ClazzPackageTraitS;
	}
	public void setClazzPackageTraitS(List<ClazzPackageTrait> clazzPackageTraitS) {
		ClazzPackageTraitS = clazzPackageTraitS;
	}
	public String getAbstractClazz() {
		return abstractClazz;
	}
	public void setAbstractClazz(String abstractClazz) {
		this.abstractClazz = abstractClazz;
		this.clazzEditName = this.clazzName;
	}
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
		this.clazzEditName = RandomStringUtil.autoImitateString(clazzName);
	}
	public String getClazzEditName() {
		return clazzEditName;
	}
	public void setClazzEditName(String clazzEditName) {
		this.clazzEditName = clazzEditName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
		this.packageEditName = RandomStringUtil.getPackage(packageName);
	}
	public String getPackageEditName() {
		return packageEditName;
	}
	public void setPackageEditName(String packageEditName) {
		this.packageEditName = packageEditName;
	}
	public List<ClazzParamTrait> getClazzParamTraitS() {
		return clazzParamTraitS;
	}
	public void setClazzParamTraitS(List<ClazzParamTrait> clazzParamTraitS) {
		this.clazzParamTraitS = clazzParamTraitS;
	}
	public List<ClazzObjectTrait> getClazzObjctTraitS() {
		return clazzObjectTraitS;
	}
	public void setClazzObjctTraitS(List<ClazzObjectTrait> clazzObjctTraitS) {
		this.clazzObjectTraitS = clazzObjctTraitS;
	}
	public List<ClazzMethodTrait> getClazzMethodTraitS() {
		return clazzMethodTraitS;
	}
	public void setClazzMethodTraitS(List<ClazzMethodTrait> clazzMethodTraitS) {
		this.clazzMethodTraitS = clazzMethodTraitS;
	}
	@Override
	public String toString() {
		return "ClazzObjctTrait [state=" + state + ", abstractClazz=" + abstractClazz + ", clazzName=" + clazzName
				+ ", clazzEditName=" + clazzEditName + ", packagName=" + packageName + ", packagEditName="
				+ packageEditName + ", implementsString=" + Arrays.toString(implementsString) + "]";
	}
	
}
