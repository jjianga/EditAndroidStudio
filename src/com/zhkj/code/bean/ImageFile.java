package com.zhkj.code.bean;

import com.zhkj.code.utils.RandomStringUtil;

public class ImageFile extends ResFile{
	private int type = 1;
	public ImageFile(String parentPath, String imageName, String imageSuffix) {
		super();
		this.parentPath = parentPath;
		this.fileName = imageName;
		this.suffix = imageSuffix;
		this.fileEditName = RandomStringUtil.autoImitateStringToLowerCase(imageName);
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
	public String getImageName() {
		return fileName;
	}
	public void setImageName(String imageName) {
		this.fileName = imageName;
	}
	public String getImageNameEdit() {
		return fileEditName;
	}
	public void setImageNameEdit(String imageNameEdit) {
		this.fileEditName = imageNameEdit;
	}
	public String getImageSuffix() {
		return suffix;
	}
	public void setImageSuffix(String imageSuffix) {
		this.suffix = imageSuffix;
	}
	

}
