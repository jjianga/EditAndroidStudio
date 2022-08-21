package com.zhkj.code.bean;

import com.zhkj.code.utils.RandomStringUtil;

public abstract class ResFile {
	//type  С��100 �� ��ֻ�޸��ļ���
	private final int type = 0;
	protected String parentPath = "����Ŀ¼����";
	protected String fileName = "�ļ�����";
	protected String suffix = null;			//"�ļ���׺"
	protected String fileEditName = null;	//"�޸ĺ��ļ�����"
	public int getType(){
		return this.type;
	}
	public String getParentPath() {
		return parentPath;
	}
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileEditName() {
		if(this.fileEditName==null)
		this.fileEditName = RandomStringUtil.autoImitateStringToLowerCase(fileName);
		return fileEditName;
	}
	public void setFileEditName(String fileEditName) {
		this.fileEditName = fileEditName;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
}
