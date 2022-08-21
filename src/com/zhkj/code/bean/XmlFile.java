package com.zhkj.code.bean;

import java.util.ArrayList;
import java.util.List;

import com.zhkj.code.utils.RandomStringUtil;

public class XmlFile extends ResFile{
	public int type = 100;
	List<XmlTager> xmlTagger = new ArrayList<XmlTager>();
	public XmlFile(String parentPath, String xmlName, String xmlSuffix) {
		super();
		this.parentPath = parentPath;
		this.fileName = xmlName;
		this.suffix = xmlSuffix;
	}
	public int getType(){
		return this.type;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public String getXmlName() {
		return fileName;
	}
	public void setXmlName(String xmlName) {
		this.fileName = xmlName;
	}
	public List<XmlTager> getXmlTagger() {
		return xmlTagger;
	}
	public void setXmlTagger(List<XmlTager> xmlTagger) {
		this.xmlTagger = xmlTagger;
	}
	public String getXmlNameEdit() {
		if(this.fileEditName == null)
		this.fileEditName = RandomStringUtil.autoImitateStringToLowerCase(this.fileName);
		return this.fileEditName;
	}
	public void setXmlNameEdit(String xmlNameEdit) {
		this.fileEditName = xmlNameEdit;
	}
	public String getXmlSuffix() {
		return suffix;
	}
	public void setXmlSuffix(String xmlSuffix) {
		this.suffix = xmlSuffix;
	}
	@Override
	public String toString() {
		return ", fileName=" + fileName + "XmlFile [type=" + type + ", xmlTager=" + xmlTagger + "]";
	}
	
}
