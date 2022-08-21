package com.zhkj.code.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zhkj.code.utils.RandomStringUtil;
/**
 * 类描述信息
 * @author Administrator
 *
 */
public class ClazzObjectTrait {
	public int state = 0;			//当前扫描状态 类外部 -1    类扫描 0，方法内部 1以上    
	private String abstractClazz;	//是否 继承 ，继承哪个类*
	private String clazzName;		//类名*
	private String clazzEditName;
	private String packageName;		//包名*
	private String packageEditName;
	private String[] implementsString;//实现类 列表
	
	private List<ClazzPackageTrait> ClazzPackageTraitS
		= new ArrayList<ClazzPackageTrait>();//引用到的包或类*
	private List<ClazzParamTrait> clazzParamTraitS 
		= new ArrayList<ClazzParamTrait>();//属性*
	private List<ClazzMethodTrait> clazzMethodTraitS
		= new ArrayList<ClazzMethodTrait>();//方法*
	private List<ClazzObjectTrait> clazzObjectTraitS
		= new ArrayList<ClazzObjectTrait>();//内部类*
	
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
