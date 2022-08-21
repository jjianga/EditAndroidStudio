package com.zhkj.code.bean;

import com.zhkj.code.utils.RandomStringUtil;

public class ClazzParamTrait {
	private int live = 0;				//属性级别 默认  类属性      1 以上为方法局部变量
	private boolean isStatic;
	private String paramTypeName;			//属性类型
	private String paramName;				//属性名称
	private String paramEditName;
	public ClazzParamTrait() {
		super();
	}
	public boolean isStatic() {
		return isStatic;
	}
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	public int getLive() {
		return live;
	}
	public void setLive(int live) {
		this.live = live;
	}
	public ClazzParamTrait(String paramName, String paramEditName) {
		super();
		this.paramName = paramName;
		this.paramEditName = paramEditName;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
		this.paramEditName = RandomStringUtil.autoImitateString(paramName);
	}
	public String getParamEditName() {
		return paramEditName;
	}
	public void setParamEditName(String paramEditName) {
		this.paramEditName = paramEditName;
	}
	public String getParamTypeName() {
		return paramTypeName;
	}
	public void setParamTypeName(String paramTypeName) {
		this.paramTypeName = paramTypeName;
	}
	
	
}
