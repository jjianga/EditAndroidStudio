package com.zhkj.code.bean;

import com.zhkj.code.utils.RandomStringUtil;

public abstract class ResFile {
	//type  小于100 的 都只修改文件名
	private final int type = 0;
	protected String parentPath = "父级目录名称";
	protected String fileName = "文件名称";
	protected String suffix = null;			//"文件后缀"
	protected String fileEditName = null;	//"修改后文件名称"
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
