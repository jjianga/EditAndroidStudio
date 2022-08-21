package com.zhkj.code.bean;

public class ClazzPackageTrait {
	private String packagePath;
	private String packageEditPath;
	public ClazzPackageTrait() {
		super();
	}
	public ClazzPackageTrait(String packagePath, String packageEditPath) {
		super();
		this.packagePath = packagePath;
		this.packageEditPath = packageEditPath;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;//TODO 必须 保持和其他同名保证唯一
	}
	public String getPackageEditPath() {
		return packageEditPath;
	}
	public void setPackageEditPath(String packageEditPath) {
		this.packageEditPath = packageEditPath;
	}
	
}
