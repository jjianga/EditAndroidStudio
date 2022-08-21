package com.zhkj.code.bean;

import com.zhkj.code.utils.RandomStringUtil;

public class XmlTager {
	String name = "��ǩName";
	String nameParamValue = null;//"��ǩName���Ե�ֵ"
	String nameParamValueEdit = null;
	String idParamValue = null;//"��ǩid���Ե�ֵ"
	String idParamValueEdit = null;
	
	public XmlTager(String name, String nameParamValue, String idParamValue) {
		super();
		this.name = name;
		this.nameParamValue = nameParamValue;
		this.nameParamValueEdit = RandomStringUtil.autoImitateString(nameParamValue);
		this.idParamValue = idParamValue;
		this.idParamValueEdit = RandomStringUtil.autoImitateString(idParamValue);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameParamValue() {
		return nameParamValue;
	}
	public void setNameParamValue(String nameParamValue) {
		this.nameParamValue = nameParamValue;
	}
	public String getIdParamValue() {
		return idParamValue;
	}
	public void setIdParamValue(String idParamValue) {
		this.idParamValue = idParamValue;
	}
	public String getNameParamValueEdit() {
		return nameParamValueEdit;
	}
	public void setNameParamValueEdit(String nameParamValueEdit) {
		this.nameParamValueEdit = nameParamValueEdit;
	}
	public String getIdParamValueEdit() {
		return idParamValueEdit;
	}
	public void setIdParamValueEdit(String idParamValueEdit) {
		this.idParamValueEdit = idParamValueEdit;
	}
	
}
