package com.zhkj.code.mode;

import com.zhkj.code.bean.ClazzObjectTrait;
/**
 * 读取模板
 * @author Administrator
 *
 */
public interface ReadJavaFileMode extends Mode {
	/**
	 * 传入java代码  解释成 类描述文件  （每次仅传入一段完整语句块）     
	 * @param javaCode
	 * @param clazzObjectTrait
	 */
	public void readJavaCodeToClazzObject(String javaCode, ClazzObjectTrait clazzObjectTrait);
}
